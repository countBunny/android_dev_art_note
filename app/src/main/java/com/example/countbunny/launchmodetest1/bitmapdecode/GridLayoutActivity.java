package com.example.countbunny.launchmodetest1.bitmapdecode;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.GridView;

import com.example.countbunny.launchmodetest1.R;
import com.example.countbunny.launchmodetest1.base.Apis;
import com.example.countbunny.launchmodetest1.base.network.HttpFactory;
import com.example.countbunny.launchmodetest1.base.network.IHttpRequest;
import com.example.countbunny.launchmodetest1.utils.IConstant;

public class GridLayoutActivity extends AppCompatActivity {

    private static final String TAG = "GridLayoutActivity";

    private GridView mGridView;

    private ImageAdapter mAdapter;

    private int mPage = 1;

    private IHttpRequest.Call mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_layout);
        initView();
        if (ContextCompat.checkSelfPermission
                (this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //需要申请权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //需要征求用户意见后申请权限
                Log.w(TAG, "you should ask user to decide whether grant it");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, IConstant.RequestCode.OPERATE_EXTERNAL_STOTRAGE);
        } else {
            // load data
            loadData();
        }
    }

    private void initView() {
        mGridView = findViewById(R.id.gridView);
        mAdapter = new ImageAdapter();
        mGridView.setAdapter(mAdapter);
        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    mAdapter.setGridViewIdle(true);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter.setGridViewIdle(false);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mCall != null && !mCall.isExecuted()) {
                    //still requesting
                    return;
                }
                if (totalItemCount - 5 < firstVisibleItem + visibleItemCount) {
                    //loadMore
                    if (mAdapter.isCanGetBitmapFromNetwork()&&mAdapter.isGridViewIdle()) {
                        mPage++;
                        realLoad();
                    }
                }
            }
        });
    }

    private void loadData() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (null != info) {
            if (info.getType() != ConnectivityManager.TYPE_WIFI) {
                //使用的并非Wifi网络
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("初次使用会从网络中下载大概5M的图片，确认下载吗？");
                builder.setTitle("注意");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.setCanGetBitmapFromNetwork(true);
                        mAdapter.notifyDataSetChanged();
                        realLoad();
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            } else {
                mAdapter.setCanGetBitmapFromNetwork(true);
                realLoad();
            }
        }
    }

    private void realLoad() {
        final int page = mPage;
        mCall = HttpFactory.getHttpRequest()
                .get(Apis.ImageApis.getApis().getWelfares(page, 20), BeautyRootBean.class, null,
                        new IHttpRequest.Listener<BeautyRootBean>() {
                            @Override
                            public void onSuccess(final BeautyRootBean result) {
                                if (!result.error) {
                                    mGridView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (page > 1) {
                                                mAdapter.getData().addAll(result.results);
                                                mAdapter.notifyDataSetChanged();
                                            } else {
                                                mAdapter.setData(result.results);
                                            }
                                        }
                                    });
                                } else {
                                    if (mPage>1) {
                                        mPage--;
                                    }
                                }
                            }

                            @Override
                            public void onFailed(String failMessage) {
                                if (mPage>1) {
                                    mPage--;
                                }
                                Log.e(TAG, failMessage);
                            }
                        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case IConstant.RequestCode.OPERATE_EXTERNAL_STOTRAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult 取得外部存储操作权限。");
                    //load data
                    loadData();
                } else {
                    Log.d(TAG, "onRequestPermissionsResult 用户没有授权。");
                }
                break;
        }
    }
}
