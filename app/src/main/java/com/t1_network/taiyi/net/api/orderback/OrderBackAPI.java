package com.t1_network.taiyi.net.api.orderback;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 15/12/21.
 */
public class OrderBackAPI extends TYAPI {

    private OrderBackAPIListener listener;

    public OrderBackAPI(OrderBackAPIListener listener) {
        this.listener = listener;
        addUserAuthorization();
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiOrderBackFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {

    }

    @Override
    public String getURL() {
        return Build.HOST + "orderback/listall";
    }

    public interface OrderBackAPIListener {

        public void apiOrderBackSuccess();

        public void apiOrderBackFailure(long code, String msg);

    }

}
