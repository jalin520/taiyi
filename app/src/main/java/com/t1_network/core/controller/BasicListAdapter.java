package com.t1_network.core.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/11 11:30
 * 修改记录:
 */
public abstract class BasicListAdapter extends BaseAdapter {


    /**
     * 返回的数据
     */
    public List<?> data;
    /**
     * item的布局文件
     */
    public int itemLayoutId;
    public ImageLoader imageLoader;
    public Context context;
    private View view;

    public ViewHolder holder;

    public BasicListAdapter(List<?> data, int itemLayoutId) {
        this.data = data;
        this.itemLayoutId = itemLayoutId;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(itemLayoutId, parent, false);

            holder = ViewHolder.getViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        bind(holder, position);

        //动画效果
        AlphaAnimation animation = new AlphaAnimation(0.6f,1f);
        animation.setDuration(1120);
        convertView.startAnimation(animation);

        return convertView;
    }

    public abstract void bind(ViewHolder holder, int position);

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public View getView() {
        return view;
    }
}
