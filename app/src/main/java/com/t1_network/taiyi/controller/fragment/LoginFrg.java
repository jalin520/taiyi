package com.t1_network.taiyi.controller.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.t1_network.core.controller.BasicFrg;
import com.t1_network.core.utils.KeyBoardUtils;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.activity.ForgetPwdAct;
import com.t1_network.taiyi.controller.activity.LoginByVerifyAct;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.model.event.LoginEvent;
import com.t1_network.taiyi.model.verify.MobileVerify;
import com.t1_network.taiyi.model.verify.PasswordVerify;
import com.t1_network.taiyi.model.verify.VerifyResult;
import com.t1_network.taiyi.net.api.user.LoginByPasswordAPI;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


public class LoginFrg extends BasicFrg implements LoginByPasswordAPI.LoginByPasswordAPIListener {


    @Bind(R.id.frg_register_root)
    RelativeLayout layoutRoot;

    //手机输入框
    @Bind(R.id.frg_login_et_phone_one)
    EditText editMobile;
    //密码输入框
    @Bind(R.id.frg_login_et_password)
    EditText editPassword;
    private String mMobile;
    private String mPassword;


    //关联.电话号码和密码的登录.Frg
    public LoginFrg() {
        super(R.layout.frg_login);
    }

    //初始化View
    @Override
    public void initView(View view) {

    }

    //电话号码and密码.登录
    @OnClick(R.id.frg_login_btn_login)
    public void login() {
        KeyBoardUtils.hideKeyboard(context, layoutRoot);
        mMobile = editMobile.getText().toString().trim();
        mPassword = editPassword.getText().toString().trim();
        VerifyResult result = MobileVerify.verifyMobile(mMobile);//获取电话号码校验结果
        VerifyResult passwordResult = PasswordVerify.verify(mPassword);//获取密码校验结果
        if (!result.getResult()) {
            LogUtils.e(mMobile + "?" + mMobile.isEmpty());
            TipUtils.snackBar(layoutRoot, result.getErrorMsg());
            LogUtils.e(result.getErrorMsg());
            return;
        }
        if (!passwordResult.getResult()) {//获取密码格式有误==>提示错误信息
            TipUtils.snackBar(layoutRoot, passwordResult.getErrorMsg());
            LogUtils.e(mPassword + "?" + mPassword.isEmpty());
            return;
        }
        LogUtils.e("zou");
        //求情服务器.是否登录成功
        new LoginByPasswordAPI(this, mMobile, mPassword);

    }

    //动态验证码登录
    @OnClick(R.id.frg_login_tv_verify_login)
    public void verifyLogin(View v) {
        LoginByVerifyAct.startLoginByVerifyAct(context);
    }

    //忘记密码
    @OnClick(R.id.ftg_register_btn_git_pwd_code)
    public void gitPwd(View v) {
        ForgetPwdAct.startForgetPwdAct(context);
    }

    //登录成功.回调
    @Override
    public void apiLoginByPasswordSuccess(User user) {

        TipUtils.snackBar(layoutRoot, "登陆成功");
        EventBus.getDefault().post(new LoginEvent(user));
        ((Activity) context).finish();
    }

    //登录失败.回调
    @Override
    public void apiLoginByPasswordFailure(long code, String msg) {
        TipUtils.snackBar(layoutRoot, msg);

    }

    @OnClick(R.id.frg_register_root)
    public void clickRoot(){
        KeyBoardUtils.hideKeyboard(context, layoutRoot);
    }


}
