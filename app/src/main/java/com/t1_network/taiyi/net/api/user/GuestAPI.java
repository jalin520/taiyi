package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/2/16 11:22
 * 修改记录:
 */
public class GuestAPI extends TYAPI {

    private GuestAPIListener listener;


    public GuestAPI(GuestAPIListener listener) {
        this.listener = listener;
        
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiGuestFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {

        try {

            JSONObject json = new JSONObject(data);
            String guestId = json.getString("guest");
            listener.apiGuestSuccess(guestId);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public String getURL() {
        return Build.HOST + "consumer/guest";
    }

    public interface GuestAPIListener {

        public void apiGuestSuccess(String guestId);

        public void apiGuestFailure(long code, String msg);

    }


}
