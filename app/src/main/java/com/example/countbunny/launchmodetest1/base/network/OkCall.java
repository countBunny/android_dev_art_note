package com.example.countbunny.launchmodetest1.base.network;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class OkCall implements IHttpRequest.Call {

    private Call mCall;

    private OkCall(Call call) {
        mCall = call;
    }

    public static OkCall getInstance(Call call) {
        return new OkCall(call);
    }

    @Override
    public boolean cancel() {
        if (mCall.isCanceled() || mCall.isExecuted()) {
            return true;
        }
        mCall.cancel();
        return false;
    }

    @Override
    public <T> void execute(Class<T> clz, IHttpRequest.Listener<T> listener) {
        if (mCall.isExecuted() || mCall.isCanceled()) {
            return;
        }
        final Call old = mCall;
        mCall = mCall.clone();
        //cancel from queue
        old.cancel();
        try {
            Response response = mCall.execute();
            if (null == listener || null == clz) {
                return;
            }
            if (response.code() == 200) {
                T t = new Gson().fromJson(response.body().string(), clz);
                listener.onSuccess(t);
            } else {
                listener.onFailed(toString() + " error=\n" + response.code()
                        + " message=" + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isExecuted() {
        return mCall.isExecuted() || mCall.isCanceled();
    }
}
