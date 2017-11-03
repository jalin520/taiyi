package com.t1_network.taiyi.net.api.order;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 15/12/21.
 */
public class OrderCancelAPI extends TYAPI {

    private OrderCancelAPIListener listener;

    public OrderCancelAPI(OrderCancelAPIListener listener, String orderId) {
        this.listener = listener;
        addUserAuthorization();
        addParams("id", orderId);
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiOrderCancelFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        listener.apiOrderCancelSuccess();
    }

    @Override
    public String getURL() {
        return Build.HOST + "order/cancel";
    }

    public interface OrderCancelAPIListener {
        public void apiOrderCancelSuccess();

        public void apiOrderCancelFailure(long code, String msg);
    }
}
