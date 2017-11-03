package com.t1_network.taiyi.model.verify;

import android.text.TextUtils;

import com.t1_network.taiyi.R;

/**
 * Created by David on 15/12/9.
 */
public class ExchangeTokenVerify extends BasicVerify {

    public static VerifyResult verify(String id) {
        ExchangeTokenVerify verify = new ExchangeTokenVerify();

        if (TextUtils.isEmpty(id)) {
            return verify.error(R.string.error_exchange_token_is_empty);
        }

        return verify.success();
    }


}
