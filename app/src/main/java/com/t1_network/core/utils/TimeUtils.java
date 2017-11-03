package com.t1_network.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/18 12:57
 * 修改记录:
 */
public class TimeUtils {


    public static String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

}
