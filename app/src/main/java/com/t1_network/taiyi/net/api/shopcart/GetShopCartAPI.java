package com.t1_network.taiyi.net.api.shopcart;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.cart.Cart;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * 查看购物车接口，返回购物车列表
 */
public class GetShopCartAPI extends TYAPI {

    private GetShopCartListener listener;

    public GetShopCartAPI(GetShopCartListener listener) {
        this.listener = listener;

        addUserAuthorization();
        new TYHttp(this).request();

    }

    @Override
    public String getURL() {
        return Build.HOST + "cart/list";
    }


    @Override
    public void apiRequestSuccess(String data) {
        Cart cart = gson.fromJson(data, Cart.class);
        listener.apiGetShopCartSuccess(cart);
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiGetShopCartFailure(code, msg);
    }

    public interface GetShopCartListener {

        public void apiGetShopCartSuccess(Cart cart);

        public void apiGetShopCartFailure(long code, String msg);

    }

}
