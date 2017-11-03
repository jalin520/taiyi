package com.t1_network.taiyi.controller.adapter;

import android.support.v7.widget.CardView;
import android.view.View;

import com.t1_network.core.controller.BasicAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.R;

import java.util.List;


public class HomeIconAdapter extends BasicAdapter {

    public HomeIconAdapter(List<?> data) {
        super(data, R.layout.item_frg_home_good_rv_item);
    }

    @Override
    public void bind(final ViewHolder holder, final int position) {
        Integer id = (Integer) data.get(position);
        holder.getImageView(R.id.item_frg_home_good_rv_icon).setImageResource(id);



        CardView cardView = holder.get(R.id.item_frg_home_good_rv_root);
        if(!cardView.hasOnClickListeners()){
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TipUtils.snackBar(holder.get(R.id.item_frg_home_good_rv_root),"点击了"+position);
                }
            });
        }

    }
}
