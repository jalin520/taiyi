package com.t1_network.core.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * 功能：封装了RecyclerView的Adapter，并在内部通过ButterKnife进行注解
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-10-26
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class BasicHolder extends RecyclerView.ViewHolder {

    /**
     * 自定义的ViewHolder类，参考ListView的ViewHolder
     */
    private ViewHolder viewHolder;
    public View itemView;
    public ViewHolder getViewHolder() {
        return viewHolder;
    }

    public BasicHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);//进行注解式框架的声明
        this.itemView = itemView;
        viewHolder = ViewHolder.getViewHolder(itemView);
    }
}
