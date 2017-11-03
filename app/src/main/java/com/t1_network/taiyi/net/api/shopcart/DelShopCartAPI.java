package com.t1_network.taiyi.net.api.shopcart;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.ShopCartNormal;
import com.t1_network.taiyi.model.bean.ShopCartNormalList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * Created by David on 15/12/8.
 */
public class DelShopCartAPI extends TYAPI {

    private DelShopCartAPIListener listener;

    public DelShopCartAPI(DelShopCartAPIListener listener, String cartId) {
        this.listener = listener;

        addUserAuthorization();
        addParams("productid", cartId);
        new TYHttp(this).request();

    }

    @Override
    public String getURL() {
        return Build.HOST + "cart/del";
    }


    @Override
    public void apiRequestSuccess(String data) {
        ShopCartNormalList shopCartNormalList = gson.fromJson(data, ShopCartNormalList.class);
        listener.apiDelShopCartSuccess(shopCartNormalList.getShopCarNormalList());
    }


    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiDelShopCartFailure(code, msg);
    }

    public interface DelShopCartAPIListener {

        public void apiDelShopCartSuccess(List<ShopCartNormal> shopCartNormalList);

        public void apiDelShopCartFailure(long code, String msg);

    }

}
