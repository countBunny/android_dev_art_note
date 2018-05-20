package com.example.countbunny.launchmodetest1.jnindk;

public class JniTest {

    {
        System.load("/Users/countbunny/StudioProjects/android_dev_art_note/app/src/main/java/jni/libjni-test.so");
//        System.loadLibrary("jni-test");
    }

    public static void main(String args[]) {
        JniTest jniTest = new JniTest();
        System.out.println(jniTest.get());
        jniTest.set("hello world");
    }

    public native String get();

    public native void set(String str);
}
