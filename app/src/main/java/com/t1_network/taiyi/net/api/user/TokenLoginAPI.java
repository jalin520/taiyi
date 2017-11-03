package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.db.TYSP;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 15/12/9.
 */
public class TokenLoginAPI extends TYAPI {

    private TokenLoginAPIListener listener;

    public TokenLoginAPI(TokenLoginAPIListener listener) {
        this.listener = listener;
        addParams("token", TYSP.getToken());
        new TYHttp(this).request();
    }

    @Override
    public String getURL() {
        return Build.HOST + "login/token";
    }

    @Override
    public void apiRequestSuccess(String data) {
        User user = gson.fromJson(data, User.class);
        listener.apiTokenLoginSuccess(user);
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiTokenLoginFailure(code, msg);
    }

    public interface TokenLoginAPIListener {
        public void apiTokenLoginSuccess(User user);//请求成功

        public void apiTokenLoginFailure(long code, String msg);//请求失败
    }
}
