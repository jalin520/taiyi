package com.t1_network.core.net;


import android.text.TextUtils;

import com.android.volley.Request;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * body此处有3种情况:
 * <p>
 * 1.纯JSONObject对象,如 {"mobile":"15680030824"},直接填在params对象中;
 * <p>
 * 2.JSONArray对象,既JSONObject数组,如 [{"mobile":"15680030824"},{"mobile":"15680030824"}],这种情况请将其转化为String后赋值给body字符串中
 * <p>
 * 3.自定义报文,写在body字符串中
 */
public abstract class BasicAPI {

    /**
     * Gson 初始化
     */
    public Gson gson = new Gson();

    /**
     * 让子类重写URL地址
     *
     * @return
     */
    public abstract String getURL();

    /**
     * 如需改变请求方式,重载此方法
     *
     * @return
     */
    public int getHttpType() {
        return Request.Method.POST;
    }

    /**
     * 请求的Body
     */
    public String body = "";

    public byte[] bodyByte=null;

    /**
     * body的ContentType
     */
    public String bodyContentType = "";

    /**
     * 请求的参数
     */
    public JSONObject params = new JSONObject();

    /**
     * 往params中添加参数
     *
     * @param key
     * @param value
     */
    public void addParams(String key, Object value) {

        //进行value的有效性判断
        if (value == null) {
            return;
        }

        if (value instanceof String) {
            if (TextUtils.isEmpty((String) value)) {
                return;
            }
        }

        try {
            params.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
            //进行回调 报错

        }
    }

    /**
     * 获取 API的body
     *
     * @return
     */
    public byte[] getBody() {

        if (bodyByte != null) {
            return bodyByte;
        }

        if (TextUtils.isEmpty(body) && params.length() != 0) {
            return params.toString().getBytes();
        }

        return body.getBytes();
    }


    /**
     * Http请求的header
     */
    public Map<String, String> header = new HashMap<String, String>();

    public Map<String, String> getHeader() {
        return header;
    }

    public abstract void addUserAuthorization();

    public abstract void requestSuccess(String data);

    public abstract void requestError(long code, String msg);

    public String getBodyContentType() {
        return bodyContentType;
    }

    public void setBodyContentType(String bodyContentType) {
        this.bodyContentType = bodyContentType;
    }
}
