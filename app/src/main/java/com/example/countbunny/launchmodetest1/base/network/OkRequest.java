package com.example.countbunny.launchmodetest1.base.network;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * please obtain this from HttpFactory
 */
public class OkRequest extends IHttpRequest.Simple {

    private OkHttpClient mClient;

    private static final String TAG = "OkRequest";

    private interface Configuration {
        int READ_TIMEOUT = 10;
        int WRITE_TIMEOUT = 10;
        boolean NEED_RETRY = true;
    }

    OkRequest() {
        mClient = new OkHttpClient.Builder()
                .connectTimeout(Configuration.READ_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Configuration.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Configuration.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(Configuration.NEED_RETRY)
                .build();
    }

    @Override
    public <T> Call get(String url, final Class<T> clz, Map<String, String> params, final Listener<T> listener) {
        super.get(url, clz, params, listener);
        //param is handled by super
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        okhttp3.Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                if (null == listener) {
                    return;
                }
                listener.onFailed(call.toString() + " error=\n" + e);
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (null == listener || null == clz) {
                    return;
                }
                if (response.code() == 200) {
                    String result = response.body().string();
                    Log.d(TAG, "result = " + result);
                    T t = new Gson().fromJson(result, clz);
                    listener.onSuccess(t);
                } else {
                    listener.onFailed(call.toString() + " error=\n" + response.code()
                            + " message=" + response.body().string());
                }
            }
        });
        return OkCall.getInstance(call);
    }
}
