package com.t1_network.taiyi.controller.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.t1_network.core.controller.BasicFrg;
import com.t1_network.core.task.TimeTask;
import com.t1_network.core.utils.KeyBoardUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.model.event.LoginEvent;
import com.t1_network.taiyi.model.verify.MobileVerify;
import com.t1_network.taiyi.model.verify.PasswordVerify;
import com.t1_network.taiyi.model.verify.VerifyCodeVerify;
import com.t1_network.taiyi.model.verify.VerifyResult;
import com.t1_network.taiyi.net.api.user.RegisterAPI;
import com.t1_network.taiyi.net.api.user.SendVerifyCodeAPI;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


public class RegisterFrg extends BasicFrg implements SendVerifyCodeAPI.SendVerifyCodeAPIListener, RegisterAPI.RegisterAPIListener {

    @Bind(R.id.frg_register_root)
    RelativeLayout layoutRoot;

    //验证码 按钮
    @Bind(R.id.frg_register_tv_send_verify_code)
    TextView tvVerify;
    //显示按钮
    @Bind(R.id.frg_register_tv_send_password_show)
    TextView showPassword;


    //电话显示输入框
    @Bind(R.id.feg_register_check_phone_edit_mobile)
    EditText editMobile;
    //验证码.输入框
    @Bind(R.id.frg_register_edit_verify_code)
    EditText editVerifyCode;
    //密码输入框
    @Bind(R.id.frg_register_edit_password)
    EditText editPassword;

    @Bind(R.id.frg_register_btn_send)
    TextView tvSend;

    @Bind(R.id.ftg_register_ll_btn_is_check)
    LinearLayout llBtnIsChecked;

    @Bind(R.id.ftg_register_iv_is_check_icon)
    ImageView mIvIcon;

    private boolean isChecked;
    private boolean isShow;
    private String mMobile;


    /**
     * 倒计时进程
     */
    private TimeTask<TextView> timeTask;
    private String mPwd;
    private String mVerifyCode;


    public RegisterFrg() {
        super(R.layout.frg_register);
    }

    @Override
    public void initView(View view) {


        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.hideKeyboard(context, layoutRoot);
                if (isShow) {
                    editPassword.setInputType(129);
                    isShow = false;
                } else {
                    editPassword.setInputType(3);
                    isShow = true;
                }
            }
        });

    }

    //验证码 按钮
    @OnClick(R.id.frg_register_tv_send_verify_code)
    public void sendVerifyCode(View view) {
        KeyBoardUtils.hideKeyboard(context, layoutRoot);
        //判断手机号码是否正确...正确的显示下一个界面.不正确.给出提示.
        mMobile = editMobile.getText().toString().trim();
        VerifyResult result = MobileVerify.verifyMobile(mMobile);

        if (!result.getResult()) {
            TipUtils.snackBar(layoutRoot, result.getErrorMsg());
            return;
        }
        timeTask = new TimeTask<TextView>(tvVerify, R.color.btnText_1);
        timeTask.execute(60);
        new SendVerifyCodeAPI(this, mMobile);

    }


    //提交 按钮
    @OnClick(R.id.frg_register_btn_send)
    public void sendOrder(View view) {
        KeyBoardUtils.hideKeyboard(context, layoutRoot);
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

        new RegisterAPI(this, mMobile, mVerifyCode, mPwd);
    }


    //协议的件事件事
    @OnClick(R.id.ftg_register_ll_btn_is_check)
    public void onClickIsCheckProtocol(View v) {
        isChecked = !isChecked;
        if (isChecked) {
            mIvIcon.setImageResource(R.drawable.ic_check_image_icon);
            tvSend.setClickable(true);
            tvSend.setBackgroundResource(R.drawable.btn_in_login_full_info);
            tvSend.setTextColor(ResUtils.getColor(R.color.text_white));
        } else {
            mIvIcon.setImageResource(R.drawable.ic_normal_image_icon);
            tvSend.setClickable(false);
            tvSend.setBackgroundResource(R.drawable.btn_in_login_full_normal_info);
            tvSend.setTextColor(ResUtils.getColor(R.color.btn_in_verify_full));
        }
    }


    @Override
    public void apiSendVerifyCodeSuccess() {

        TipUtils.snackBar(layoutRoot, "发送验证码成功，请查收");
    }

    @Override
    public void apiSendVerifyCodeFailure(long code, String msg) {
        TipUtils.snackBar(layoutRoot, msg);
    }


    @Override
    public void apiRegisterSuccess(User user) {
        TipUtils.snackBar(layoutRoot, "注册成功");
        EventBus.getDefault().post(new LoginEvent(user));
        this.getActivity().finish();
    }

    @Override
    public void apiRegisterFailure(long code, String msg) {
        TipUtils.snackBar(layoutRoot, msg);
    }


    @Override
    public void onDestroy() {
        if (timeTask != null) {
            timeTask.stop();
        }
        super.onDestroy();
    }

    @OnClick(R.id.frg_register_root)
    public void clickRoot() {
        KeyBoardUtils.hideKeyboard(context, layoutRoot);
    }
}
