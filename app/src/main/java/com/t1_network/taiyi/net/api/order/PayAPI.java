package com.t1_network.taiyi.net.api.order;


import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 15/11/4.
 */
public class PayAPI extends TYAPI {

    private PayAPIListener listener;

    public PayAPI(PayAPIListener listener, String id , String payType) {
        super();

        this.listener = listener;

        addParams("id", id);
        addParams("paytype", payType);

        new TYHttp(this).request();
    }

    @Override
    public String getURL() {
        return Build.HOST + "pingpp/pay";
    }

    @Override
    public void apiRequestSuccess(String data) {
        listener.apiPaySuccess(data);
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiPayFailure(code, msg);
    }

    public interface PayAPIListener {
        public void apiPaySuccess(String change);

        public void apiPayFailure(long code, String msg);
    }
}
