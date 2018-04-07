package com.example.countbunny.launchmodetest1.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

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
}
