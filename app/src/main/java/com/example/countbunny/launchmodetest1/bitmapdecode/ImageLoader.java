package com.example.countbunny.launchmodetest1.bitmapdecode;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.example.countbunny.launchmodetest1.R;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class ImageLoader {

    private static final String TAG = "ImageLoader";
    private static final int TAG_KEY_URL = R.id.image_loader_uri;

    private static volatile ImageLoader sInstance;

    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            LoaderResult result = (LoaderResult) msg.obj;
            ImageView imageView = result.target;
            String urlLatest = (String) imageView.getTag(TAG_KEY_URL);
            if (urlLatest.equals(result.url)) {
                imageView.setImageBitmap(result.bitmap);
            } else {
                Log.w(TAG, "set image bitmap, but url has changed, ignored!");
            }
        }
    };

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = 2 * CPU_COUNT + 1;
    private static final long KEEP_ALIVE = 10L;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "ImageLoader#" + mCount.getAndIncrement());
        }
    };

    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor
            (CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<Runnable>(), sThreadFactory);

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        if (null == sInstance) {
            synchronized (ImageLoader.class) {
                if (null == sInstance) {
                    sInstance = new ImageLoader();
                }
            }
        }
        return sInstance;
    }

    public final Bitmap loadBitmap(String url, int reqWidth, int reqHeight) {
        BitmapCacheManager cacheManager = BitmapCacheManager.getInstance();
        Bitmap bitmap = cacheManager.getFromMemory(url);
        if (bitmap != null) {
            Log.d(TAG, "loadBitmapFromMemCache, url" + url);
            return bitmap;
        }

        bitmap = cacheManager.getFromNetOrDisk(url, reqWidth, reqHeight);
        if (null != bitmap) {
            Log.d(TAG, "loadBitmapFromDiskCache, url:" + url);
            return bitmap;
        }
        if (!cacheManager.isDiskLruCacheCreated()) {
            Log.w(TAG, "encounter error, DiskLruCache is not created.");
            return cacheManager.getBitmapFromNet(url, reqWidth, reqHeight);
        }
        return null;
    }

    public void bindBitmap(final String url, final ImageView target, final int reqWidth, final int reqHeight) {
        target.setTag(TAG_KEY_URL, url);
        Bitmap bitmap = BitmapCacheManager.getInstance().getFromMemory(url);
        if (null != bitmap) {
            Log.d(TAG, "loadBitmapFromMemCache, url" + url);
            target.setImageBitmap(bitmap);
            return;
        }
        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(url, reqWidth, reqHeight);
                if (bitmap != null) {
                    LoaderResult result = new LoaderResult(target, url, bitmap);
                    mMainHandler.obtainMessage(0, result).sendToTarget();
                }
            }
        };
        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    private static class LoaderResult {
        public ImageView target;
        public String url;
        public Bitmap bitmap;

        public LoaderResult(ImageView target, String url, Bitmap bitmap) {
            this.target = target;
            this.url = url;
            this.bitmap = bitmap;
        }
    }
}
