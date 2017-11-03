package com.t1_network.core.utils;

import android.content.Context;
import android.view.WindowManager;

import com.t1_network.core.app.App;

/**
 * 功能：UI相关工具类，如获取屏幕宽度等
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-10-15
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class UIUtils {

    public static int dp2Px(double dp) {
        final float scale = App.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2Dp(int px) {
        final float scale = App.getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int getWidthByPx() {
        WindowManager wm = (WindowManager) App.getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int height = wm.getDefaultDisplay().getHeight();
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getHeightPx() {
        WindowManager wm = (WindowManager) App.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    //执行延时任务
    public static void postDelayed(Runnable task, int delayedTime) {
        App.getMainHandler().postDelayed(task,delayedTime);

    }

    public static void removeCallBacks(Runnable task) {
        App.getMainHandler().removeCallbacks(task);
    }
}


