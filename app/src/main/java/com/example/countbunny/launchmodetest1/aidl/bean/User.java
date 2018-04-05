package com.example.countbunny.launchmodetest1.aidl.bean;

/**
 * Created by countBunny on 2018/3/18.
 */

public class User {

    public int userId;

    public String userName;

    public boolean isMale;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", isMale=" + isMale +
                '}';
    }
}
