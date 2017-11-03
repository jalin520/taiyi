package com.t1_network.taiyi.controller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.t1_network.core.controller.BasicListAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.activity.EvaluateAct;
import com.t1_network.taiyi.model.bean.order.OrderWaitComment;
import com.t1_network.taiyi.utils.VerifyUtils;

import java.util.List;

/**
 * Created by chenyu on 2015/11/12.
 */
public class OrderWaitCommentAdapter extends BasicListAdapter {


    public OrderWaitCommentAdapter(List<?> data) {
        super(data, R.layout.item_order_wait_comment);
    }

    public void bind(ViewHolder holder, int position) {

        OrderWaitComment order = (OrderWaitComment) data.get(position);

        holder.getTextView(R.id.orderId).setText(ResUtils.getString(R.string.act_good_list_text_order_id, order.getOrderId()));
        LinearLayout ll = holder.get(R.id.item_order_wait_pay_ll);
        ll.removeAllViews();

        View view = LayoutInflater.from(context).inflate(R.layout.item_order_wait_comment_plus, ll, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.orderName);
        tvTitle.setText(order.getGoodName());

        if (VerifyUtils.hasImage(order)) {
            ImageView imageView = (ImageView) view.findViewById(R.id.good_image);
            imageLoader.displayImage(order.getImage(), imageView);
        }
        Button commitButton = (Button) view.findViewById(R.id.commentOrder);
        commitButton.setOnClickListener(new OnClickEvaluateButtonListener(order));

        ll.addView(view);


    }


    public class OnClickEvaluateButtonListener implements View.OnClickListener {

        private OrderWaitComment order;

        public OnClickEvaluateButtonListener(OrderWaitComment order) {
            this.order = order;
        }

        @Override
        public void onClick(View v) {
            EvaluateAct.startActivity(context, order);
//            TipUtils.toast(order.getGoodName());
        }
    }


}
