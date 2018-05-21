package com.example.countbunny.optimize;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.PopupWindowCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.example.countbunny.launchmodetest1.R;

public class MemoryHoldWindow {

    private static Context sContext;

    public static void pop(Context context) {
        sContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.window_hint, null);
        PopupWindow windowCompat = new PopupWindow(view);
        windowCompat.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, context.getResources().getDisplayMetrics()));
        windowCompat.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, context.getResources().getDisplayMetrics()));
//        windowCompat.setAnimationStyle(R.style.);
        windowCompat.setBackgroundDrawable(null);
        windowCompat.setFocusable(true);
        windowCompat.setOutsideTouchable(true);
        if (context instanceof PopupWindow.OnDismissListener) {
            windowCompat.setOnDismissListener((PopupWindow.OnDismissListener) context);
        }
        windowCompat.showAtLocation(windowCompat.getContentView(), Gravity.CENTER, 0, 0);
    }


}
