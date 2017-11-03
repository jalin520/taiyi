package com.t1_network.taiyi.net;

import com.android.volley.VolleyError;
import com.t1_network.core.app.App;
import com.t1_network.core.net.BasicAPI;
import com.t1_network.core.net.BasicHttp;
import com.t1_network.core.utils.JsonUtils;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.taiyi.controller.reveiver.LoginReceiver;
import com.t1_network.taiyi.db.TYSP;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.net.api.user.LoginByTokenAPI;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by David on 15/12/16.
 */
public class TYHttp extends BasicHttp implements LoginByTokenAPI.LoginByTokenListener {

    public TYHttp(BasicAPI api) {
        super(api);
    }

    @Override
    public void apiLoginByTokenFailure(long code, String msg) {
        //通知所有页面
        App.getApp().setUser(null);
        TYSP.clearToken();
        LoginReceiver.send(App.getContext());
    }

    @Override
    public void apiLoginByTokenSuccess(User user) {

        App.getApp().setUser(user);
        TYSP.setToken(user.getConsumer().getToken());
        LoginReceiver.send(App.getContext());

        api.addUserAuthorization();

        request();//重新请求
    }

    @Override
    public void onResponse(JSONObject response) {

        LogUtils.e("[response] " + response == null ? "null" : response.toString());

        try {
            if (response != null) {
                boolean isSuccess = false;
                long code = -1;
                String message = "";

                if (response.has("success")) { //如果是太一返回的接口（太一返回的接口必带success属性）

                    isSuccess = response.getBoolean("success");
                    code = JsonUtils.getLong(response, "code");
                    message = JsonUtils.getString(response, "message");

                    if (code == 200 && isSuccess) {

                        JSONObject data = JsonUtils.getJSONObject(response, "data");

                        if (data == null) {
                            api.requestSuccess(response.toString()); //部分接口没有data字段
                        } else {
                            api.requestSuccess(data.toString()); //正常字段,既带data的接口
                        }

                        return;
                    } else {

                        if (code == 407 || code == 401) {
                            new LoginByTokenAPI(this);
                            return;
                        }

                        api.requestError(code, message);
                        return;
                    }

                } else {
                    //其它接口
                    if (response != null) {
                        api.requestSuccess(response.toString());
                        return;
                    } else {
                        api.requestError(0, "");
                        return;
                    }
                    //待拓展
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        try {
            if (volleyError != null && volleyError.networkResponse != null) {
                api.requestError(volleyError.networkResponse.statusCode, volleyError.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            api.requestError(0, "");
        }
    }
}
