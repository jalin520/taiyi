package com.t1_network.taiyi.controller.adapter;

import com.t1_network.core.controller.BasicAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.taiyi.R;

import java.util.List;

/**
 * Created by David on 15/12/8.
 */
public class DistrictAdapter extends BasicAdapter {

    public DistrictAdapter(List<?> data) {
        super(data, R.layout.item_district);
    }

    @Override
    public void bind(ViewHolder holder, int position) {
        String name = (String) data.get(position);
        holder.getTextView(R.id.item_district_text_title).setText(name);
    }
}
