package com.example.countbunny.launchmodetest1.remoteview;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.example.countbunny.launchmodetest1.R;
import com.example.countbunny.launchmodetest1.SecondActivity;

public class RemoteViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_view);
    }

    public void notify1(View view) {
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.avatar)
                .setLargeIcon(((BitmapDrawable)getResources().getDrawable(R.mipmap.avatar, getTheme())).getBitmap())
//                .setTicker("hello world",getMyRemoteViews())
                .setContentText("test notify")
                .setContentTitle("hello world")
                .setWhen(System.currentTimeMillis())
                .setPriority(2)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 0,
                        new Intent(this, SecondActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                .build();
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(1, notification);
    }

    public void notify2(View view) {
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setTicker("hello world")
                .setWhen(System.currentTimeMillis())
                .setPriority(2)
                .setAutoCancel(true)
                .setCustomContentView(getMyRemoteViews())
                .build();

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(2, notification);
    }

    private RemoteViews getMyRemoteViews() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remote_view);
        remoteViews.setImageViewResource(R.id.icon, R.mipmap.avatar);
        remoteViews.setTextViewText(R.id.msg, "chapter_5");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, SecondActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.open_activity, pendingIntent);
        return remoteViews;
    }
}
