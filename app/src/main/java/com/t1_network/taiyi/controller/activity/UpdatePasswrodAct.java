package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.t1_network.core.app.App;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.task.TimeTask;
import com.t1_network.core.utils.KeyBoardUtils;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.reveiver.LoginReceiver;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.model.verify.MobileVerify;
import com.t1_network.taiyi.model.verify.PasswordVerify;
import com.t1_network.taiyi.model.verify.VerifyCodeVerify;
import com.t1_network.taiyi.model.verify.VerifyResult;
import com.t1_network.taiyi.net.api.user.ForgetPasswordAPI;
import com.t1_network.taiyi.net.api.user.SMSForGetPasswordAPI;

import butterknife.Bind;
import butterknife.OnClick;

public class UpdatePasswrodAct extends BasicAct implements SMSForGetPasswordAPI.SMSForGetPasswordAPIListener, ForgetPasswordAPI.ForgetPasswordAPIListener {

    @Bind(R.id.act_register_root)
    RelativeLayout layoutRoot;


    //验证码 按钮
    @Bind(R.id.act_forget_pwd_tv_send_verify_code)
    TextView tvVerify;
    //显示按钮
    @Bind(R.id.act_forget_pwd_tv_send_password_show)
    TextView showPassword;


    //电话显示输入框
    @Bind(R.id.act_forget_pwd_et_phone)
    EditText editMobile;
    //验证码.输入框
    @Bind(R.id.act_forget_pwd_et_verify_code)
    EditText editVerifyCode;
    //密码输入框
    @Bind(R.id.act_forget_pwd_et_password)
    EditText editPassword;


    private boolean isShow;
    private String mMobile;
    /**
     * 倒计时进程
     */
    private TimeTask<TextView> timeTask;
    private String mPwd;
    private String mVerifyCode;


    public UpdatePasswrodAct() {
        super(R.layout.activity_update_passwrod, NO_TITLE, false, TOOLBAR_TYPE_NO_TOOLBAR);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UpdatePasswrodAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.hideKeyboard(UpdatePasswrodAct.this, layoutRoot);
            }
        });
    }

    //验证码 按钮
    @OnClick(R.id.act_forget_pwd_tv_send_verify_code)
    public void sendVerifyCode(View view) {

        KeyBoardUtils.hideKeyboard(this, layoutRoot);
        //判断手机号码是否正确...正确的显示下一个界面.不正确.给出提示.
        mMobile = editMobile.getText().toString().trim();
        VerifyResult result = MobileVerify.verifyMobile(mMobile);

        if (!result.getResult()) {
            TipUtils.snackBar(layoutRoot, result.getErrorMsg());
            return;
        }
        timeTask = new TimeTask<TextView>(tvVerify, R.color.btnText_1);
        timeTask.execute(60);
        new SMSForGetPasswordAPI(this, mMobile);

    }

    //显示密码按钮
    @OnClick(R.id.act_forget_pwd_tv_send_password_show)
    public void showPassword(View view) {
        KeyBoardUtils.hideKeyboard(UpdatePasswrodAct.this, layoutRoot);
        if (isShow) {
            editPassword.setInputType(129);
            isShow = false;
        } else {
            editPassword.setInputType(3);
            isShow = true;
        }
    }

    //提交 按钮
    @OnClick(R.id.act_forget_pwd_btn_login)
    public void sendOrder(View view) {
        KeyBoardUtils.hideKeyboard(this, layoutRoot);
        //判断验证码.是否一致
        mVerifyCode = editVerifyCode.getText().toString().trim();
        mPwd = editPassword.getText().toString().trim();
        mMobile = editMobile.getText().toString().trim();

        VerifyResult mobileResult = MobileVerify.verifyMobile(mMobile);
        VerifyResult verify = VerifyCodeVerify.verify(mVerifyCode);
        VerifyResult result = PasswordVerify.verify(mPwd);


        if (!mobileResult.getResult()) {
            TipUtils.snackBar(layoutRoot, mobileResult.getErrorMsg());
            return;
        }
        if (!verify.getResult()) {
            TipUtils.snackBar(layoutRoot, verify.getErrorMsg());
            return;
        }
        if (!result.getResult()) {
            TipUtils.snackBar(layoutRoot, result.getErrorMsg());
            return;
        }

        new ForgetPasswordAPI(this, mMobile, mVerifyCode, mPwd);

    }

    @OnClick(R.id.login_and_register_ll_back)
    public void finishAct() {
        finish();
    }


    @Override
    public void onDestroy() {
        if (timeTask != null) {
            timeTask.stop();
        }
        super.onDestroy();
    }

//    //微信登录
//    @OnClick(R.id.act_login_by_shear_wechat)
//    public void wechatLogin(View view) {
//        TipUtils.toast("微信登录");
//    }
//
//    //新浪登录
//    @OnClick(R.id.act_login_by_shear_sina)
//    public void sinaLogin(View view) {
//        TipUtils.toast("新浪登录");
//    }
//
//    //QQ登录
//    @OnClick(R.id.act_login_by_shear_qq)
//    public void qqLogin(View view) {
//        TipUtils.toast("QQ登录");
//    }


    //*************网络请求的回调方法*********
    @Override
    public void apiForgetPasswordSuccess(User user) {
        TipUtils.snackBar(layoutRoot, "密码设置成功");
        App.getApp().setUser(user);

        //进行广播
        LoginReceiver.send(this);

        finish();
    }

    @Override
    public void apiForgetPasswordFailure(long code, String msg) {
        TipUtils.snackBar(layoutRoot, msg);
    }

    @Override
    public void apiSMSForGetPasswordSuccess() {
        TipUtils.snackBar(layoutRoot, "发送验证码成功，请查收");
    }

    @Override
    public void apiSMSForGetPasswordFailure(long code, String msg) {
        TipUtils.snackBar(layoutRoot, msg);
    }




}
