package com.example.countbunny.launchmodetest1.bitmapdecode;

import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.countbunny.launchmodetest1.R;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private List<PhotoBean> mData;

    private boolean mCanGetBitmapFromNetwork;
    private boolean mIsGridViewIdle;

    private int mImageWidth;

    public List<PhotoBean> getData() {
        return mData;
    }

    public void setData(List<PhotoBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public boolean isCanGetBitmapFromNetwork() {
        return mCanGetBitmapFromNetwork;
    }

    public void setCanGetBitmapFromNetwork(boolean canGetBitmapFromNetwork) {
        mCanGetBitmapFromNetwork = canGetBitmapFromNetwork;
    }

    public boolean isGridViewIdle() {
        return mIsGridViewIdle;
    }

    public void setGridViewIdle(boolean gridViewIdle) {
        mIsGridViewIdle = gridViewIdle;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_for_gridview, parent, false);
            vh = new VH();
            vh.mImageView = (AppCompatImageView) convertView;
            convertView.setTag(vh);
        } else {
            vh = (VH) convertView.getTag();
        }
        ImageView img = vh.mImageView;
        final String tag = (String) img.getTag();
        final String url = mData.get(position).getDownloadUrl();
        if (!url.equals(tag)) {
            img.setImageDrawable(null);
        }
        if (mIsGridViewIdle&&mCanGetBitmapFromNetwork) {
            img.setTag(url);
            ImageLoader.getInstance().bindBitmap(url, img, mImageWidth, mImageWidth);
        }
        return convertView;
    }

    private static class VH {

        private AppCompatImageView mImageView;
    }
}
