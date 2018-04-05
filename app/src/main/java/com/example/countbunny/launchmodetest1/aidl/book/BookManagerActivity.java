package com.example.countbunny.launchmodetest1.aidl.book;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.countbunny.launchmodetest1.R;
import com.example.countbunny.launchmodetest1.aidl.Book;
import com.example.countbunny.launchmodetest1.aidl.IBookManager;
import com.example.countbunny.launchmodetest1.aidl.IOnNewBookArrivedListener;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {

    private static final String TAG = BookManagerActivity.class.getSimpleName();

    TextView mTvContent;

    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private com.example.countbunny.launchmodetest1.aidl.IBookManager mRemoteBookManager;

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, book)
                    .sendToTarget();
        }
    };
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service == null) {
                return;
            }
            mRemoteBookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> list = mRemoteBookManager.getBookList();
                Log.i(TAG, "----------->list type is " + list.getClass().getCanonicalName());
                mTvContent.setText(list.toString());
                Book book = new Book(3, "Android develop art");
                mRemoteBookManager.addBook(book);
                Log.i(TAG, "------------->add book:" + book);
                List<Book> listNew = mRemoteBookManager.getBookList();
                Log.i(TAG, "------------->BookList :" + listNew.toString());
                mRemoteBookManager.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG, "received new book:" + msg.obj);
                    mTvContent.setText(mTvContent.getText().toString() + msg.obj.toString());
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        initViews();
        bindService(new Intent(this, BookManagerService.class), mConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (null != mRemoteBookManager && mRemoteBookManager.asBinder().isBinderAlive()) {
            Log.i(TAG, "------------->unregister listener:" + mOnNewBookArrivedListener);
            try {
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        unbindService(mConnection);
        super.onDestroy();
    }

    private void initViews() {
        mTvContent = (TextView) findViewById(R.id.tvContent);
    }

    public void getBookListButton(View view) {
        Toast.makeText(this, "getBookList clicked", Toast.LENGTH_SHORT).show();
        if (null == mRemoteBookManager) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (null == mRemoteBookManager) {
                    return;
                }
                try {
                    final List<Book> bookList = mRemoteBookManager.getBookList();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mTvContent.setText(bookList.toString());
                        }
                    });
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
