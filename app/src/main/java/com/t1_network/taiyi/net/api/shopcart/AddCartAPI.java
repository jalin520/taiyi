package com.t1_network.taiyi.net.api.shopcart;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.cart.Cart;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/29 15:20
 * 修改记录:
 */
public class AddCartAPI extends TYAPI {

    private AddCartAPIListener listener;

    public AddCartAPI(AddCartAPIListener listener, String id, String count) {
        this.listener = listener;

        addUserAuthorization();
        addParams("productid", id);
        addParams("remain", count);

        new TYHttp(this).request();

    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiAddCartFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        Cart cart = gson.fromJson(data, Cart.class);
        listener.apiAddCartSuccess(cart);
    }

    @Override
    public String getURL() {
        return Build.HOST + "cart/add";
    }

    public interface AddCartAPIListener {

        public void apiAddCartSuccess(Cart cart);

        public void apiAddCartFailure(long code, String msg);

    }

}
