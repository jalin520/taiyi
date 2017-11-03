package com.t1_network.taiyi.controller.adapter;

import com.t1_network.core.controller.BasicListAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.Voucher;

import java.util.List;

/**
 * Created by chenyu on 2015/11/11.
 */
public class TokenAdapter extends BasicListAdapter {

    public TokenAdapter(List<?> data) {
        super(data, R.layout.item_token);
    }

    public void bind(ViewHolder holder, int position) {

        Voucher voucher = (Voucher) data.get(position);

        holder.getTextView(R.id.my_token_name).setText(voucher.getContent());
        holder.getTextView(R.id.my_token_time).setText(voucher.getTime());
        holder.getTextView(R.id.my_token_Id).setText(voucher.getId());

        if ("0".equals(voucher.getInOut())) {
            holder.getTextView(R.id.my_token_money).setText("-" + voucher.getCount());

        } else {
            holder.getTextView(R.id.my_token_money).setText("+" + voucher.getCount());
        }


    }
}