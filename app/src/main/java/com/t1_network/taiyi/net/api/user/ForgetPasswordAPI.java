package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * 功能：忘记密码之后，使用短信验证码重置密码且自动重新登陆,访问接口需要提供用户11位合法的手机号码，6位短信验证码，用户新设置的登录密码
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-11-2
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class ForgetPasswordAPI extends TYAPI {

    private ForgetPasswordAPIListener listener;

    public ForgetPasswordAPI(ForgetPasswordAPIListener listener, String mobile, String verifyCode, String password) {
        this.listener = listener;

        addParams("phone", mobile);
        addParams("verifycode", verifyCode);
        addParams("password", password);
        new TYHttp(this).request();
    }

    @Override
    public String getURL() {
        return Build.HOST + "register/forgetpassword";
    }


    @Override
    public void apiRequestSuccess(String data) {
        User user = gson.fromJson(data, User.class);
        listener.apiForgetPasswordSuccess(user);
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiForgetPasswordFailure(code, msg);
    }

    public interface ForgetPasswordAPIListener {
        public void apiForgetPasswordSuccess(User user);//请求成功

        public void apiForgetPasswordFailure(long code, String msg);//请求失败
    }
}
