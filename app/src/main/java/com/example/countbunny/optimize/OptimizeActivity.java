package com.example.countbunny.optimize;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.countbunny.launchmodetest1.R;

public class OptimizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize);
    }

    public void generateAnr(View view) {
        SystemClock.sleep(30 * 1000);
    }
}
