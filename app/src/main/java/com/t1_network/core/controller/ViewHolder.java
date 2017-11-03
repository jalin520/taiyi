package com.t1_network.core.controller;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 功能：自定义了ViewHolder，可根据资源id获取View
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-10-26
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */

public class ViewHolder {
    /**
     * 使用稀疏数组存放View
     */
    private SparseArray<View> viewHolder;

    /**
     * 通过item的资源文件实例化出来的View
     */
    private View view;

    public static ViewHolder getViewHolder(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        return viewHolder;
    }

    /**
     * 私有的构造方法，实例化了稀疏数组，并把它设置为View的Tag
     *
     * @param view
     */
    private ViewHolder(View view) {
        this.view = view;
        viewHolder = new SparseArray<View>();
        view.setTag(viewHolder);
    }

    /**
     * 根据资源id获取View，如果viewHolder数组中没有，则在View中查找并放入viewHolder数组
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T get(int id) {
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    /**
     * 获取重用的view
     *
     * @return
     */
    public View getConvertView() {
        return view;
    }

    /**
     * 封装上面的get方法，调用
     *
     * @param id
     * @return
     */
    public TextView getTextView(int id) {
        return get(id);
    }

    public Button getButton(int id) {
        return get(id);
    }

    public ImageView getImageView(int id) {
        return get(id);
    }

    public void setTextView(int id, CharSequence charSequence) {
        getTextView(id).setText(charSequence);
    }
}
