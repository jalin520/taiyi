package com.t1_network.taiyi.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.good.Feature;

import java.util.List;

/**
 * Created by David on 2016/1/21.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class GoodListPopupAdapter extends BaseAdapter {
    private List<Feature> featureList;
    private Context mContext;

    public GoodListPopupAdapter(Context context, List<Feature> featureList) {
        this.mContext = context;
        this.featureList = featureList;

    }

    @Override
    public int getCount() {
        if (featureList != null)
            return featureList.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (featureList != null)
            return featureList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_popup_text_view, null);
            tv = (TextView) convertView.findViewById(R.id.item_good_list_auto_all_sort);
            convertView.setTag(tv);
        }else{
            tv = (TextView) convertView.getTag();
        }

        tv.setText(featureList.get(position).getName());

        return convertView;
    }
}
