package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * 功能：1.7 用户使用短信验证码登录，请求发送短信接口,访问接口需要提供用户11位合法的手机号码
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-11-2
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class SMSForLoginAPI extends TYAPI {


    private SMSForLoginAPIListener listener;

    public SMSForLoginAPI(SMSForLoginAPIListener listener, String mobile) {
        this.listener = listener;


        addParams("phone", mobile);
        new TYHttp(this).request();


    }

    @Override
    public String getURL() {
        return Build.HOST + "login/sms";
    }

    @Override
    public void apiRequestSuccess(String data) {
        listener.apiSMSForLoginSuccess();
    }


    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiSMSForLoginFailure(code, msg);
    }

    public interface SMSForLoginAPIListener {
        public void apiSMSForLoginSuccess();//请求成功

        public void apiSMSForLoginFailure(long code, String msg);//请求失败
    }
}
