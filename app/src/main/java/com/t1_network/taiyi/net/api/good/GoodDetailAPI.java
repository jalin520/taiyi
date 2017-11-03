package com.t1_network.taiyi.net.api.good;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.good.GoodDetail;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 15/12/17.
 */
public class GoodDetailAPI extends TYAPI {

    private GoodDetailAPIListener listener;

    public GoodDetailAPI(GoodDetailAPIListener listener, String goodId) {
        this.listener = listener;

        addParams("id", goodId);
        new TYHttp(this).request();

    }

    @Override
    public void apiRequestSuccess(String data) {

        GoodDetail goodDetail = gson.fromJson(data, GoodDetail.class);
        listener.apiGoodDetailAPISuccess(goodDetail);
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiGoodDetailAPIFailure(code, msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "home/detail";
    }

    public interface GoodDetailAPIListener {

        public void apiGoodDetailAPISuccess(GoodDetail goodDetail);

        public void apiGoodDetailAPIFailure(long code, String msg);

    }

}
