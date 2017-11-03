package com.t1_network.taiyi.model.bean.cart;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 购物车页面Bean
 */
public class Cart {

    @SerializedName("quantity")
    private String allSelectCount;

    @SerializedName("subtotal")
    private String allSelectMoney;

    @SerializedName("detail")
    private List<GoodInCart> goodList;

    public String getAllSelectCount() {
        return allSelectCount;
    }

    public void setAllSelectCount(String allSelectCount) {
        this.allSelectCount = allSelectCount;
    }

    public String getAllSelectMoney() {
        return allSelectMoney;
    }

    public void setAllSelectMoney(String allSelectMoney) {
        this.allSelectMoney = allSelectMoney;
    }

    public List<GoodInCart> getGoodList() {
        return goodList;
    }

    public void setGoodList(List<GoodInCart> goodList) {
        this.goodList = goodList;
    }
}
