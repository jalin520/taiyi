package com.t1_network.taiyi.controller.adapter;

import com.t1_network.core.controller.BasicAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.SpanStringUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.Logistic;

import java.util.List;

/**
 * Created by chenyu on 2015/11/9.
 */
public class LogisticAdapter extends BasicAdapter {

    public LogisticAdapter(List<?> data) {
        super(data, R.layout.item_logistics_info);
    }

    public void bind(ViewHolder holder, int position) {

        Logistic logistics = (Logistic) data.get(position);

        CharSequence cs = SpanStringUtils.getHighLightText(ResUtils.getColor(R.color.text_red),
                logistics.getLogisticDetails(),
                logistics.getLogisticId());

        holder.getTextView(R.id.logistics_details).setText(cs);
        holder.getTextView(R.id.logistics_time).setText(logistics.getLogisticTime());
        holder.getTextView(R.id.logistics_state).setText(logistics.getLogisticState());


    }
}
