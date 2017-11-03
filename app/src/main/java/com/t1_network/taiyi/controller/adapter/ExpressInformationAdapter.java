package com.t1_network.taiyi.controller.adapter;

import android.view.View;

import com.t1_network.core.controller.BasicAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.order.ExpressInfoBean;

import java.util.List;

/**
 * Created by David on 2015/11/10.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class ExpressInformationAdapter extends BasicAdapter {


    public ExpressInformationAdapter(List<?> data) {
        super(data, R.layout.item_express_info_show_time);
    }

    @Override
    public void bind(ViewHolder holder, int position) {
        ExpressInfoBean.InfoEntity infoEntity = (ExpressInfoBean.InfoEntity) data.get(position);
        String title = infoEntity.getContent();
        holder.getTextView(R.id.item_express_text_title).setText(title);
        if (position == 0) {
            holder.getImageView(R.id.item_express_image).setImageResource(R.drawable.ic_received);
            holder.get(R.id.item_express_view_top).setVisibility(View.INVISIBLE);
        }
        if (data.size() - 1 == position) {
            holder.get(R.id.item_express_view_below).setVisibility(View.GONE);
            holder.getImageView(R.id.item_express_image).setImageResource(R.drawable.ic_logistics);
        }

    }
}
