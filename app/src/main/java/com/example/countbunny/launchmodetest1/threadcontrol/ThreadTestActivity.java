package com.example.countbunny.launchmodetest1.threadcontrol;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.countbunny.launchmodetest1.R;
import com.example.countbunny.launchmodetest1.utils.Downloader;
import com.example.countbunny.launchmodetest1.utils.MyUtils;

import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadTestActivity extends AppCompatActivity {

    private static final String TAG = "ThreadTestActivity";

    private ProgressBar mProgressBar;
    private Dialog mDialog;
    private URL[] urls = new URL[]{
            MyUtils.convert("https://www.hdwallpapers.in/download/beach_huawei_mediapad_m5_stock-wide.jpg"),
            MyUtils.convert("https://www.hdwallpapers.in/download/kylie_jenner_4k_8k-wide.jpg"),
            MyUtils.convert("https://www.hdwallpapers.in/download/sunset_lake_reflections-wide.jpg"),
            MyUtils.convert("https://www.hdwallpapers.in/download/green_landscape_2-wide.jpg"),
    };

    private Runnable command = new Runnable() {
        @Override
        public void run() {
            SystemClock.sleep(2000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_test);
        initViews();
    }

    @Override
    protected void onDestroy() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.cancel();
            mDialog = null;
        }
        super.onDestroy();
    }

    private void initViews() {
        mProgressBar = findViewById(R.id.progressBar);
    }

    public void startAsyncTask(View view) {
        new DownloadFilesTask().execute(urls);
    }

    public void startAsyncTask2(View view) {
        new MyAsyncTask("MyAsyncTask#1").execute("");
        new MyAsyncTask("MyAsyncTask#2").execute("");
        new MyAsyncTask("MyAsyncTask#3").execute("");
        new MyAsyncTask("MyAsyncTask#4").execute("");
        new MyAsyncTask("MyAsyncTask#5").execute("");
    }

    /**
     * 并行处理五个任务
     * @param view
     */
    public void startAsyncTask3(View view) {
        new MyAsyncTask("MyAsyncTask#1").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        new MyAsyncTask("MyAsyncTask#2").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        new MyAsyncTask("MyAsyncTask#3").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        new MyAsyncTask("MyAsyncTask#4").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        new MyAsyncTask("MyAsyncTask#5").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        Log.d(TAG, "Thread Executor info = " + AsyncTask.THREAD_POOL_EXECUTOR.toString());
    }

    public void startIntentService(View view) {
        Intent intent = new Intent(this, LocalIntentService.class);
        intent.putExtra("task_action", "com.countbunny.action.TASK1");
        startService(intent);
        intent.putExtra("task_action", "com.countbunny.action.TASK2");
        startService(intent);
        intent.putExtra("task_action", "com.countbunny.action.TASK3");
        startService(intent);
    }

    public void startAllKindExecutors(View view) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        fixedThreadPool.execute(command);

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(command);

        ScheduledExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(4);
        //2 秒后执行 command
        scheduleThreadPool.schedule(command, 2000, TimeUnit.MILLISECONDS);
        // 10ms后，每隔1秒执行一次command
        scheduleThreadPool.scheduleAtFixedRate(command, 10, 1000, TimeUnit.MILLISECONDS);

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(command);
    }

    private static class MyAsyncTask extends AsyncTask<String, Integer, String> {

        private String mName = "AsyncTask";

        public MyAsyncTask(String name) {
            super();
            mName = name;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Log.d(TAG, "current thread name = " + Thread.currentThread().getName());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mName;
        }

        @Override
        protected void onPostExecute(String s) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.e(TAG, s + " execute finish at " + df.format(new Date()));
        }
    }

    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Long aLong) {
            showDialog("Download " + aLong + " bytes");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            setProgressPercent(values[0]);
        }

        @Override
        protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalSize = 0;
            for (int i = 0; i < count; i++) {
                totalSize += Downloader.downloadFile(urls[i]);
                publishProgress((int) ((i + 1) / (float) count * 100));
                if (isCancelled()) {
                    break;
                }
            }
            return totalSize;
        }
    }

    private void setProgressPercent(int value) {
        Log.d("ThreadTestActivity", "onProgressUpdated value = " + value);
        mProgressBar.setProgress(value);
    }

    private void showDialog(String s) {
        if (null == mDialog) {
            mDialog = new AlertDialog.Builder(this)
                    .setIcon(R.mipmap.ic_launcher_round)
                    .setCancelable(true)
                    .setTitle("Download Mission Complete!")
                    .create();
        }
        ((AlertDialog) mDialog).setMessage(s);
        mDialog.show();
    }

}
