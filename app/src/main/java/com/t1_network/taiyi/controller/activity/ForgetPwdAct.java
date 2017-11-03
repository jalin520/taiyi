package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.t1_network.core.app.App;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.task.TimeTask;
import com.t1_network.core.utils.KeyBoardUtils;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.reveiver.LoginReceiver;
import com.t1_network.taiyi.db.TYSP;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.model.verify.MobileVerify;
import com.t1_network.taiyi.model.verify.PasswordVerify;
import com.t1_network.taiyi.model.verify.VerifyCodeVerify;
import com.t1_network.taiyi.model.verify.VerifyResult;
import com.t1_network.taiyi.net.api.user.ForgetPasswordAPI;
import com.t1_network.taiyi.net.api.user.LoginByOtherAPI;
import com.t1_network.taiyi.net.api.user.SMSForGetPasswordAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class ForgetPwdAct extends BasicAct implements ForgetPasswordAPI.ForgetPasswordAPIListener, SMSForGetPasswordAPI.SMSForGetPasswordAPIListener, PlatformActionListener, LoginByOtherAPI.LoginByOtherAPIListener {


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


    public ForgetPwdAct() {
        super(R.layout.act_forget_pwd, NO_TITLE, false, TOOLBAR_TYPE_NO_TOOLBAR);
    }

    public static void startForgetPwdAct(Context context) {
        Intent intent = new Intent(context, ForgetPwdAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {

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
        KeyBoardUtils.hideKeyboard(ForgetPwdAct.this, layoutRoot);
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

        mMobile = editMobile.getText().toString();
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

    @OnClick(R.id.login_root)
    public void clickRoot() {
        KeyBoardUtils.hideKeyboard(this, layoutRoot);
    }

    //*************网络请求的回调方法*********
    @Override
    public void apiForgetPasswordSuccess(User user) {
        TipUtils.snackBar(layoutRoot, "密码设置成功");
        App.getApp().setUser(user);

        //进行广播
        LoginReceiver.send(this);

        finish();
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


    /**
     * 第三方登录----------------------------------------------------------------------------------------------------------
     */

    public final static int LOGIN_BY_WECHAT = 1;
    public final static int LOGIN_BY_SINA = 2;
    public final static int LOGIN_BY_QQ = 3;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            try {
                HashMap<String, Object> res = (HashMap<String, Object>) msg.obj;


                switch (msg.what) {

                    case LOGIN_BY_WECHAT:
                        fromWechat(res);
                        break;
                    case LOGIN_BY_SINA:

                        break;

                    case LOGIN_BY_QQ:

                        break;


                }


            } catch (Exception e) {
                e.printStackTrace();
                showTip(e.getMessage());
            }
        }
    };

    /**
     * 传入平台名称,进行第三方登录,并回调 PlatformActionListener 接口
     *
     * @param name
     */
    private void loginByOther(String name) {
        Platform platform = ShareSDK.getPlatform(name);
        platform.setPlatformActionListener(this);
        platform.SSOSetting(false);
        platform.showUser(null);//执行登录，登录后在回调里面获取用户资料
    }


    /**
     * 点击 QQ登录
     */
    @OnClick(R.id.act_login_by_shear_qq)
    public void qqLogin(View view) {
        loginByOther(QQ.NAME);
    }

    /**
     * 点击 微信登录
     */
    @OnClick(R.id.act_login_by_shear_wechat)
    public void wechatLogin(View view) {
        loginByOther(Wechat.NAME);
    }

    /**
     * 点击 新浪登录
     */
    @OnClick(R.id.act_login_by_shear_sina)
    public void sinaLogin(View view) {
        loginByOther(SinaWeibo.NAME);
    }


    /**
     * 第三方登录的回调,三个方法都是在子线程中,需要用Handler去进行UI操作
     * <p/>
     * onComplete 回调成功
     * <p/>
     * onError 回调失败
     * <p/>
     * onCancel 回调取消
     */

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message msg = new Message();

        if (Wechat.NAME.equals(platform.getName())) {
            msg.what = LOGIN_BY_WECHAT;
        }

        if (SinaWeibo.NAME.equals(platform.getName())) {
            msg.what = LOGIN_BY_SINA;
        }

        if (QQ.NAME.equals(platform.getName())) {
            msg.what = LOGIN_BY_QQ;
        }

        msg.obj = hashMap;
        handler.sendMessage(msg);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    private void fromWechat(HashMap<String, Object> res) {

        String openId = res.get("openid").toString();
        String name = res.get("nickname").toString();
        String avatar = res.get("headimgurl").toString();
        String unionId = res.get("unionid").toString();
        String country = res.get("country").toString();
        String privilege = res.get("privilege").toString();
        String province = res.get("province").toString();
        String language = res.get("language").toString();
        String sex = res.get("sex").toString();

        String str = "";
        str = str + "openid:" + openId + "\n";
        str = str + "nickname:" + name + "\n";
        str = str + "headimgurl:" + avatar + "\n";
        str = str + "unionid:" + unionId + "\n";
        str = str + "country:" + country + "\n";
        str = str + "privilege:" + privilege + "\n";
        str = str + "province:" + province + "\n";
        str = str + "language:" + language + "\n";
        str = str + "sex:" + sex + "\n";


        JSONObject params = new JSONObject();
        try {


            params.put("channel", "wechat");

            params.put("sex", sex);
            params.put("nickname", name);
            params.put("account", openId);
            params.put("user", new JSONObject(new Gson().toJson(res)));

            showTip(params.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        int sexInt = 1;
        if ("男".equals(sex)) {
            sexInt = 1;
        } else {
            sexInt = 2;
        }

        new LoginByOtherAPI(this, LOGIN_BY_WECHAT, sexInt, name, openId, new Gson().toJson(res));
    }


    @Override
    public void apiLoginByOtherSuccess(User user) {
        App.getApp().setUser(user);
        TYSP.setToken(user.getConsumer().getToken());
        LoginReceiver.send(this);
        this.finish();
    }

    @Override
    public void apiLoginByOtherFailure(long code, String msg) {
        showTip(msg);
    }
}

