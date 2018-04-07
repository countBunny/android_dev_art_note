package com.example.countbunny.launchmodetest1.touchconflict;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by countBunny on 2018/4/7.
 */

public class ListViewEx extends ListView {

    public final static String TAG = "ListViewEx";

    private HorizontalScrollViewEx2 mHorizontalScrollViewEx2;

    private int mLastX = 0;
    private int mlastY = 0;

    public ListViewEx(Context context) {
        super(context);
    }

    public ListViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ListViewEx(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //收到down 事件要求父容器不要拦截后续事件
                mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mlastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    //一旦x滑动方向的位移大于y的，要求父元素拦截事件流
                    mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(false);
                }
                break;

            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    public void setInterceptParent(HorizontalScrollViewEx2 listContainer) {
        mHorizontalScrollViewEx2 = listContainer;
    }
}
