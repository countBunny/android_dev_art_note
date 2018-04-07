package com.example.countbunny.launchmodetest1.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by countBunny on 2018/4/7.
 */

public class StickyLayout extends LinearLayout {

    private static final int STATUS_EXPANDED = 1;
    private static final int STATUS_COLLAPSED = 2;

    private int mTouchSlop;

    private int mLastX = 0;
    private int mLastY = 0;

    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    private boolean mDisallowInterceptTouchEventOnHeader;
    private boolean mIsSticky = false;
    private int mStatus;
    private int mHeaderHeight;
    private int mOriginalHeaderHeight;

    private GiveUpTouchEventListener mGiveUpTouchEventListener;

    public StickyLayout(Context context) {
        super(context);
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StickyLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int intercepted = 0;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastXIntercept = x;
                mLastYIntercept = y;
                mLastX = x;
                mLastY = y;
                intercepted = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if(mDisallowInterceptTouchEventOnHeader && y<= getHeaderHeight()){
                    intercepted = 0;
                } else if (Math.abs(deltaX) >= Math.abs(deltaY)) {
                    intercepted = 0;
                } else if (mStatus == STATUS_EXPANDED && deltaY <= -mTouchSlop) {
                    intercepted = 1;
                } else if (mGiveUpTouchEventListener != null) {
                    if (mGiveUpTouchEventListener.giveUpTouchEvent(ev)&& deltaY>= mTouchSlop) {
                        intercepted = 1;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = 0;
                mLastXIntercept = mLastYIntercept = 0;
                break;
            default:
                break;
        }
        return intercepted!=0 && mIsSticky;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mIsSticky) {
            return true;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                mHeaderHeight += deltaY;
                setHeaderHeight(mHeaderHeight);
                break;
            case MotionEvent.ACTION_UP:
                //松手时向两边移动 根据所在的位置确定向哪边移动
                int destHeight = 0;
                if (mHeaderHeight <= mOriginalHeaderHeight * .5f) {
                    //收缩header
                    destHeight = 0;
                    mStatus = STATUS_COLLAPSED;
                } else {
                    destHeight = mOriginalHeaderHeight;
                    mStatus = STATUS_EXPANDED;
                }
                smoothSetHeaderHeight(mHeaderHeight, destHeight, 500);
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothSetHeaderHeight(int headerHeight, int destHeight, int duration) {

    }

    private void setHeaderHeight(int height) {

    }

    private int getHeaderHeight() {
        return mHeaderHeight;
    }

    public interface GiveUpTouchEventListener {
        boolean giveUpTouchEvent(MotionEvent event);
    }
}
