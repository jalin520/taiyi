package com.t1_network.taiyi.model.verify;

import android.text.TextUtils;

import com.t1_network.taiyi.R;

/**
 * Created by David on 15/11/16.
 */
public class PasswordVerify extends BasicVerify {
    public static PasswordVerify passwordVerify = newInstent();

    private static PasswordVerify newInstent() {
        return new PasswordVerify();
    }

    public static VerifyResult verify(String pwd, String comfirmPassword) {
        String password = pwd.trim();
        if (TextUtils.isEmpty(password)) {
            return passwordVerify.error(R.string.error_password_code_can_no_be_enpty);
        }

        if (TextUtils.isEmpty(comfirmPassword)) {
            return passwordVerify.error(R.string.error_comfirm_password_code_can_no_be_enpty);
        }

        if (!password.equals(comfirmPassword)) {
            return passwordVerify.error(R.string.error_password_is_no_same);
        }

        return passwordVerify.success();
    }

    public static VerifyResult verify(String password) {

        if (TextUtils.isEmpty(password)) {
            return passwordVerify.error(R.string.error_password_code_can_no_be_enpty);
        }

        return passwordVerify.success();
    }
}
