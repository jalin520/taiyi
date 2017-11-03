package com.t1_network.taiyi.net.api.order;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.order.Order;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/30 11:46
 * 修改记录:
 */
public class OrderCompleteAPI extends TYAPI {

    private OrderCompleteAPIListener listener;
    private Order mOrder;

    public OrderCompleteAPI(OrderCompleteAPIListener listener, Order order) {
        this.listener = listener;
        this.mOrder = order;

        addUserAuthorization();
        addParams("id", order.getId());

        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiOrderCompleteFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        listener.apiOrderCompleteSuccess(mOrder);
    }

    @Override
    public String getURL() {
        return Build.HOST + "order/complete";
    }

    public interface OrderCompleteAPIListener {

        public void apiOrderCompleteSuccess(Order order);

        public void apiOrderCompleteFailure(long code, String msg);

    }
}
