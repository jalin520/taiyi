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
import com.t1_network.taiyi.model.bean.order.GoodInOrder;
import com.t1_network.taiyi.model.bean.order.Order;
import com.t1_network.taiyi.model.factory.ImageOptionFactory;
import com.t1_network.taiyi.utils.VerifyUtils;

import java.util.List;

/**
 * Created by chenyu on 2015/11/10.
 */
public class OrderWaitPayAdapter extends BasicListAdapter {

    private OrderWaitPayListener listener;

    public OrderWaitPayAdapter(List<?> data, OrderWaitPayListener listener) {
        super(data, R.layout.item_order_wait_pay);
        this.listener = listener;
    }

    public void bind(ViewHolder holder, int position) {

        Order order = (Order) data.get(position);

        holder.getTextView(R.id.orderId).setText(ResUtils.getString(R.string.act_good_list_text_order_id, order.getId()));
        LinearLayout ll = holder.get(R.id.item_order_wait_pay_ll);
        ll.removeAllViews();
        for (int i = 0; i < order.getGoodList().size(); i++) {

            GoodInOrder good = order.getGoodList().get(i);

            LogUtils.e(good.getName());

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

        //设置button的点击事件
        Button btnCancelOrder = holder.getButton(R.id.item_order_wait_pay_btn_cancel_order);
        btnCancelOrder.setOnClickListener(new CancelOrderListener(listener, order));

        Button btnPay = holder.getButton(R.id.item_order_wait_pay_btn_pay);
        btnPay.setOnClickListener(new PayOrderListener(listener, order));
    }


    public interface OrderWaitPayListener {

        public void onClickCancelOrder(Order order);

        public void onClickPay(Order order);

    }

    public class CancelOrderListener implements View.OnClickListener {

        private Order order;
        private OrderWaitPayListener listener;

        public CancelOrderListener(OrderWaitPayListener listener, Order order) {
            this.listener = listener;
            this.order = order;
        }

        @Override
        public void onClick(View v) {
            listener.onClickCancelOrder(order);
        }
    }

    public class PayOrderListener implements View.OnClickListener {
        private Order order;
        private OrderWaitPayListener listener;

        public PayOrderListener(OrderWaitPayListener listener, Order order) {
            this.listener = listener;
            this.order = order;
        }

        @Override
        public void onClick(View v) {
            listener.onClickPay(order);
        }
    }


}