package com.example.countbunny.launchmodetest1.base.network;

import java.util.Map;

public interface IHttpRequest {

    void get(String url, Class clz, Map<String, String> params, Listener listener);

    interface Listener<T> {

        public void onSuccess(T result);

        public void onFailed(String failMessage);

    }

    abstract class Simple implements IHttpRequest {

        @Override
        public void get(String url, Class clz, Map<String, String> params, Listener listener) {
            if (null != params && !params.isEmpty()) {
                StringBuilder sb = new StringBuilder(url);
                sb.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
                url = sb.toString();
            }
        }
    }
}
