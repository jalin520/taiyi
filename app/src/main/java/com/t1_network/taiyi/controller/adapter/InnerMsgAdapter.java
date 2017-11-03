package com.t1_network.taiyi.controller.adapter;

import android.view.View;

import com.t1_network.core.controller.BasicListAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.user.SystemMsg;

import java.util.List;

/**
 * Created by chenyu on 2015/11/10.
 */
public class InnerMsgAdapter extends BasicListAdapter {

    public InnerMsgAdapter(List<?> data) {
        super(data, R.layout.item_system_message);
    }

    public void bind(ViewHolder holder, int position) {

        SystemMsg msg = (SystemMsg) data.get(position);

        holder.getTextView(R.id.systemMessageDetail).setText(msg.getContent());
        holder.getTextView(R.id.systemMessageTime).setText(msg.getTime());


    }
}