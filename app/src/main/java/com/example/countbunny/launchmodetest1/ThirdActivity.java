package com.example.countbunny.launchmodetest1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }

    public void click(View view) {
        Intent intent = new Intent();
//        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction("com.ryq.task1.visit1");
        intent.setDataAndType(Uri.parse("http://abc"), "text/plain");
        PackageManager pm = getPackageManager();
        if (pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            Toast.makeText(this, "匹配失败", Toast.LENGTH_SHORT).show();
            return;
        } else {
            startActivity(intent);
        }
    }
}
