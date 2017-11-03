package com.t1_network.taiyi.net.api.good;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.good.Feature;
import com.t1_network.taiyi.model.bean.good.FeatureList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/20 15:20
 * 修改记录:
 */
public class FeatureAPI extends TYAPI {

    private FeatureAPIListener listener;

    public FeatureAPI(FeatureAPIListener listener, String classifyId) {
        this.listener = listener;
        addParams("ids", classifyId);
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiFeatureFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {

        FeatureList temp = gson.fromJson(data, FeatureList.class);
        listener.apiFeatureSuccess(temp.getFeatureList());

    }

    @Override
    public String getURL() {
        return Build.HOST + "home/feature";
    }


    public interface FeatureAPIListener {

        public void apiFeatureSuccess(List<Feature> featureList);

        public void apiFeatureFailure(long code, String msg);

    }

}
