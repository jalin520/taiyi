package com.t1_network.taiyi.net.api.home;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.home.SpecialGood;
import com.t1_network.taiyi.model.bean.home.SpecialGoodList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/27 17:23
 * 修改记录:
 */
public class SpecialGoodAPI extends TYAPI {

    private SpecialGoodAPIListener listener;

    public SpecialGoodAPI(SpecialGoodAPIListener listener) {
        this.listener = listener;

        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiSpecialGoodFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        SpecialGoodList specialGoodList = gson.fromJson(data, SpecialGoodList.class);
        listener.apiSpecialGoodSuccess(specialGoodList.getGoodList());
    }

    @Override
    public String getURL() {
        return Build.HOST + "homepage/specialoffer";
    }

    public interface SpecialGoodAPIListener {
        public void apiSpecialGoodSuccess(List<SpecialGood> goodList);

        public void apiSpecialGoodFailure(long code, String msg);
    }
}
