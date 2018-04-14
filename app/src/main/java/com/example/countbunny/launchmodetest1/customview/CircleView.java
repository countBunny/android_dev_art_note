package com.example.countbunny.launchmodetest1.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.countbunny.launchmodetest1.R;

/**
 * Created by countBunny on 2018/4/14.
 */

public class CircleView extends View {

    private int mColor = Color.RED;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mDefaultSize;

    public CircleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPaint.setColor(mColor);
        mDefaultSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200,
                getContext().getResources().getDisplayMetrics());
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mColor = a.getColor(R.styleable.CircleView_cvColor, Color.RED);
        a.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = mDefaultSize;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = mDefaultSize;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int paddingStart = getPaddingStart();
        final int paddingEnd = getPaddingEnd();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingStart - paddingEnd;
        int height = getHeight() - paddingBottom - paddingTop;
        int radius = Math.min(width, height) / 2;
        canvas.drawCircle(paddingStart + width / 2, paddingTop + height / 2, radius, mPaint);
    }
}
