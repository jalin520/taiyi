package com.t1_network.taiyi.controller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.t1_network.core.controller.BasicListAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.listener.OrderButtonListener;
import com.t1_network.taiyi.model.bean.order.GoodInOrder;
import com.t1_network.taiyi.model.bean.order.Order;
import com.t1_network.taiyi.model.factory.ImageOptionFactory;
import com.t1_network.taiyi.utils.VerifyUtils;

import java.util.List;

/**
 * Created by chenyu on 2015/11/12.
 */
public class OrderWaitReceiveAdapter extends BasicListAdapter implements OrderButtonListener.ButtonOnClickListener {


    private OrderWaitReceiveListener listener;

    public OrderWaitReceiveAdapter(List<?> data, OrderWaitReceiveListener listener) {
        super(data, R.layout.item_order_wait_receive);
        this.listener = listener;
    }

    public void bind(ViewHolder holder, int position) {

        Order order = (Order) data.get(position);
        LogUtils.e(order.getStatus());

        holder.getTextView(R.id.orderId).setText(ResUtils.getString(R.string.act_good_list_text_order_id, order.getId()));

        LinearLayout ll = holder.get(R.id.item_order_wait_pay_ll);
        ll.removeAllViews();

        for (int i = 0; i < order.getGoodList().size(); i++) {

            GoodInOrder good = order.getGoodList().get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.item_order_wait_pay_item, ll, false);
            TextView tvTitle = (TextView) view.findViewById(R.id.orderName);
            tvTitle.setText(good.getName());


            TextView textPrice = (TextView) view.findViewById(R.id.order_is_seven);
            textPrice.setText(ResUtils.getString(R.string.money, good.getPrice()));

            TextView tvOrderAmount = (TextView) view.findViewById(R.id.orderAmount);
            tvOrderAmount.setText(ResUtils.getString(R.string.count, good.getCount()));

            if (VerifyUtils.hasImage(good)) {
                ImageView image = (ImageView) view.findViewById(R.id.good_image);
                imageLoader.displayImage(good.getImageList().get(0).getUrl(), image, ImageOptionFactory.getGoodOptions());
            }

            ll.addView(view);
        }

        holder.getTextView(R.id.order_freight).setText(ResUtils.getString(R.string.act_good_list_text_order_freight, order.getGoodList().size(), order.getFeight()));
        holder.getTextView(R.id.orderMoney).setText(ResUtils.getString(R.string.act_good_list_text_total, order.getTotal()));

        if (order.getStatus().equals("2")) {
            holder.getButton(R.id.item_order_wait_pay_btn_cancel_order).setVisibility(View.VISIBLE);
            holder.getButton(R.id.select_logistics).setVisibility(View.GONE);
            holder.getButton(R.id.take_for_order).setVisibility(View.GONE);
        } else {
            holder.getButton(R.id.item_order_wait_pay_btn_cancel_order).setVisibility(View.GONE);
            holder.getButton(R.id.select_logistics).setVisibility(View.VISIBLE);
            holder.getButton(R.id.take_for_order).setVisibility(View.VISIBLE);
        }


        //设置button的点击事件
        Button selectButton = holder.getButton(R.id.select_logistics);
        selectButton.setOnClickListener(new OrderButtonListener(order, this));

        Button takeButton = holder.getButton(R.id.take_for_order);
        takeButton.setOnClickListener(new OrderButtonListener(order, this));

        Button cancelButton = holder.getButton(R.id.item_order_wait_pay_btn_cancel_order);
        cancelButton.setOnClickListener(new OrderButtonListener(order, this));
    }


    public interface OrderWaitReceiveListener {

        public void onClickCompleteOrder(Order order);

        public void onClickLookLogistics(Order order);

        public void onClickCancleOrder(Order order);

    }


    @Override
    public void buttonOnclickListener(View v, Order order) {
        int id = v.getId();
        switch (id) {
            case R.id.select_logistics:
//
                listener.onClickLookLogistics(order);
                break;
            case R.id.take_for_order:
                listener.onClickCompleteOrder(order);
                break;
            case R.id.item_order_wait_pay_btn_cancel_order:
                listener.onClickCancleOrder(order);
                break;
        }
    }
}