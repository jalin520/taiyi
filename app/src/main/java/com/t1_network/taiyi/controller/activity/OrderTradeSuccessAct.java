package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.order.Order;

import butterknife.OnClick;

public class OrderTradeSuccessAct extends BasicAct {

    private Order go_order;

    public OrderTradeSuccessAct() {
        super(R.layout.activity_trade_success, R.string.title_activity_trade_success);
    }


    public static void startActivity(Context context, Order order) {
        Intent intent = new Intent(context, OrderTradeSuccessAct.class);
        intent.putExtra("GO_ORDER", order);
        context.startActivity(intent);

    }


    @Override
    public void initView() {
        Intent intent = this.getIntent();
        go_order = intent.getParcelableExtra("GO_ORDER");

    }


    //评价
    @OnClick(R.id.act_trade_success_tv_input_evaluate)
    public void onClickInputEvaluate() {
        OrderListAct.startActivity(this, OrderListAct.OrderStatus.WAIT_COMMENT);
    }

    //订单
    @OnClick(R.id.act_trade_success_tv_input_order)
    public void onClickInputOrder() {
        OrderDetailAct.startActivity(this, go_order, OrderListAct.OrderStatus.WAIT_COMMENT);
    }

}
