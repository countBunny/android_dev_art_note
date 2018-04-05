package com.example.countbunny.launchmodetest1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.countbunny.launchmodetest1.aidl.AidlActivity;
import com.example.countbunny.launchmodetest1.viewevent.ViewEventActivity;

public class MainActivity extends AppCompatActivity {

    private String[] mTestNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        View child = viewGroup.getChildAt(0);
        mTestNames = getResources().getStringArray(R.array.test_name);
        if (null != child && child instanceof RecyclerView) {
            ((RecyclerView) child).setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                    false));
            ((RecyclerView) child).setAdapter(new TestAdapter());
        }
    }

    private class TestAdapter extends RecyclerView.Adapter<VH> {

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_item, parent, false));
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            if (null != mTestNames && position < mTestNames.length) {
                holder.bindData(mTestNames[position]);
            }
        }

        @Override
        public int getItemCount() {
            return null == mTestNames ? 0 : mTestNames.length;
        }
    }

    private static class VH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTestName;

        public VH(View itemView) {
            super(itemView);
            mTestName = (TextView) itemView.findViewById(R.id.testName);
            itemView.setOnClickListener(this);
        }

        public void bindData(String testName) {
            mTestName.setText(testName);
        }

        @Override
        public void onClick(View v) {
            if (getLayoutPosition() == 0) {
                Intent intent = new Intent();
                intent.setClass(itemView.getContext(), SecondActivity.class);
                itemView.getContext().startActivity(intent);
            } else if (getLayoutPosition() == 1) {
                Intent intent = new Intent();
                intent.setClass(itemView.getContext(), AidlActivity.class);
                itemView.getContext().startActivity(intent);
            } else if (getLayoutPosition() == 2) {
                Intent intent = new Intent();
                intent.setClass(itemView.getContext(), ViewEventActivity.class);
                itemView.getContext().startActivity(intent);
            }
        }
    }
}
