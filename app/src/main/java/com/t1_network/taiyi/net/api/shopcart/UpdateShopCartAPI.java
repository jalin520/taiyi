package com.t1_network.taiyi.net.api.shopcart;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.cart.Cart;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * 更新购物车API
 */
public class UpdateShopCartAPI extends TYAPI {

    private UpdateShopCartAPIListener listener;

    public UpdateShopCartAPI(UpdateShopCartAPIListener listener, String id, String count) {
        this.listener = listener;

        addUserAuthorization();
        addParams("productid", id);
        addParams("remain", count);
        new TYHttp(this).request();

    }

    @Override
    public String getURL() {
        return Build.HOST + "cart/update";
    }

    @Override
    public void apiRequestSuccess(String data) {
        Cart cart = gson.fromJson(data, Cart.class);
        listener.apiUpdateShopCartSuccess(cart);
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiUpdateShopCartFailure(code, msg);
    }

    public interface UpdateShopCartAPIListener {

        public void apiUpdateShopCartSuccess(Cart cart);

        public void apiUpdateShopCartFailure(long code, String msg);

    }

}
