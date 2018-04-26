package com.example.countbunny.launchmodetest1.anims;

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
}
