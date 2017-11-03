package com.t1_network.core.controller;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 功能：RecyclerView 的Adapter的基类
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-10-23
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public abstract class BasicAdapter extends RecyclerView.Adapter<BasicHolder> {



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

    /**
     * 监听器
     */
    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemLongClickListener onItemLongClickListener;



    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public BasicAdapter(List<?> data, int itemLayoutId) {
        this.data = data;
        this.itemLayoutId = itemLayoutId;
        imageLoader = ImageLoader.getInstance();
    }

    /**
     * 根据子类传进的item layout文件进行填写
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BasicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(context).inflate(itemLayoutId, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        return new BasicHolder(view);
    }

    @Override
    public void onViewRecycled(BasicHolder holder) {
        super.onViewRecycled(holder);
    }

    /**
     * 绑定id和view，并位itemView设置监听器
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BasicHolder holder, int position) {

        if (data == null) {
            return;
        }

        //显示普通item
        bind(holder.getViewHolder(), position);

        //如果设置了监听器
        if (onItemClickListener != null) {
            //进行监听器的绑定
            holder.itemView.setOnClickListener(new BasicItemClickListener(onItemClickListener, holder));
        }
        //如果设置了监听器
        if (onItemLongClickListener != null) {
            //进行监听器的绑定
            holder.itemView.setOnLongClickListener(new BasicItemLongClickListener(onItemLongClickListener, holder));
        }
    }


    public abstract void bind(ViewHolder holder, int position);

    /**
     * 获取数据数组的长度
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (data != null) {
            //添加一个加载更过的Item
            return data.size();
        }
        return 0;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }




}
