package com.t1_network.taiyi.controller.adapter;

import com.t1_network.core.controller.BasicListAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.good.Consult;

import java.util.List;

/**
 * Created by David on 2015/11/26.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class GoodAllConsultAdapter extends BasicListAdapter {

    public GoodAllConsultAdapter(List<?> data) {
        super(data, R.layout.item_good_all_consult);
    }

    @Override
    public void bind(ViewHolder holder, int position) {

        Consult consult = (Consult) data.get(position);

        holder.getTextView(R.id.item_good_all_consult_tv_phone).setText(consult.getUsername());
        holder.getTextView(R.id.item_good_all_consult_tv_date).setText(consult.getTime());
        holder.getTextView(R.id.item_good_all_consult_tv_ask).setText(consult.getQuestion());
        holder.getTextView(R.id.item_good_all_consult_tv_answer).setText(consult.getAnswer());


    }
}
