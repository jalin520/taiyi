package com.t1_network.taiyi.controller.adapter;

import com.t1_network.core.controller.BasicAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.taiyi.R;

import java.util.List;

/**
 * Created by David on 2016/1/28.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class MyAdviceAdapter extends BasicAdapter {

    public MyAdviceAdapter(List<?> data) {
        super(data, R.layout.item_my_advice_item);
    }

    @Override
    public void bind(ViewHolder holder, int position) {
        if (position %2 == 0){
            holder.getImageView(R.id.item_my_advice_iv_icon).setImageResource(R.drawable.ic_answer_icon);
            holder.getTextView(R.id.item_my_advice_tv_content).setText("问题");
        }else{
            holder.getImageView(R.id.item_my_advice_iv_icon).setImageResource(R.drawable.ic_ask_icon);
            holder.getTextView(R.id.item_my_advice_tv_content).setText("回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答");
        }

    }
}
