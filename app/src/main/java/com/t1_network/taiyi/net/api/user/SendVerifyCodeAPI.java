package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * 功能：API 通过手机获取验证码
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-10-30
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class SendVerifyCodeAPI extends TYAPI {

    private SendVerifyCodeAPIListener listener;

    public SendVerifyCodeAPI(SendVerifyCodeAPIListener listener, String mobile) {
        this.listener = listener;

        if (mobile != null && !mobile.isEmpty() && !"".equals(mobile)) {

            addParams("phone", mobile);
            new TYHttp(this).request();

        }
    }

    @Override
    public String getURL() {
        return Build.HOST + "register/sms";
    }

    @Override
    public void apiRequestSuccess(String data) {
        listener.apiSendVerifyCodeSuccess();
    }


    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiSendVerifyCodeFailure(code, msg);
    }

    public interface SendVerifyCodeAPIListener {
        public void apiSendVerifyCodeSuccess();//请求成功

        public void apiSendVerifyCodeFailure(long code, String msg);//请求失败
    }

}
