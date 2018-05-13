package com.example.countbunny.launchmodetest1.utils;

/**
 * Created by countBunny on 2018/4/21.
 */

public interface IConstant {
    String REMOTE_ACTION = "com.example.countbunny.launchmodetest1.remote_view_send_here";

    String EXTRA_REMOTE_VIEWS = "remote_view_extra";

    int IO_BUFFERED_SIZE = 1024;

    interface RequestCode {

        int WINDOW_MODIFY = 0x100;
        int OPERATE_EXTERNAL_STOTRAGE = 0x101;
    }
}
