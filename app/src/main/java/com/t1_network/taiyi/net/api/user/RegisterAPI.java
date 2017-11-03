package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * 功能：1.2 使用短信验证码和密码注册新用户,访问接口需要提供用户11位合法的手机号码，6位短信验证码，用户新设置的登录密码
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-11-2
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class RegisterAPI extends TYAPI {

    private RegisterAPIListener listener;

    public RegisterAPI(RegisterAPIListener listener, String mobile, String verifyCode, String password) {
        this.listener = listener;

        addParams("phone", mobile);
        addParams("verifycode", verifyCode);
        addParams("password", password);

        new TYHttp(this).request();

    }

    @Override
    public String getURL() {
        return Build.HOST + "register/verify";
    }

    @Override
    public void apiRequestSuccess(String data) {
        User user = gson.fromJson(data, User.class);
        listener.apiRegisterSuccess(user);
    }


    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiRegisterFailure(code, msg);
    }

    public interface RegisterAPIListener {
        public void apiRegisterSuccess(User user);//请求成功

        public void apiRegisterFailure(long code, String msg);//请求失败
    }

}
