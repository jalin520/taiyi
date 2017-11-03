package com.t1_network.taiyi.net.api.orderback;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.user.AfterSaleDetail;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 15/12/21.
 */
public class OrderBackDetailAPI extends TYAPI {

    private OrderBackDetailAPIListener listener;

    public OrderBackDetailAPI(OrderBackDetailAPIListener listener,String id) {
        this.listener = listener;
        addUserAuthorization();
        addParams("id",id);
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestSuccess(String data) {
        AfterSaleDetail afterSale = gson.fromJson(data, AfterSaleDetail.class);
        listener.apiOrderBackDetailSuccess(afterSale);
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiOrderBackDetailFailure(code,msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "orderback/detail";
    }


    public interface OrderBackDetailAPIListener {

        public void apiOrderBackDetailSuccess( AfterSaleDetail afterSale);

        public void apiOrderBackDetailFailure(long code, String msg);

    }

}
