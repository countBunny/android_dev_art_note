package com.example.countbunny.launchmodetest1.aidl.binderpool;

import android.os.RemoteException;

/**
 * Created by countBunny on 2018/3/25.
 */

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
