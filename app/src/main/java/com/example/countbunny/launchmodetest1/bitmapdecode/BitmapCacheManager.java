package com.example.countbunny.launchmodetest1.bitmapdecode;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.example.countbunny.launchmodetest1.App;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;

public class BitmapCacheManager {

    private static final String TAG = "BitmapCacheManager";
    private static volatile BitmapCacheManager sInstance;

    private static final int MAX_MEMORY = (int) (Runtime.getRuntime().maxMemory() / 1024);
    //50MB
    private static final int MAX_DISK = 50 * 1024 * 1024;

    private int mCacheSize;

    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;

    private BitmapCacheManager() {
        initLruCache();
        initDiskLruCache();
    }

    private void initDiskLruCache() {
        File diskCacheDir = getDiskCacheDir(App.ctx, "bitmap");
        try {
            mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, MAX_DISK);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private File getDiskCacheDir(Context ctx, String childFold) {
        File destDir = new File(ctx.getExternalCacheDir(), childFold);
        if (!destDir.exists()) {
            destDir.mkdir();
            Log.d(TAG, "cache child fold's absPath is " + destDir.getAbsolutePath());
        }
        return destDir;
    }

    public final void putInMemory(String key, Bitmap value) {
        mMemoryCache.put(key, value);
    }

    public final Bitmap getFromMemory(String key) {
        return mMemoryCache.get(key);
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

}
