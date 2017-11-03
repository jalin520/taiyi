package com.t1_network.taiyi.net.api.order;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.order.Order;
import com.t1_network.taiyi.model.bean.order.OrderList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * 功能：全部订单接口
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-11-16
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class AllOrderAPI extends TYAPI {

    private AllOrderAPIListener listener;

    public AllOrderAPI(AllOrderAPIListener listener, long limit) {
        this.listener = listener;

        addUserAuthorization();


        addParams("pagenum", limit + "");
        addParams("pagesize", "10");
        addParams("orderby", "desc");
        addParams("sort", "sysupdate");

        new TYHttp(this).request();

    }

    @Override
    public String getURL() {
        return Build.HOST + "order/listall";
    }


    @Override
    public void apiRequestSuccess(String data) {
        OrderList orderList = gson.fromJson(data, OrderList.class);
        listener.apiAllOrderSuccess(orderList.getOrderList(), orderList.getCount());
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiAllOrderFailure(code, msg);
    }

    public interface AllOrderAPIListener {
        public void apiAllOrderSuccess(List<Order> orderList, long total);

        public void apiAllOrderFailure(long code, String msg);
    }

}
