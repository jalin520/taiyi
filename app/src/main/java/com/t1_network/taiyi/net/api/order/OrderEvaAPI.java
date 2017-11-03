package com.t1_network.taiyi.net.api.order;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import org.json.JSONArray;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/13 10:22
 * 修改记录:
 */

public class OrderEvaAPI extends TYAPI {

    private OrderEvaAPIListener listener;

    public OrderEvaAPI(OrderEvaAPIListener listener, String id, String score, String content, JSONArray urlArray) {
        this.listener = listener;

        addUserAuthorization();

        addParams("id", id);
        addParams("score", score);
        addParams("content", content);
        addParams("pic", urlArray);

        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiOrderEvaFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        listener.apiOrderEvaSuccess();
    }

    @Override
    public String getURL() {
        return Build.HOST + "comment/comment";
    }

    public interface OrderEvaAPIListener {

        public void apiOrderEvaSuccess();

        public void apiOrderEvaFailure(long code, String msg);

    }

}
