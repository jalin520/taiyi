package com.t1_network.core.utils;


import android.util.TypedValue;

import com.t1_network.core.app.App;

/**
 * 功能：获取资源的快捷方式，根据资源id直接获取
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-10-15
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class ResUtils {

    /**
     * 获取字符串
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return App.getContext().getResources().getString(resId);
    }

    public static String getString(int id, Object... args) {
        return String.format(App.getContext().getResources().getString(id), args);
    }

    /**
     * 获取颜色
     *
     * @param resId
     * @return
     */
    public static int getColor(int resId) {
        return App.getContext().getResources().getColor(resId);
    }


    public static float getDimen(int resId) {
        return App.getContext().getResources().getDimension(resId);
    }

    public static int getAttr(int resId) {
        TypedValue typedValue = new TypedValue();
        App.getContext().getTheme().resolveAttribute(resId, typedValue, true);
        return typedValue.density;
    }


}
