package com.example.countbunny.launchmodetest1;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.countbunny.launchmodetest1.othertips.CrashHandler;

public class App extends Application {

    public static Context ctx;

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = getApplicationContext();
        CrashHandler.getInstance().init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
