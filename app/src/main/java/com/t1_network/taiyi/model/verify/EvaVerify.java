package com.t1_network.taiyi.model.verify;

import android.text.TextUtils;

import com.t1_network.taiyi.R;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/12 17:33
 * 修改记录:
 */
public class EvaVerify extends BasicVerify {

    public static VerifyResult verify(Float rating, String content) {
        EvaVerify result = new EvaVerify();

        if (rating <= 0) {
            return result.error(R.string.error_rating_is_empty);
        }

        if (TextUtils.isEmpty(content)) {
            return result.error(R.string.error_content_is_empty);
        }

        return result.success();
    }

}
