package com.t1_network.taiyi.net.api.shopcart;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.cart.Cart;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 15/12/18.
 */
public class GetSelectCartAPI extends TYAPI {

    private GetSelectCartAPIListener listener;

    public GetSelectCartAPI(GetSelectCartAPIListener listener) {
        this.listener = listener;

        addUserAuthorization();

        new TYHttp(this).request();

    }

    @Override
    public void apiRequestSuccess(String data) {
        Cart cart = gson.fromJson(data, Cart.class);
        listener.apiGetSelectCartSuccess(cart);
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiGetSelectCartFailure(code, msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "cart/selectlist";
    }

    public interface GetSelectCartAPIListener {
        public void apiGetSelectCartSuccess(Cart cart);

        public void apiGetSelectCartFailure(long code, String msg);
    }

}
