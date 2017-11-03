package com.t1_network.taiyi.net.api.good;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/28 16:46
 * 修改记录:
 */
public class CollectAPI extends TYAPI {

    CollectAPIListener listener;

    public CollectAPI(CollectAPIListener listener, String id) {
        this.listener = listener;

        addParams("id", id);
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiCollectFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        listener.apiCollectSuccess();
    }

    @Override
    public String getURL() {
        return Build.HOST + "collection/collect";
    }

    public interface CollectAPIListener {
        public void apiCollectSuccess();

        public void apiCollectFailure(long code, String msg);
    }
}
