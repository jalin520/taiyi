package com.t1_network.taiyi.net.api.order;

import com.google.gson.JsonSyntaxException;
import com.t1_network.core.app.Build;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.model.bean.order.OrderDetailBean;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 2016/1/21.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class OrderDetailAPI extends TYAPI {

    public OrderDetailAPI(OrderDetailAPIListener orderDetailAPIListener,String orderID) {
        mOrderDetailAPIListener = orderDetailAPIListener;
        addParams("id",orderID);
        new TYHttp(this).request();

    }

    private OrderDetailAPIListener mOrderDetailAPIListener;

    public interface OrderDetailAPIListener{
        void apiOrderDetailSuccess(OrderDetailBean orderDetailBean);
        void apiOrderDetailFailure(long code,String mag);
    }

    @Override
    public void apiRequestSuccess(String data) {

        try {

            if (data.contains("\"receipt\":[]")){

            }
            OrderDetailBean orderDetailBean = gson.fromJson(data, OrderDetailBean.class);
            mOrderDetailAPIListener.apiOrderDetailSuccess(orderDetailBean);
        } catch (JsonSyntaxException e) {
            TipUtils.toast("接口信息有误");
        }
    }

    @Override
    public void apiRequestError(long code, String msg) {
        mOrderDetailAPIListener.apiOrderDetailFailure(code,msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "order/detail";
    }
}
