package com.t1_network.taiyi.net.api.order;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.order.Order;
import com.t1_network.taiyi.model.bean.order.OrderList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * Created by David on 15/11/23.
 */
public class OrderWaitPayAPI extends TYAPI {

    private OrderWaitPayAPIListener listener;

    public OrderWaitPayAPI(OrderWaitPayAPIListener listener, long limit) {
        this.listener = listener;
        addUserAuthorization();

        addParams("pagenum", limit + "");
        addParams("pagesize", "10");
        new TYHttp(this).request();

    }

    @Override
    public String getURL() {
        return Build.HOST + "order/unpaylist";
    }

    @Override
    public void apiRequestSuccess(String data) {
        OrderList orderList = gson.fromJson(data, OrderList.class);
        listener.apiOrderWaitPaySuccess(orderList.getOrderList(), orderList.getCount());
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiOrderWaitPayFailure(code, msg);
    }

    public interface OrderWaitPayAPIListener {

        public void apiOrderWaitPaySuccess(List<Order> orderList, long total);

        public void apiOrderWaitPayFailure(long code, String msg);

    }
}
