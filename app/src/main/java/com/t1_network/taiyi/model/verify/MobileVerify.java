package com.t1_network.taiyi.model.verify;

import android.text.TextUtils;

import com.t1_network.taiyi.R;

/**
 * Created by David on 15/11/16.
 */
public class MobileVerify extends BasicVerify {

    public static VerifyResult verifyMobile(String mobile) {
        MobileVerify mobileVerify = new MobileVerify();

        if (TextUtils.isEmpty(mobile)) {
            return mobileVerify.error(R.string.error_mobile_can_no_be_enpty);
        }

        if (mobile.length() != 11) {
            return mobileVerify.error(R.string.error_mobile_must_be_11_length);
        }

        if (!mobile.startsWith("1")) {
            return mobileVerify.error(R.string.error_mobile_must_be_start_with_1);
        }

        return mobileVerify.success();
    }

}
