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
 * 创建日期: 16/1/18 15:38
 * 修改记录:
 */
public class GoodListBySearchAPI extends TYAPI {


    private GoodListBySearchAPIListener listener;


    public GoodListBySearchAPI(GoodListBySearchAPIListener listener, String name, String sort, long limit) {
        this.listener = listener;

        addParams("name", name);
        addParams("orderby", "desc");
        addParams("sort", sort);
        addParams("pagenum", limit + "");
        addParams("pagesize", "10");

        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiGoodListBySearchFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {

        GoodList goodList = gson.fromJson(data, GoodList.class);
        listener.apiGoodListBySearchSuccess(goodList.getGoodList());
    }

    @Override
    public String getURL() {
        return Build.HOST + "home/search";
    }

    public interface GoodListBySearchAPIListener {
        public void apiGoodListBySearchSuccess(List<Good> goodList);

        public void apiGoodListBySearchFailure(long code, String msg);
    }


}
