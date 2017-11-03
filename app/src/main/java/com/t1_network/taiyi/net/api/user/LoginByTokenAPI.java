package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.db.TYSP;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 15/12/16.
 */
public class LoginByTokenAPI extends TYAPI {

    private LoginByTokenListener listener;

    public LoginByTokenAPI(LoginByTokenListener listener) {
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
        listener.apiLoginByTokenSuccess(user);
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiLoginByTokenFailure(code, msg);
    }

    public interface LoginByTokenListener {

        public void apiLoginByTokenSuccess(User user);

        public void apiLoginByTokenFailure(long code, String msg);

    }


}
