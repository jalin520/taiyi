package com.t1_network.core.controller;

import android.view.View;
import android.widget.AdapterView;

/**
 * 功能：RecyclerView每个Item的点击事件
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-10-26
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class BasicItemLongClickListener implements View.OnLongClickListener {

    /**
     * AdapterView的通用item点击监听器
     */
    private AdapterView.OnItemLongClickListener onItemLongClickListener;

    /**
     * RecyclerView的抽象ViewHolder
     */
    private BasicHolder holder;

    public BasicItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener, BasicHolder holder) {
        this.onItemLongClickListener = onItemLongClickListener;
        this.holder = holder;
    }

    /**
     * 处理点击事件，此处调用AdapterView.onItemLongClickListener的点击事件
     *
     * @param v
     */
    @Override
    public boolean onLongClick(View v) {
        return onItemLongClickListener.onItemLongClick(null, v, holder.getPosition(), holder.getItemId());
    }
}
