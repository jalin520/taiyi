package com.t1_network.taiyi.net.api.order;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.order.Order;
import com.t1_network.taiyi.model.bean.order.OrderList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * Created by David on 15/11/24.
 */
public class OrderWaitReceiveAPI extends TYAPI {

    private OrderWaitReceiveAPIListener listener;

    public OrderWaitReceiveAPI(OrderWaitReceiveAPIListener listener, long limit) {
        this.listener = listener;

        addUserAuthorization();
        addParams("pagenum", limit + "");
        addParams("pagesize", "10");
        new TYHttp(this).request();
    }

    @Override
    public String getURL() {
        return Build.HOST + "order/notreceivelist";
    }

    @Override
    public void apiRequestSuccess(String data) {
        OrderList orderList = gson.fromJson(data, OrderList.class);
        listener.apiOrderWaitReceiveSuccess(orderList.getOrderList(), orderList.getCount());
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiOrderWaitReceiveFailure(code, msg);
    }

    public interface OrderWaitReceiveAPIListener {

        public void apiOrderWaitReceiveSuccess(List<Order> orderList, long total);

        public void apiOrderWaitReceiveFailure(long code, String msg);

    }
}