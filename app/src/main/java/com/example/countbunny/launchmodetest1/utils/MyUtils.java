package com.example.countbunny.launchmodetest1.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

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
}
