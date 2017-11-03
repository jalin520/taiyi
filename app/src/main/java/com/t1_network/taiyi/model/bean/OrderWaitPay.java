package com.t1_network.taiyi.model.bean;

/**
 * Created by chenyu on 2015/11/10.
 */
public class OrderWaitPay {
    private String orderId;        //订单ID
    private String orderState ;    //订单状态
    private String goodName;       //商品名称
    private int orderAmount;      //订单数量
    private String orderMoney;     //订单金额
    private String orderTime;     //下订单时间
    private String orderLeftTime;    //剩余支付时间

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderLeftTime() {
        return orderLeftTime;
    }

    public void setOrderLeftTime(String orderLeftTime) {
        this.orderLeftTime = orderLeftTime;
    }
}
