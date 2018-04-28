package com.example.countbunny.launchmodetest1.anims;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.example.countbunny.launchmodetest1.R;

public class AnimActivity extends AppCompatActivity {

    private ViewGroup mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        initView();
    }

    private void initView() {
        mContainer = findViewById(R.id.container);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_item);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        mContainer.setLayoutAnimation(controller);
    }

    public void addItem(View view) {
        TextView textView = new TextView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()));
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setText("i am no." + mContainer.getChildCount() + " child for this container");
        Animation a = mContainer.getLayoutAnimation().getAnimationForView(textView);
        textView.setAnimation(a);
        mContainer.addView(textView);
        a.start();
    }

    public void changeWidth1(View view) {
        performAnimate(view);
    }

    private void performAnimate(View view) {
        ViewWrapper wrapper = new ViewWrapper(view);
        ObjectAnimator.ofInt(wrapper, "width", 500).setDuration(5000).start();
    }

    public void changeWidth2(View view) {
        performAnimate(view, 200, 600);
    }

    private void performAnimate(final View target, final int start, final int end) {
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (Integer) animation.getAnimatedValue();
                float fraction = animation.getAnimatedFraction();
                target.getLayoutParams().width = mEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });
        valueAnimator.setDuration(5000).start();
    }

    private static class ViewWrapper {

        private View mTarget;

        public ViewWrapper(View target) {
            mTarget = target;
        }

        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }
    }
}
