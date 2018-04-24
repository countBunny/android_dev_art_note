package com.example.countbunny.launchmodetest1.drawable;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.countbunny.launchmodetest1.R;

public class DrawableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);
        initView();
    }

    private void initView() {
        View view = findViewById(R.id.scaleView);
        ScaleDrawable scaleDrawable = (ScaleDrawable) view.getBackground();
        scaleDrawable.setLevel(2000);

        View viewClip = findViewById(R.id.clipView);
        ClipDrawable clipDrawable = (ClipDrawable) viewClip.getBackground();
        clipDrawable.setLevel(5000);

        View cusView = findViewById(R.id.cusView);
        cusView.setBackground(new CustomDrawable(0xff8899ee));
    }
}
