package com.t1_network.taiyi.net.api.shopcart;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.cart.Cart;
import com.t1_network.taiyi.model.bean.cart.GoodInCart;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by David on 15/12/17.
 */
public class SelectGoodAPI extends TYAPI {

    private SelectGoodAPIListener listener;


    public SelectGoodAPI(SelectGoodAPIListener listener, List<GoodInCart> goodList, boolean isSelect) {
        this.listener = listener;

        addUserAuthorization();

        try {

            JSONArray array = new JSONArray();

            for (int i = 0; i < goodList.size(); i++) {
                JSONObject goodJson = new JSONObject();
                goodJson.put("productid", goodList.get(i).getId());
                array.put(goodJson);
            }

            params.put("cart", array);

            if (isSelect) {
                params.put("select", "1");
            } else {
                params.put("select", "0");
            }

            new TYHttp(this).request();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public SelectGoodAPI(SelectGoodAPIListener listener, String id, boolean isSelect) {
        this.listener = listener;

        addUserAuthorization();

        try {

            JSONObject goodJson = new JSONObject();
            goodJson.put("productid", id);

            JSONArray array = new JSONArray();
            array.put(goodJson);

            params.put("cart", array);

            if (isSelect) {
                params.put("select", "1");
            } else {
                params.put("select", "0");
            }

            new TYHttp(this).request();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void apiRequestSuccess(String data) {
        Cart cart = gson.fromJson(data, Cart.class);
        listener.apiSelectGoodSuccess(cart);
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiSelectGoodFailure(code, msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "cart/select";
    }

    public interface SelectGoodAPIListener {

        public void apiSelectGoodSuccess(Cart cart);

        public void apiSelectGoodFailure(long code, String msg);

    }

}
