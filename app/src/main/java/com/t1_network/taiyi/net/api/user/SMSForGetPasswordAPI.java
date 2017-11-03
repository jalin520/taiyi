package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * 功能：1.3 用户忘记密码之后，发送手机短信 6位验证码,访问接口需要提供用户11位合法的手机号码
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-11-2
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class SMSForGetPasswordAPI extends TYAPI {

    private SMSForGetPasswordAPIListener listener;

    public SMSForGetPasswordAPI(SMSForGetPasswordAPIListener listener, String mobile) {
        this.listener = listener;

        addParams("phone", mobile);
        new TYHttp(this).request();

    }

    @Override
    public String getURL() {
        return Build.HOST + "register/smsforgetpw";
    }

    @Override
    public void apiRequestSuccess(String data) {
        listener.apiSMSForGetPasswordSuccess();
    }


    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiSMSForGetPasswordFailure(code, msg);
    }

    public interface SMSForGetPasswordAPIListener {
        public void apiSMSForGetPasswordSuccess();//请求成功

        public void apiSMSForGetPasswordFailure(long code, String msg);//请求失败
    }
}
