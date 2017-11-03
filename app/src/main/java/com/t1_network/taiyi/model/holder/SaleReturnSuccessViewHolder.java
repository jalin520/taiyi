package com.t1_network.taiyi.model.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.t1_network.taiyi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenyu on 2015/11/19.
 * 退货成功
 */
public class SaleReturnSuccessViewHolder extends RecyclerView.ViewHolder {

    private final static int SALE_RETURN_SUCCESS = 4;       //退货成功

    @Bind(R.id.after_sale_id)
    TextView after_sale_id_text;

    @Bind(R.id.sale_return_success_query)
    LinearLayout sale_return_success_query;

    public SaleReturnSuccessViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        sale_return_success_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AfterSaleDetailAct.startActivity(v.getContext(), SALE_RETURN_SUCCESS);
            }
        });
    }

    public void setAfter_sale_id_text(String after_sale_id) {
        after_sale_id_text.setText(after_sale_id);
    }


}
