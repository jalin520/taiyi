package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * 功能：1.6 用户登出,访问接口不需要body参数
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-11-2
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class LogoutAPI extends TYAPI {

    private LogoutAPIListener listener;

    public LogoutAPI(LogoutAPIListener listener) {
        this.listener = listener;
        addUserAuthorization();
        new TYHttp(this).request();
    }

    @Override
    public String getURL() {
        return Build.HOST + "login/logout";
    }

    @Override
    public void apiRequestSuccess(String data) {
        listener.apiLogoutSuccess();
    }


    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiLogoutFailure(code, msg);
    }

    public interface LogoutAPIListener {
        public void apiLogoutSuccess();

        public void apiLogoutFailure(long code, String msg);
    }
}
