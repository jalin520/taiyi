package com.t1_network.core.net;

import com.android.volley.Response;
import com.t1_network.core.app.App;
import com.t1_network.core.utils.LogUtils;

import org.json.JSONObject;


/**
 * Created by David on 15/12/16.
 */
public abstract class BasicHttp implements Response.Listener<JSONObject>, Response.ErrorListener {

    public BasicAPI api;
    public BasicRequest request;

    public BasicHttp(BasicAPI api) {
        this.api = api;
    }

    public void request() {
        LogUtils.e("[url] " + api.getURL());
        LogUtils.e("[header] " + api.getHeader());
        LogUtils.e("[body] " + new String(api.getBody()));
        request = new BasicRequest(api, this, this);
        App.getQueue().add(request);
    }

}
