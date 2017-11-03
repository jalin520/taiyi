package com.t1_network.core.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * body有几种情况:<br/>
 * 1.JSONObject对象
 * 2.JSONArray
 * 3.自定义报文
 * <p>
 * header情况如下:<br/>
 * 1.固定header,如平台信息
 * 2.动态header,如用户信息
 */
public class BasicRequest extends JsonObjectRequest {

    public BasicAPI api;

    public BasicRequest(BasicAPI api, Response.Listener<JSONObject> successlistener, Response.ErrorListener errorListener) {
        super(api.getHttpType(), api.getURL(), null, successlistener, errorListener);
        this.api = api;
    }

    /**
     * 重写getHeaders，把Header需要的数据在此处填入
     *
     * @return
     * @throws AuthFailureError
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return api.getHeader();
    }

    @Override
    public byte[] getBody() {
        if (api.getBody() == null) {
            return super.getBody();
        }
        return api.getBody();
    }

    /**
     * 重写parseNetworkResponse方法，将返回的数据格式化位UTF-8
     *
     * @param response
     * @return
     */
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String je = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            String temp = new String(response.data, "UTF-8");

            return Response.success(new JSONObject(temp), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException var3) {
            return Response.error(new ParseError(var3));
        } catch (JSONException var4) {
            return Response.error(new ParseError(var4));
        }
    }


    @Override
    public String getBodyContentType() {

        if (!"".equals(api.getBodyContentType())) {
            return api.getBodyContentType();
        }
        return super.getBodyContentType();

    }
}
