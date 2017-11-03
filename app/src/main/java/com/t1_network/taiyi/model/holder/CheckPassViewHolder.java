package com.t1_network.taiyi.model.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.t1_network.taiyi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenyu on 2015/11/19.
 * 审核通过
 */
public class CheckPassViewHolder  {

    private final static int CHECK_PASS = 3;       //审核通过

    @Bind(R.id.after_sale_id)
    public TextView after_sale_id_text;
    @Bind(R.id.good_image)
    public ImageView ivIcon;
    @Bind(R.id.orderAmount)
    public TextView tvQuantity;
    @Bind(R.id.orderName)
    public TextView tvOrderName;
    @Bind(R.id.act_after_sale_item_status)
    public TextView tvStatus;
    @Bind(R.id.act_after_sale_item_audit_time)
    public TextView tvAuditTime;






    @Bind(R.id.check_pending_query)
    public LinearLayout check_pass_query;

    @Bind(R.id.after_sale_edit_imformation)
    public LinearLayout check_deliver;


    @Bind(R.id.item_after_sale_check_pending_address)
    public LinearLayout llAddress;
    @Bind(R.id.item_after_sale_check_pass_cancle)
    public LinearLayout llCancle;


    public CheckPassViewHolder(View itemView) {
        ButterKnife.bind(this, itemView);
    }


}