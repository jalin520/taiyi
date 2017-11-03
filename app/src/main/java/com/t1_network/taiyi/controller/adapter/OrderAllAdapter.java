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
 * Created by David on 2016/1/8.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class OrderAllAdapter extends BasicListAdapter implements OrderButtonListener.ButtonOnClickListener {

    View inflateView;
    private OrderWaitPayListener listener;


    public OrderAllAdapter(OrderWaitPayListener listener, List<?> data) {
        super(data, R.layout.item_order_all_list);
        this.listener = listener;
    }


    @Override
    public void bind(ViewHolder holder, int position) {

        Order order = (Order) data.get(position);

        holder.getTextView(R.id.orderId).setText("订单号: " + order.getId());


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

        TextView tvOrderState = holder.getTextView(R.id.orderState);

        View receiveView = holder.get(R.id.item_order_all_list_receive_rl_input);
        View payView = holder.get(R.id.item_order_all_list_pay_rl_input);
        View comment = holder.get(R.id.item_order_all_list_receive_rl_input_comment);

        //0-待支付 1-已完成 2-待发货 3-待收货 4-已取消
        if (order.getStatus().equals("0")) {
            receiveView.setVisibility(View.GONE);
            payView.setVisibility(View.VISIBLE);
            comment.setVisibility(View.GONE);
            tvOrderState.setText(R.string.order_pay_state);
            //设置button的点击事件
            Button btnCancelOrder = holder.getButton(R.id.item_order_wait_pay_btn_cancel_order);
            if (!btnCancelOrder.hasOnClickListeners()) {
                btnCancelOrder.setOnClickListener(new CancelOrderListener(listener, order));
            }
            Button btnPay = holder.getButton(R.id.item_order_wait_pay_btn_pay);
            if (!btnPay.hasOnClickListeners()) {
                btnPay.setOnClickListener(new PayOrderListener(listener, order));
            }
        } else if (order.getStatus().equals("1")) {
            comment.setVisibility(View.VISIBLE);
            receiveView.setVisibility(View.GONE);
            payView.setVisibility(View.GONE);
            tvOrderState.setText(R.string.order_state_text);
            Button commentBtn = holder.getButton(R.id.order_detail_CommitForOrder);
            if (!commentBtn.hasOnClickListeners()){
                commentBtn.setOnClickListener(new ClickCommentListener(listener,order));
            }

        }else if (order.getStatus().equals("2")) {
            receiveView.setVisibility(View.GONE);
            payView.setVisibility(View.GONE);
            comment.setVisibility(View.GONE);
            tvOrderState.setText(R.string.order_receive_state_delivery);
        } else if (order.getStatus().equals("4")) {
            receiveView.setVisibility(View.GONE);
            comment.setVisibility(View.GONE);
            payView.setVisibility(View.GONE);
            tvOrderState.setText(R.string.order_receive_state_cancel);
        } else if (order.getStatus().equals("3")) {
            receiveView.setVisibility(View.VISIBLE);
            payView.setVisibility(View.GONE);
            comment.setVisibility(View.GONE);
            tvOrderState.setText(R.string.order_receive_state);
            //设置button的点击事件
            Button selectButton = holder.getButton(R.id.select_logistics);
            if (!selectButton.hasOnClickListeners())
                selectButton.setOnClickListener(new OrderButtonListener(order, this));

            Button takeButton = holder.getButton(R.id.take_for_order);
            if (!takeButton.hasOnClickListeners())
                takeButton.setOnClickListener(new OrderButtonListener(order, this));



        }
    }


    public interface OrderWaitPayListener {
        void onClickCancelOrder(Order order);

        void onClickPay(Order order);

        void onClickCompleteOrder(Order order);

        void onClickLookLogistics(Order order);

        void onClickCommentListener(Order order);
    }

    private class ClickCommentListener implements View.OnClickListener {

        private Order order;
        private OrderWaitPayListener listener;

        public ClickCommentListener(OrderWaitPayListener listener, Order order) {
            this.listener = listener;
            this.order = order;
        }

        @Override
        public void onClick(View v) {
            listener.onClickCommentListener(order);
        }
    }

    private class CancelOrderListener implements View.OnClickListener {

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

    private class PayOrderListener implements View.OnClickListener {

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
        }
    }


}
