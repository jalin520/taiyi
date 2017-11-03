package com.t1_network.core.controller;

import android.view.View;

import butterknife.ButterKnife;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/11 12:14
 * 修改记录:
 */
public class BasicListHolder {

    /**
     * 自定义的ViewHolder类，参考ListView的ViewHolder
     */
    private ViewHolder viewHolder;
    public View itemView;

    public ViewHolder getViewHolder() {
        return viewHolder;
    }

    public BasicListHolder(View itemView) {

        ButterKnife.bind(this, itemView);//进行注解式框架的声明
        this.itemView = itemView;
        viewHolder = ViewHolder.getViewHolder(itemView);
    }

}
