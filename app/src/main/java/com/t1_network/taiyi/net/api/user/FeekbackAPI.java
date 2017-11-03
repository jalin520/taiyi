package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/30 17:22
 * 修改记录:
 */
public class FeekbackAPI extends TYAPI {

    private FeekbackAPIListener listener;

    public FeekbackAPI(FeekbackAPIListener listener, String content, String contact, String name) {
        this.listener = listener;

        addParams("contact", name + ":" + contact);
        addParams("content", content);

        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiFeekbackFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        listener.apiFeekbackSuccess();
    }

    @Override
    public String getURL() {
        return Build.HOST + "consultation/advice";
    }

    public interface FeekbackAPIListener {

        public void apiFeekbackSuccess();

        public void apiFeekbackFailure(long code, String msg);

    }

}
