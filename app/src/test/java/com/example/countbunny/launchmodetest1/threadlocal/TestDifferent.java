package com.example.countbunny.launchmodetest1.threadlocal;

import android.util.Log;

import org.junit.Test;

public class TestDifferent {

    private static final String TAG = "TestDifferent";
    private ThreadLocal<Boolean> mBooleanThreadLocal = new ThreadLocal<>();

    @Test
    public void runThreadLocalTest() {
        mBooleanThreadLocal.set(true);
        System.out.println("[Thread#main] mBooleanThreadLocal=" + mBooleanThreadLocal.get());

        new Thread("Thread#1"){
            @Override
            public void run() {
                mBooleanThreadLocal.set(false);
                System.out.println("[Thread#1] mBooleanThreadLocal=" + mBooleanThreadLocal.get());
            }
        }.start();

        new Thread("Thread#2"){
            @Override
            public void run() {
                System.out.println("[Thread#2] mBooleanThreadLocal=" + mBooleanThreadLocal.get());
            }
        }.start();
    }
}
