package com.t1_network.taiyi.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by David on 15/12/7.
 */
public class ShopCartNormalList {

    @SerializedName("total")
    private long count;
    @SerializedName("result")
    private List<ShopCartNormal> shopCarNormalList;


    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<ShopCartNormal> getShopCarNormalList() {
        return shopCarNormalList;
    }

    public void setShopCarNormalList(List<ShopCartNormal> shopCarNormalList) {
        this.shopCarNormalList = shopCarNormalList;
    }
}
