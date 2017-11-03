package com.t1_network.taiyi.net.api.order;

import com.t1_network.core.app.Build;
import com.t1_network.core.utils.JsonUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.Address;
import com.t1_network.taiyi.model.bean.cart.GoodInCart;
import com.t1_network.taiyi.model.bean.order.Receipt;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by David on 15/12/18.
 */
public class OrderComfirmAPI extends TYAPI {

    private OrderComfirmAPIListener listener;

    public OrderComfirmAPI(OrderComfirmAPIListener listener, Address address, Receipt receipt, List<GoodInCart> goodList, List<String> voucherList) {
        this.listener = listener;

        addUserAuthorization();

        try {

            //添加地址
            JSONObject jsonAddress = new JSONObject();
            jsonAddress.put("id", address.getId());
            addParams("recvadd", jsonAddress);

            //添加发票
            if (receipt != null) {
                JSONObject jsonReceipt = new JSONObject();

                JsonUtils.put(jsonReceipt, "title", receipt.getTitle());
                JsonUtils.put(jsonReceipt, "phone", receipt.getPhone());
                JsonUtils.put(jsonReceipt, "email", receipt.getEmail());
                JsonUtils.put(jsonReceipt, "content", receipt.getContent());
                JsonUtils.put(jsonReceipt, "type", receipt.getType());

                addParams("receipt", jsonReceipt);
            }

            //添加商品列表
            JSONArray jsonArrayCart = new JSONArray();

            for (int i = 0; i < goodList.size(); i++) {

                GoodInCart good = goodList.get(i);

                JSONObject jsonGood = new JSONObject();
                jsonGood.put("id", good.getId());
                jsonGood.put("voucher", voucherList.get(i));
                jsonArrayCart.put(jsonGood);
            }
            addParams("cart", jsonArrayCart);

            new TYHttp(this).request();

        } catch (Exception e) {
            e.printStackTrace();
            listener.apiOrderComfirmFailure(0, ResUtils.getString(R.string.error_request_body_error));
        }

    }

    @Override
    public void apiRequestSuccess(String data) {

        try {
            JSONObject jsonData = new JSONObject(data);
            String orderId = JsonUtils.getString(jsonData, "orderids");
            listener.apiOrderComfirmSuccess(orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiOrderComfirmFailure(code, msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "order/commit";
    }


    public interface OrderComfirmAPIListener {

        public void apiOrderComfirmSuccess(String orderId);

        public void apiOrderComfirmFailure(long code, String msg);

    }
}
