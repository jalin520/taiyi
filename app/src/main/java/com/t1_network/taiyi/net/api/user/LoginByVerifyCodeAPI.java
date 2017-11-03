package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * 功能：1.8 用户手机验证码登陆,访问接口需要提供用户11位合法的手机号码
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-11-2
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class LoginByVerifyCodeAPI extends TYAPI {

    private LoginByVerifyCodeAPIListener listener;

    public LoginByVerifyCodeAPI(LoginByVerifyCodeAPIListener listener, String mobile, String verifyCode) {
        this.listener = listener;
        addParams("phone", mobile);
        addParams("verifycode", verifyCode);
        new TYHttp(this).request();

    }

    @Override
    public String getURL() {
        return Build.HOST + "login/smslogin";
    }

    @Override
    public void apiRequestSuccess(String data) {
        User user = gson.fromJson(data, User.class);
        listener.apiLoginByVerifyCodeSuccess(user);
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiLoginByVerifyCodeFailure(code, msg);
    }

    public interface LoginByVerifyCodeAPIListener {
        public void apiLoginByVerifyCodeSuccess(User user);//请求成功

        public void apiLoginByVerifyCodeFailure(long code, String msg);//请求失败
    }
}
