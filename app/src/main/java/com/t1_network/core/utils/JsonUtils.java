package com.t1_network.core.utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 功能：封装了JSON的读取方式，以安全的方式读取Json中的数据，即先判断是否有此键值对，再进行读取
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-10-30
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class JsonUtils {


    public static void put(JSONObject json, String key, String value) {
        if (json == null) {
            return;
        }

        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            return;
        }

        try {
            json.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 特定的方法，用于打包网络请求时header中user参数的JSON串
     * * @param id
     *
     * @param uuid
     * @return
     * @throws Exception
     */
    public static String packageString(String id, String uuid) throws Exception {
        JSONObject user = new JSONObject();
        user.put("id", id);
        user.put("uuid", uuid);

        String str = user.toString();

        str = str.replace("\"", "\\\"");

        LogUtils.e("haha:" + str);
        return user.toString();
    }


    /**
     * 根据json-Key值 取Value值 返回String
     *
     * @param jsonObject
     * @param keyName
     * @return 返回String
     */
    public static String getString(JSONObject jsonObject, String keyName) {

        try {

            if (jsonObject.has(keyName)) {
                return jsonObject.getString(keyName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 根据json-Key值 取Value值 返回Long
     *
     * @param jsonObject
     * @param keyName
     * @return 返回Long
     */
    public static long getLong(JSONObject jsonObject, String keyName) {

        try {

            if (jsonObject.has(keyName)) {
                return jsonObject.getLong(keyName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 根据json-Key值 取Value值 返回Int
     *
     * @param jsonObject
     * @param keyName
     * @return 返回Int
     */
    public static int getInt(JSONObject jsonObject, String keyName) {

        try {

            if (jsonObject.has(keyName)) {
                return jsonObject.getInt(keyName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 根据json-Key值 取Value值 返回Boolean
     *
     * @param jsonObject
     * @param keyName
     * @return 返回Boolean
     */
    public static boolean getBoolean(JSONObject jsonObject, String keyName) {

        try {

            if (jsonObject.has(keyName)) {
                return jsonObject.getBoolean(keyName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 根据json-Key值 取Value值 返回Double
     *
     * @param jsonObject
     * @param keyName
     * @return 返回Double
     */
    public static double getDouble(JSONObject jsonObject, String keyName) {

        try {

            if (jsonObject.has(keyName)) {
                return jsonObject.getDouble(keyName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 根据json-Key值 取Value值 返回Object
     *
     * @param jsonObject
     * @param keyName
     * @return 返回Object
     */
    public static JSONObject getJSONObject(JSONObject jsonObject, String keyName) {

        try {

            if (jsonObject.has(keyName)) {
                return jsonObject.getJSONObject(keyName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据json-Key值 取Value值 返回数组
     *
     * @param jsonObject
     * @param keyName
     * @return 返回数组
     */
    public static JSONArray getJSONArray(JSONObject jsonObject, String keyName) {

        try {

            if (jsonObject.has(keyName)) {
                return jsonObject.getJSONArray(keyName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JSONArray();
    }


}
