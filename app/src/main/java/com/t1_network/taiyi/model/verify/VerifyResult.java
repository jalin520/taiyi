package com.t1_network.taiyi.model.verify;

/**
 * Created by David on 15/11/16.
 */
public class VerifyResult {

    private boolean result;
    private String errorMsg;

    public VerifyResult() {
        result = false;
        errorMsg = "";
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
