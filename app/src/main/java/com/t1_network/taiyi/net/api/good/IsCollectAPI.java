package com.t1_network.taiyi.net.api.good;

import com.t1_network.core.app.Build;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/28 16:37
 * 修改记录:
 */
public class IsCollectAPI extends TYAPI {

    private IsCollectAPIListener listener;

    public IsCollectAPI(IsCollectAPIListener listener, String id) {
        this.listener = listener;

        addUserAuthorization();
        addParams("id", id);

        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiIsCollectFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {

        try {
            JSONObject json = new JSONObject(data);

            boolean isCollect = json.getBoolean("collection");

            listener.apiIsCollectSuccess(isCollect);

        } catch (JSONException e) {
            e.printStackTrace();
            listener.apiIsCollectFailure(0, ResUtils.getString(R.string.error_reslove));
        }

    }

    @Override
    public String getURL() {
        return Build.HOST + "home/iscollect";
    }


    public interface IsCollectAPIListener {

        public void apiIsCollectSuccess(boolean isCollect);

        public void apiIsCollectFailure(long code, String msg);


    }

}

