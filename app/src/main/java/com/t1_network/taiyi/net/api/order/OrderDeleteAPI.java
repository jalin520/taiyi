package com.t1_network.taiyi.net.api.order;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.order.Order;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 2016/2/2.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class OrderDeleteAPI extends TYAPI {

    private Order order;
    private OrderDeleteAPIListener mListener;

    public OrderDeleteAPI(OrderDeleteAPIListener listener, Order order) {

        this.mListener = listener;
        this.order = order;

        addParams("id", order.getId());
        new TYHttp(this).request();
    }


    public interface OrderDeleteAPIListener {
        void apiOrderDeleteAPISuccess(Order order);

        void apiOrderDeleteAPIFailure(long code, String msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        mListener.apiOrderDeleteAPISuccess(order);
    }

    @Override
    public void apiRequestError(long code, String msg) {
        mListener.apiOrderDeleteAPIFailure(code, msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "order/delete";
    }


}
