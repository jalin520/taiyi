package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.controller.activity.LoginAndRegisterAct;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 15/12/15.
 */
public class LoginByOtherAPI extends TYAPI {

    private LoginByOtherAPIListener listener;

    public LoginByOtherAPI(LoginByOtherAPIListener listener, int type, int sex, String nickName, String account, String avatar) {
        this.listener = listener;


        switch (type) {

            case LoginAndRegisterAct.LOGIN_BY_WECHAT:
                addParams("channel", "wechat");
                break;

            case LoginAndRegisterAct.LOGIN_BY_SINA:
                addParams("channel", "weibo");
                break;

            case LoginAndRegisterAct.LOGIN_BY_QQ:
                addParams("channel", "qq");
                break;
        }

        addParams("sex", sex);
        addParams("nickname", nickName);
        addParams("account", account);
        addParams("headimgurl", avatar);

        new TYHttp(this).request();
    }

    @Override
    public String getURL() {
        return Build.HOST + "login/thirdparty";
    }


    @Override
    public void apiRequestSuccess(String data) {
        User user = gson.fromJson(data, User.class);
        listener.apiLoginByOtherSuccess(user);
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiLoginByOtherFailure(code, msg);
    }

    public interface LoginByOtherAPIListener {
        public void apiLoginByOtherSuccess(User user);

        public void apiLoginByOtherFailure(long code, String msg);
    }


}
