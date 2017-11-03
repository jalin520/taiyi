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
 * 创建日期: 16/1/15 10:36
 * 修改记录:
 */
public class SupposeYouWantAPI extends TYAPI {


    private SupposeYouWantAPIListener listener;

    public SupposeYouWantAPI(SupposeYouWantAPIListener listener) {
        this.listener = listener;
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiSupposeYouWantFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        GoodList goodList = gson.fromJson(data, GoodList.class);
        listener.apiSupposeYouWantSuccess(goodList.getGoodList());
    }

    @Override
    public String getURL() {
        return Build.HOST + "home/recommend";
    }

    public interface SupposeYouWantAPIListener {
        public void apiSupposeYouWantSuccess(List<Good> goodList);

        public void apiSupposeYouWantFailure(long code, String msg);
    }
}
