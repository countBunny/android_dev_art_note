package com.example.countbunny.launchmodetest1.bitmapdecode;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

public final class BitmapDecodeUtils {


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        //First decode with inJustDecodeBounds= true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        //Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        //Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }
        //Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            //Calculate the largest inSampleSize value that is a power of 2 and keep both
            //height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static final Bitmap decodeSampledBitmapFromFileDescriptor
            (FileDescriptor fd, int reqWidth, int reqHeight) {
        //decode bounds info
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        //calculate sample size
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    /**
     * 重新调整Bitmap的大小
     * @param bitmap
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap compress(Bitmap bitmap, int reqWidth, int reqHeight) {
        if (reqHeight == 0 || reqWidth == 0) {
            return bitmap;
        }
        if (bitmap == null) {
            return null;
        }
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();

        int halfHeight = bitmapHeight / 2;
        int halfWidth = bitmapWidth / 2;
        int compressRate = 1;
        while ((halfWidth / compressRate) > reqWidth && (halfHeight / compressRate) > reqHeight) {
            compressRate *= 2;
        }
        int scale = (int) Math.pow(2, compressRate);
        reqWidth = bitmapWidth / scale;
        reqHeight = bitmapHeight / scale;
        return Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, true);
    }
}
