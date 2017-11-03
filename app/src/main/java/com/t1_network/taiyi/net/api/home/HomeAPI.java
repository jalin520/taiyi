package com.t1_network.taiyi.net.api.home;

import com.t1_network.core.app.Build;
import com.t1_network.core.utils.JsonUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/5 17:29
 * 修改记录:
 */
public class HomeAPI extends TYAPI {

    private HomeAPIListener listener;

    public HomeAPI(HomeAPIListener listener) {
        this.listener = listener;
        addUserAuthorization();
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiHomeFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {


        try {
            JSONObject json = new JSONObject(data);
            String waitPayNum = JsonUtils.getString(json, "unpay");
            String waitReceiveNum = JsonUtils.getString(json, "unrecv");
            String waitCommentNum = JsonUtils.getString(json, "comment");
//            String waitReturn = JsonUtils.getString(json, "return");
            String collectNum = JsonUtils.getString(json, "collect");
            String voucherNum = JsonUtils.getString(json, "voucher");

            String msgNum = JsonUtils.getString(json, "sysmsg");

            listener.apiHomeSuccess(waitPayNum, waitReceiveNum, waitCommentNum, collectNum, msgNum, voucherNum);

        } catch (JSONException e) {
            e.printStackTrace();
            listener.apiHomeFailure(0, ResUtils.getString(R.string.error_reslove));
        }

    }

    @Override
    public String getURL() {
        return Build.HOST + "consumer/home";
    }

    public interface HomeAPIListener {
        public void apiHomeSuccess(String waitPayNum, String waitReceiveNum, String waitCommentNum, String collectNum, String msgNum, String voucherNum);

        public void apiHomeFailure(long code, String msg);
    }
}
