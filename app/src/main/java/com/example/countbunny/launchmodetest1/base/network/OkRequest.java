package com.example.countbunny.launchmodetest1.base.network;

import java.util.Map;

public class OkRequest extends IHttpRequest.Simple {

    OkRequest(){

    }

    @Override
    public void get(String url, Class clz, Map<String, String> params, Listener listener) {
        super.get(url, clz, params, listener);
        //param is handled by super

    }
}
