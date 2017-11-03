package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * 功能：1.5 用户手机号和密码登陆,访问接口需要提供用户11位合法的手机号码
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-11-2
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class LoginByPasswordAPI extends TYAPI {
    //自定义回调接口的...回调
    private LoginByPasswordAPIListener listener;

    //构造犯法
    public LoginByPasswordAPI(LoginByPasswordAPIListener listener, String mobile, String passwrod) {
        this.listener = listener;


        addParams("phone", mobile);//添加.电话到.属性集合中.处理
        addParams("password", passwrod);//添加密码到 属性集合中处理
        new TYHttp(this).request();


    }

    //重写几类的方法.处理URL地址
    @Override
    public String getURL() {
        return Build.HOST + "login/usercode";
    }

    @Override
    public void apiRequestSuccess(String data) {
        User user = gson.fromJson(data, User.class);
        listener.apiLoginByPasswordSuccess(user);
    }


    //失败的时候服务器返回的数据
    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiLoginByPasswordFailure(code, msg);
    }

    //自定义.接口.把服务器的数据.给子类实现.
    public interface LoginByPasswordAPIListener {
        //子类显示成功后的数据
        public void apiLoginByPasswordSuccess(User user);//请求成功

        //子类显示.失败后的数据
        public void apiLoginByPasswordFailure(long code, String msg);//请求失败

    }
}
