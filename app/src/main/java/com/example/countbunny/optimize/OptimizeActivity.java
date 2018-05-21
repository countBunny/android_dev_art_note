package com.example.countbunny.optimize;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;

import com.example.countbunny.launchmodetest1.R;

public class OptimizeActivity extends AppCompatActivity implements PopupWindow.OnDismissListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize);
    }

    public void generateAnr(View view) {
        SystemClock.sleep(30 * 1000);
    }

    public void leakMemory(View view) {
        MemoryHoldWindow.pop(this);
    }

    @Override
    public void onDismiss() {
        finish();
    }
}
