package com.example.countbunny.launchmodetest1;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.example.countbunny.launchmodetest1.utils.IConstant;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void click(View view) {
        Intent intent = new Intent();
        intent.setClass(this, ThirdActivity.class);
        startActivity(intent);
    }

    public void sendRemoteViews(View view) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.simulated_notification);
        remoteViews.setTextViewText(R.id.msg, "msg from process:" + Process.myPid());
//        remoteViews.setImageViewResource(R.id.icon, R.mipmap.avatar);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, SecondActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, new Intent(this, ThirdActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.item_holder, pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.open_activity_third, pendingIntent2);
        Intent intent = new Intent(IConstant.REMOTE_ACTION);
        intent.putExtra(IConstant.EXTRA_REMOTE_VIEWS, remoteViews);
        sendBroadcast(intent);
    }
}
