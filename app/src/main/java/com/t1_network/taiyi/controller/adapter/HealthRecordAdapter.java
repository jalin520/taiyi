package com.t1_network.taiyi.controller.adapter;

import com.t1_network.core.controller.BasicAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.taiyi.R;

import java.util.List;

/**
 * Created by David on 2015/11/12.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class HealthRecordAdapter extends BasicAdapter {

    public HealthRecordAdapter(List<?> data) {
        super(data, R.layout.item_health_record);
    }

    @Override
    public void bind(ViewHolder holder, int position) {
        holder.getTextView(R.id.item_health_tv_title).setText(data.get(position).toString());
    }
}
