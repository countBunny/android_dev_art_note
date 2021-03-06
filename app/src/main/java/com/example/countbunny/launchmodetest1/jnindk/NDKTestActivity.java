package com.example.countbunny.launchmodetest1.jnindk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.countbunny.launchmodetest1.R;

public class NDKTestActivity extends AppCompatActivity {

    private static final String TAG = "NDKTestActivity";

    static {
        System.loadLibrary("jni-test");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndktest);
        TextView msg = findViewById(R.id.msg);
        msg.setText(get());
        set("hello world from JniTestApp");
    }

    public static void methodCalledByJni(String msgFromJni) {
        Log.d(TAG, "methodCalledByJni, msg: " + msgFromJni);
    }

    public native String get();

    public native void set(String str);
}
