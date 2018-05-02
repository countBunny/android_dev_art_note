package com.example.countbunny.launchmodetest1.window;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.countbunny.launchmodetest1.R;
import com.example.countbunny.launchmodetest1.utils.IConstant;

public class WindowTestActivity extends AppCompatActivity {

    private Button mFloatingButton;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_test);
        mWindowManager = getWindowManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, IConstant.RequestCode.WINDOW_MODIFY);
//            requestPermissions(new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW}, IConstant.RequestCode.WINDOW_MODIFY);
        }
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requestCode == IConstant.RequestCode.WINDOW_MODIFY
                    && Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "弹窗权限已取得", Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("WindowTestActivity", "permission result come back. permissions=" + permissions.toString() + "grantResult is " + grantResults.toString());
        /*super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == IConstant.RequestCode.WINDOW_MODIFY && null != grantResults && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "弹窗权限已取得", Toast.LENGTH_SHORT);
        }*/
    }

    @Override
    protected void onDestroy() {
        if (null != mFloatingButton) {
            mWindowManager.removeView(mFloatingButton);
            mFloatingButton = null;
        }
        super.onDestroy();
    }

    public void addWindowFirst(View view) {
//        if (!checkPermission()) {
//            return;
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            return;
        }
        mFloatingButton = new Button(this);
        mFloatingButton.setText("button");
        mFloatingButton.setOnTouchListener(new OnTouchListener() {

            private float mY;
            private float mX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float rawX = (int) event.getRawX();
                float rawY = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mX = rawX;
                        mY = rawY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) (rawX - mX);
                        int dy = (int) (rawY - mY);
                        mLayoutParams.x += dx;
                        mLayoutParams.y += dy;
                        mX = rawX;
                        mY = rawY;
                        mWindowManager.updateViewLayout(mFloatingButton, mLayoutParams);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        }
        mLayoutParams.x = 100;
        mLayoutParams.y = 300;
        mWindowManager.addView(mFloatingButton, mLayoutParams);
    }
}
