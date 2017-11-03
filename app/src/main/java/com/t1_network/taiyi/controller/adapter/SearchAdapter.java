package com.t1_network.taiyi.controller.adapter;

import com.t1_network.core.controller.BasicAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.taiyi.R;

import java.util.List;

/**
 * Created by David on 2015/12/9.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class SearchAdapter extends BasicAdapter {
    public SearchAdapter(List<?> data) {
        super(data, R.layout.item_good_list_search_rv_item);
    }

    @Override
    public void bind(ViewHolder holder, int position) {
        String name = (String) data.get(position);
        holder.getTextView(R.id.act_search_tv_name).setText(name);
    }

}
