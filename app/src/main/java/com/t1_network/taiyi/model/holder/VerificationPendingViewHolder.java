package com.t1_network.taiyi.model.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.t1_network.taiyi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenyu on 2015/11/19.
 * 商品寄回，待核验
 */
public class VerificationPendingViewHolder extends RecyclerView.ViewHolder {

    private final static int VERIFICATION_PENDING = 5;       //商品寄回，待核验

    @Bind(R.id.after_sale_id)
    public TextView after_sale_id_text;
    @Bind(R.id.good_image)
    public ImageView ivIcon;
    @Bind(R.id.orderName)
    public TextView tvOrderName;

    @Bind(R.id.orderAmount)
    public TextView tvQuantity;
    @Bind(R.id.act_after_sale_item_status)
    public TextView tvStatus;
    @Bind(R.id.act_after_sale_item_audit_time)
    public TextView tvAuditTime;


    LinearLayout check_pass_query;

    public VerificationPendingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        check_pass_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AfterSaleDetailAct.startActivity(v.getContext(), VERIFICATION_PENDING);
            }
        });
    }


}
