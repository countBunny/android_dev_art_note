package com.example.countbunny.launchmodetest1.bitmapdecode;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.example.countbunny.launchmodetest1.App;
import com.example.countbunny.launchmodetest1.utils.IConstant;
import com.example.countbunny.launchmodetest1.utils.MyUtils;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapCacheManager {

    private static final String TAG = "BitmapCacheManager";
    private static volatile BitmapCacheManager sInstance;

    private static final int MAX_MEMORY = (int) (Runtime.getRuntime().maxMemory() / 1024);
    //50MB
    private static final int MAX_DISK = 50 * 1024 * 1024;

    private int mCacheSize;

    private boolean mIsDiskLruCacheCreated;

    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;

    private BitmapCacheManager() {
        initLruCache();
        initDiskLruCache();
    }

    private void initDiskLruCache() {
        File diskCacheDir = getDiskCacheDir(App.ctx, "bitmap");
        if (getUsableSpace(diskCacheDir) > MAX_DISK) {

        }
        try {
            mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, MAX_DISK);
            mIsDiskLruCacheCreated = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private long getUsableSpace(File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return file.getUsableSpace();
        }
        StatFs statFs = new StatFs(file.getPath());
        return statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
    }

    private File getDiskCacheDir(Context ctx, String childFold) {
        boolean externalStorageAvailabel = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailabel) {
            cachePath = ctx.getExternalCacheDir().getPath();
        } else {
            cachePath = ctx.getCacheDir().getPath();
        }
        File destDir = new File(ctx.getExternalCacheDir(), childFold);
        if (!destDir.exists()) {
            destDir.mkdirs();
            Log.d(TAG, "cache child fold's absPath is " + destDir.getAbsolutePath());
        }
        return destDir;
    }

    public final void putInMemory(String key, Bitmap value) {
        if (getFromMemory(key) == null) {
            mMemoryCache.put(key, value);
        }
    }

    public final Bitmap getFromMemory(String key) {
        return mMemoryCache.get(key);
    }

    public final Bitmap getFromNetOrDisk(String url, int reqWidth, int reqHeight) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("can not visit network from UI Thread.");
        }
        if (mDiskLruCache == null) {
            return null;
        }
        Bitmap bitmap;
        if ((bitmap = getFromDisk(url, reqWidth, reqHeight)) != null) {
            return bitmap;
        }
        String key = MyUtils.hashKeyFromUrl(url);
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskLruCache.edit(key);
            if (null == editor) {
                Log.d(TAG, "can't get editor from DiskLruCache");
            }
            OutputStream outputStream = editor.newOutputStream(0);
            if (MyUtils.downloadUrlToStream(url, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
            return getFromDisk(url, reqWidth, reqHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private final Bitmap getFromDisk(String url, int reqWidth, int reqHeight) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("can not visit network from UI Thread.");
        }
        if (mDiskLruCache == null) {
            return null;
        }
        String key = MyUtils.hashKeyFromUrl(url);
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (null != snapshot) {
                FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(0);
                FileDescriptor fd = fileInputStream.getFD();
                Bitmap bitmap = BitmapDecodeUtils.decodeSampledBitmapFromFileDescriptor(fd, reqWidth, reqHeight);
                if (bitmap != null) {
                    putInMemory(url, bitmap);
                    return bitmap;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initLruCache() {
        mCacheSize = MAX_MEMORY / 8;
        mMemoryCache = new LruCache<String, Bitmap>(mCacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                //more steps to release the resources contained in the oldValue
                oldValue.recycle();
            }
        };
    }

    public static BitmapCacheManager getInstance() {
        if (null == sInstance) {
            synchronized (BitmapCacheManager.class) {
                if (null == sInstance) {
                    sInstance = new BitmapCacheManager();
                }
            }
        }
        return sInstance;
    }

    public Bitmap getBitmapFromNet(String url, int reqWidth, int reqHeight) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("can not visit network from UI Thread.");
        }
        Bitmap bitmap = null;
        BufferedInputStream in = null;
        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) new URL(url).openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), IConstant.IO_BUFFERED_SIZE);
            bitmap = BitmapFactory.decodeStream(in);
            //diskCache must be null
            BitmapDecodeUtils.compress(bitmap, reqWidth, reqHeight);
            mMemoryCache.put(url, bitmap);
        } catch (IOException e) {
            Log.e(TAG, "Error in downloading bitmap:" + e);
        } finally {
            if (null!=urlConnection) {
                urlConnection.disconnect();
            }
            MyUtils.close(in);
        }
        return bitmap;
    }

    public boolean isDiskLruCacheCreated() {
        return mIsDiskLruCacheCreated;
    }
}
