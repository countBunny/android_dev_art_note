package com.example.countbunny.launchmodetest1.viewevent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.countbunny.launchmodetest1.R;
import com.example.countbunny.launchmodetest1.touchconflict.DemoActivity1;
import com.example.countbunny.launchmodetest1.touchconflict.DemoActivity2;

public class ViewEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
    }

    public void launchConflictDemo(View view) {
        startActivity(new Intent(this, DemoActivity1.class));
    }

    public void launchConflictDemo2(View view) {
        startActivity(new Intent(this, DemoActivity2.class));
    }
}
