package com.t1_network.taiyi.net.api.order;

import com.google.gson.reflect.TypeToken;
import com.t1_network.core.app.Build;
import com.t1_network.core.utils.JsonUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.order.OrderWaitComment;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by David on 15/12/22.
 */
public class OrderWaitCommentAPI extends TYAPI {

    private OrderWaitCommentAPIListener listener;

    public OrderWaitCommentAPI(OrderWaitCommentAPIListener listener, long limit) {
        this.listener = listener;
        addUserAuthorization();

        addParams("pagenum", limit + "");
        addParams("pagesize", "10");
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiOrderWaitCommentFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        try {
            JSONObject json = new JSONObject(data);


            List<OrderWaitComment> orderList = gson.fromJson(JsonUtils.getJSONArray(json, "result").toString(), new TypeToken<List<OrderWaitComment>>() {
            }.getType());
            listener.apiOrderWaitCommentSuccess(orderList);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.apiOrderWaitCommentFailure(0, ResUtils.getString(R.string.error_reslove));
        }
    }

    @Override
    public String getURL() {
        return Build.HOST + "comment/uncommentlist";
    }

    public interface OrderWaitCommentAPIListener {

        public void apiOrderWaitCommentSuccess(List<OrderWaitComment> orderList);

        public void apiOrderWaitCommentFailure(long code, String msg);

    }
}
