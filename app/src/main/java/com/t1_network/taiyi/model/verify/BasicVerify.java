package com.t1_network.taiyi.model.verify;

import com.t1_network.core.utils.ResUtils;

/**
 * Created by David on 15/11/16.
 */
public abstract class BasicVerify {

    public VerifyResult result = new VerifyResult();


    public VerifyResult error(int msg) {
        String errorMsg = ResUtils.getString(msg);

        result.setResult(false);
        result.setErrorMsg(errorMsg);
        return result;
    }

    public VerifyResult error(String msg) {
        result.setResult(false);
        result.setErrorMsg(msg);
        return result;
    }

    public VerifyResult success() {
        result.setResult(true);
        return result;
    }

}
