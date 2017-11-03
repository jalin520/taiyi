package com.t1_network.taiyi.net.api.good;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.good.Good;
import com.t1_network.taiyi.model.bean.good.GoodList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/31 11:11
 * 修改记录:
 */
public class GoodListByClassficationAPI extends TYAPI {

    private GoodListByClassficationAPIListener listener;

    public GoodListByClassficationAPI(GoodListByClassficationAPIListener listener, String classficationId, String featureId, String sort, long limit) {
        this.listener = listener;

        addParams("ids", classficationId);
        addParams("id", featureId);
        addParams("orderby", "desc");
        addParams("sort", sort);
        addParams("pagenum", limit + "");
        addParams("pagesize", "10");

        new TYHttp(this).request();
    }


    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiGoodListByClassficationFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {

        GoodList goodList = gson.fromJson(data, GoodList.class);
        listener.apiGoodListByClassficationSuccess(goodList.getGoodList());

    }

    @Override
    public String getURL() {
        return Build.HOST + "home/productlist";
    }

    public interface GoodListByClassficationAPIListener {

        public void apiGoodListByClassficationSuccess(List<Good> goodList);

        public void apiGoodListByClassficationFailure(long code, String msg);

    }

}
