package com.t1_network.taiyi.net.api.order;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.order.OrderBackRefund;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 2016/2/23.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class OrderBackRefundAPI extends TYAPI {

    private OrderBackRefundAPIListener mListener;

    public OrderBackRefundAPI(OrderBackRefundAPIListener listener,String orderid,String productid,String quantity) {
        mListener = listener;
        addParams("orderid",orderid);
        addParams("productid",productid);
        addParams("quantity",quantity);

        new TYHttp(this).request();
    }

    @Override
    public void apiRequestSuccess(String data) {
        OrderBackRefund orderBackRefund = gson.fromJson(data, OrderBackRefund.class);
        mListener.apiOrderBackRefundSuccess(orderBackRefund);
    }

    @Override
    public void apiRequestError(long code, String msg) {
        mListener.apiOrderBackRefundFailure(code,msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "orderback/refund";
    }

    public interface OrderBackRefundAPIListener{
        void apiOrderBackRefundSuccess(OrderBackRefund bean);
        void apiOrderBackRefundFailure(long code, String msg);
    }

}
