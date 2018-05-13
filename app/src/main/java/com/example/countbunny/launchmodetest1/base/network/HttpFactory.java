package com.example.countbunny.launchmodetest1.base.network;

public class HttpFactory {

    private static volatile HttpFactory sInstance;

    private IHttpRequest mRequest;

    private HttpFactory() {
        mRequest = new OkRequest();
    }

    public static final IHttpRequest getHttpRequest(){
        if (null == sInstance) {
            synchronized (HttpFactory.class) {
                if (null == sInstance) {
                    sInstance = new HttpFactory();
                }
            }
        }
        return sInstance.mRequest;
    }
}
