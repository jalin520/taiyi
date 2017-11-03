package com.t1_network.taiyi.model.event;

import com.t1_network.taiyi.model.bean.cart.Cart;

/**
 * 功能: 购物车更新事件
 * 创建者: David
 * 创建日期: 16/1/21 17:11
 * 修改记录:
 */
public class UpdateCartEvent {

    private Cart cart;

    public UpdateCartEvent(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
