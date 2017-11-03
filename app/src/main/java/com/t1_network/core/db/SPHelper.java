package com.t1_network.core.db;

import android.app.Activity;
import android.content.SharedPreferences;

import com.t1_network.core.app.App;
import com.t1_network.core.app.Build;

/**
 * 功能：SharedPerference的封装，通过save直接保存数据，通过get*方法获取对应的值
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-10-26
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class SPHelper {

    /**
     * SharedPerference存放的路径，此处统一归Build配置文件管理
     */
    private static String FILE_NAME = Build.SP_PATH;

    // 默认值
    private static int defaultInt = 0;
    private static String defaultString = "";
    private static float defaultFloat = 0.0f;
    private static long defaultLong = 0;
    private static boolean defaultBoolean = false;

    /**
     * 设置保存SP的名字
     *
     * @param fileName
     */
    public static void setFileName(String fileName) {
        FILE_NAME = fileName;
    }

    /**
     * 保存int类型
     *
     * @param name
     * @param data
     */
    public static void save(String name, int data) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(name, data);
        edit.commit();
    }


    public static void removeSP(String key){
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        edit.commit();
    }

    /**
     * 保存String类型
     *
     * @param name
     * @param data
     */
    public static void save(String name, String data) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(name, data);
        edit.commit();
    }

    /**
     * 保存float类型
     *
     * @param name
     * @param data
     */
    public static void save(String name, float data) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat(name, data);
        edit.commit();
    }

    /**
     * 保存long类型
     *
     * @param name
     * @param data
     */
    public static void save(String name, long data) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(name, data);
        edit.commit();
    }

    /**
     * 保存boolean类型
     *
     * @param name
     * @param data
     */
    public static void save(String name, boolean data) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(name, data);
        edit.commit();
    }

    /**
     * 获取boolean类型
     *
     * @param name
     * @return
     */
    public static boolean getBoolean(String name) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        return sp.getBoolean(name, defaultBoolean);
    }

    /**
     * 获取boolan类型，并指定默认值
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(String name,
                                     boolean defaultValue) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        return sp.getBoolean(name, defaultValue);
    }

    /**
     * 获取 int 类型
     *
     * @param name
     * @return
     */
    public static int getInt(String name) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        return sp.getInt(name, defaultInt);
    }

    /**
     * 获取int类型，并指定默认值
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static int getInt(String name, int defaultValue) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        return sp.getInt(name, defaultValue);
    }

    /**
     * 获取 String 类型
     *
     * @param name
     * @return
     */
    public static String getString(String name) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        return sp.getString(name, defaultString);
    }

    /**
     * 获取 String 类型，并指定默认值
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getString(String name,
                                   String defaultValue) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        return sp.getString(name, defaultValue);
    }

    /**
     * 获取 long 类型
     *
     * @param name
     * @return
     */
    public static long getLong(String name) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        return sp.getLong(name, defaultLong);
    }

    /**
     * 获取 long 类型，并指定默认值
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static long getLong(String name, long defaultValue) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        return sp.getLong(name, defaultValue);
    }

    /**
     * 获取 float 类型
     *
     * @param name
     * @return
     */
    public static float getFloat(String name) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        return sp.getFloat(name, defaultFloat);
    }

    /**
     * 获取 float 类型，并指定默认值
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static float getFloat(String name,
                                 float defaultValue) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        return sp.getFloat(name, defaultValue);
    }

    /**
     * 设置默认值
     *
     * @return
     */
    public static int getDefaultInt() {
        return defaultInt;
    }

    public static String getDefaultString() {
        return defaultString;
    }

    public static float getDefaultFloat() {
        return defaultFloat;
    }

    public static long getDefaultLong() {
        return defaultLong;
    }

    public static String getFILE_NAME() {
        return FILE_NAME;
    }

    public static void setFILE_NAME(String FILE_NAME) {
        SPHelper.FILE_NAME = FILE_NAME;
    }

    public static void setDefaultInt(int defaultInt) {
        SPHelper.defaultInt = defaultInt;
    }

    public static void setDefaultString(String defaultString) {
        SPHelper.defaultString = defaultString;
    }

    public static void setDefaultFloat(float defaultFloat) {
        SPHelper.defaultFloat = defaultFloat;
    }

    public static void setDefaultLong(long defaultLong) {
        SPHelper.defaultLong = defaultLong;
    }
}
