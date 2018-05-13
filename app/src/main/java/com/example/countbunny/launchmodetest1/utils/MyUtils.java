package com.example.countbunny.launchmodetest1.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by countBunny on 2018/3/21.
 */

public class MyUtils {

    public static final void close(Reader reader) {
        if (null!= reader) {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("reader close failed! IOExc");
                e.printStackTrace();
            }
        }
    }

    public static final void close(Writer writer) {
        if (null!= writer) {
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println("writer close failed! IOExc");
                e.printStackTrace();
            }
        }
    }

    public static final DisplayMetrics getScreenMetrics(Context context) {
        return context.getApplicationContext().getResources().getDisplayMetrics();
    }

    public static final void close(OutputStream os) {
        if (null!= os) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static final void close(InputStream is) {
        if (null!= is) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static final URL convert(String urlAddr){
        try {
            return URI.create(urlAddr).toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final String hashKeyFromUrl(String url) {
        String cacheKey = null;
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            cacheKey = bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static final boolean downloadUrlToStream(String url, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            final URL urlObj = new URL(url);
            urlConnection = (HttpURLConnection) urlObj.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), IConstant.IO_BUFFERED_SIZE);
            out = new BufferedOutputStream(outputStream, IConstant.IO_BUFFERED_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (IOException e) {
            Log.e("MyUtils", "download Bitmap failed." + e);
        } finally {
            if (urlConnection!=null) {
                urlConnection.disconnect();
            }
            close(in);
            close(out);
        }

        return false;
    }
}
