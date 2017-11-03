package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.task.TimeTask;
import com.t1_network.core.utils.KeyBoardUtils;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.model.event.LoginEvent;
import com.t1_network.taiyi.model.verify.MobileVerify;
import com.t1_network.taiyi.model.verify.VerifyCodeVerify;
import com.t1_network.taiyi.model.verify.VerifyResult;
import com.t1_network.taiyi.net.api.user.LoginByVerifyCodeAPI;
import com.t1_network.taiyi.net.api.user.SMSForLoginAPI;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class LoginByVerifyAct extends BasicAct implements SMSForLoginAPI.SMSForLoginAPIListener, LoginByVerifyCodeAPI.LoginByVerifyCodeAPIListener {

    @Bind(R.id.act_register_root)
    RelativeLayout layoutRoot;

    @Bind(R.id.act_register_tv_send_verify_code)
    TextView tvSendVerify;

    @Bind(R.id.act_login_et_phone)
    EditText editMobile;

    @Bind(R.id.act_login_et_verify_code)
    EditText etTerify;

    private TimeTask<TextView> timeTask;

    public LoginByVerifyAct() {
        super(R.layout.act_login_by_verify, NO_TITLE, false, TOOLBAR_TYPE_NO_TOOLBAR);
    }

    public static void startLoginByVerifyAct(Context context) {
        Intent intent = new Intent(context, LoginByVerifyAct.class);
        context.startActivity(intent);
    }


    @Override
    public void initView() {

    }

    //登录
    @OnClick(R.id.act_login_btn_login)
    public void tvVerifyLogin(View view) {
        KeyBoardUtils.hideKeyboard(this, layoutRoot);
        String mMobile = editMobile.getText().toString().trim();
        String verify = etTerify.getText().toString().trim();
        VerifyResult result = MobileVerify.verifyMobile(mMobile);//获取电话号码校验结果
        VerifyResult verifyResult = VerifyCodeVerify.verify(verify);
        if (!result.getResult()) {
            TipUtils.snackBar(layoutRoot, result.getErrorMsg());
            return;
        }

        if (!verifyResult.getResult()) {
            TipUtils.snackBar(layoutRoot, verifyResult.getErrorMsg());
            return;
        }

        //求情服务器.是否登录成功

        new LoginByVerifyCodeAPI(this, mMobile, verify);


    }

    //获取验证码
    @OnClick(R.id.act_register_tv_send_verify_code)
    public void sendVerify() {
        KeyBoardUtils.hideKeyboard(this, layoutRoot);
        String mobile = editMobile.getText().toString().trim();

        VerifyResult mobileVerify = MobileVerify.verifyMobile(mobile);
        if (!mobileVerify.getResult()) {
            TipUtils.snackBar(layoutRoot, mobileVerify.getErrorMsg());
            return;
        }

        new SMSForLoginAPI(this, mobile);


        timeTask = new TimeTask<TextView>(tvSendVerify, R.color.text_white);
        timeTask.execute(60);
    }

    @OnClick(R.id.login_and_register_ll_back)
    public void finishAct() {
        finish();
    }


    @Override
    public void apiSMSForLoginSuccess() {
        TipUtils.snackBar(layoutRoot, "验证码已发送，请注意查收");
    }

    @Override
    public void apiSMSForLoginFailure(long code, String msg) {
        TipUtils.snackBar(layoutRoot, msg);
    }

    @Override
    public void apiLoginByVerifyCodeSuccess(User user) {
        TipUtils.snackBar(layoutRoot, "登陆成功");
        EventBus.getDefault().post(new LoginEvent(user));
        this.finish();
    }

    @Override
    public void apiLoginByVerifyCodeFailure(long code, String msg) {
        TipUtils.snackBar(layoutRoot, msg);
    }


    //微信登录
    @OnClick(R.id.act_login_by_shear_wechat)
    public void wechatLogin(View view) {
        TipUtils.snackBar(layoutRoot, "微信登录");
    }

    //新浪登录
    @OnClick(R.id.act_login_by_shear_sina)
    public void sinaLogin(View view) {
        TipUtils.snackBar(layoutRoot, "新浪登录");
    }

    //QQ登录
    @OnClick(R.id.act_login_by_shear_qq)
    public void qqLogin(View view) {
        TipUtils.snackBar(layoutRoot, "QQ登录");
    }

    @OnClick(R.id.login_root)
    public void clickRoot() {
        KeyBoardUtils.hideKeyboard(this, layoutRoot);
    }

    @Override
    protected void onDestroy() {
        if (timeTask != null)
            timeTask.stop();
        super.onDestroy();
    }


}
