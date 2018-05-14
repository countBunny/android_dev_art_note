package com.example.countbunny.launchmodetest1.base.network;

import java.util.Map;

public interface IHttpRequest {

    <T> Call get(String url, Class<T> clz, Map<String, String> params, Listener<T> listener);

    interface Listener<T> {

        public void onSuccess(T result);

        public void onFailed(String failMessage);

    }

    interface Call{

        boolean cancel();

        <T> void execute(Class<T> clz, Listener<T> listener);

        boolean isExecuted();
    }

    abstract class Simple implements IHttpRequest {

        @Override
        public <T> Call get(String url, Class<T> clz, Map<String, String> params, Listener<T> listener) {
            if (null != params && !params.isEmpty()) {
                StringBuilder sb = new StringBuilder(url);
                sb.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
                url = sb.toString();
            }
            //no necessary to give an actually call
            return null;
        }
    }
}
