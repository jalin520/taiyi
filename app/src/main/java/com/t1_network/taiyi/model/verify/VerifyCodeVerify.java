package com.t1_network.taiyi.model.verify;

import android.text.TextUtils;

import com.t1_network.taiyi.R;

/**
 * Created by David on 15/11/16.
 */
public class VerifyCodeVerify extends BasicVerify {

    public static VerifyResult verify(String mobile) {
        VerifyCodeVerify verifyCodeVerify = new VerifyCodeVerify();
        if (TextUtils.isEmpty(mobile)) {
            return verifyCodeVerify.error(R.string.error_verify_code_can_no_be_enpty);
        }

        return verifyCodeVerify.success();
    }


}
