package com.t1_network.taiyi.controller.listener;

import android.view.View;

import com.t1_network.taiyi.model.bean.order.Order;

/**
 * Created by David on 2015/11/17.
 */
public class OrderButtonListener implements View.OnClickListener {

    Order order;

    private ButtonOnClickListener mButtonOnClickListener;

    public interface ButtonOnClickListener {
        void buttonOnclickListener(View v, Order order);
    }

    public OrderButtonListener(Order order, ButtonOnClickListener mButtonOnClickListener) {
        this.order = order;
        this.mButtonOnClickListener = mButtonOnClickListener;
    }

    @Override
    public void onClick(View v) {
        mButtonOnClickListener.buttonOnclickListener(v, order);
    }
}
