package com.example.countbunny.launchmodetest1.aidl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.countbunny.launchmodetest1.R;
import com.example.countbunny.launchmodetest1.aidl.binderpool.BinderPoolActivity;
import com.example.countbunny.launchmodetest1.aidl.book.BookManagerActivity;
import com.example.countbunny.launchmodetest1.aidl.bookprovider.BookProvider;
import com.example.countbunny.launchmodetest1.aidl.bookprovider.ProviderActivity;
import com.example.countbunny.launchmodetest1.aidl.messeger.MessengerActivity;
import com.example.countbunny.launchmodetest1.aidl.socket.TCPClientActivity;

public class AidlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
    }

    public void launchMessengerActivity(View view) {
        Intent intent = new Intent(this, MessengerActivity.class);
        startActivity(intent);
    }

    public void launchBookManagerActivity(View view) {
        Intent intent = new Intent(this, BookManagerActivity.class);
        startActivity(intent);
    }

    public void launchBookProviderActivity(View view) {
        Intent intent = new Intent(this, ProviderActivity.class);
        startActivity(intent);
    }

    public void launchTCPClientActivity(View view) {
        Intent intent = new Intent(this, TCPClientActivity.class);
        startActivity(intent);
    }

    public void launchBinderPoolActivity(View view) {
        Intent intent = new Intent(this, BinderPoolActivity.class);
        startActivity(intent);
    }
}
