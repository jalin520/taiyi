package com.t1_network.core.utils;

import android.text.TextUtils;

/**
 * Created by chenyu on 2015/11/10.
 */
public class SpanStringUtils {
    /**
     * 起始位置
     */
    private static int start;
    /**
     * 结束位置
     */
    private static int end;

    /**
     * 将传入的字符串中某一指定部分转变为高亮
     *
     * @param color    欲转变为高亮的颜色
     * @param message  文本内容
     * @param valuse   欲转变为高亮的文本
     * @return
     */
    public static CharSequence getHighLightText(int color,String message,String valuse) {
        if(TextUtils.isEmpty(message)){
            return "";
        }
        if(TextUtils.isEmpty(valuse)){
            return "";
        }
        start = message.indexOf(valuse);
        end = valuse.length() + start;
        CharSequence cs = StringUtils.getHighLightText(message, color, start, end);
        return cs;
    }
}
