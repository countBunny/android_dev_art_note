package com.example.countbunny.launchmodetest1.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
    public static long downloadFile(URL url) {
        ByteArrayOutputStream bo = null;
        InputStream is = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                bo = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int readSize = is.read(bytes);
                while (readSize != -1) {
                    bo.write(bytes, 0, readSize);
                    readSize = is.read(bytes);
                }
                bo.flush();
                byte[] result = bo.toByteArray();
                return result.length;
            } else {
                Log.e("Downloader", "connect failed response code is " + conn.getResponseCode() +
                        " Message from server is " + conn.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            MyUtils.close(bo);
            MyUtils.close(is);
        }
        return 0;
    }
}
