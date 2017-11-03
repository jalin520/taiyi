package com.t1_network.taiyi.controller.adapter;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.t1_network.core.controller.BasicListAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.UIUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.activity.AddressEditAct;
import com.t1_network.taiyi.model.bean.Address;

import java.util.List;

/**
 * Created by David on 2015/11/6.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class AddressAdapter extends BasicListAdapter {

    Activity activityContext;

    private static final String DEFAULT = "1";    //默认地址

    public AddressAdapter(List<?> data, Activity activityContext) {
        super(data, R.layout.item_address);
        this.activityContext = activityContext;
    }

    @Override
    public void bind(ViewHolder holder, int position) {

        final Address address = (Address) data.get(position);
        holder.getTextView(R.id.consignee_address_text_name).setText(address.getName());
        holder.getTextView(R.id.consignee_address_text_phone).setText(address.getPhone());
        holder.getTextView(R.id.consignee_address_text_address).setText(address.getProvince() + " " + address.getCity() + " " + address.getDistrict() + " " + address.getAddress());

        ImageView edit = holder.getImageView(R.id.consignee_address_image_edit);

        if (!edit.hasOnClickListeners()) {
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddressEditAct.startActivity(activityContext, address);
                }
            });
        }

        RelativeLayout relativeLayout = (RelativeLayout) holder.get(R.id.consignee_address_relativelayout);

        if (DEFAULT.equals(address.getDefaults())) {
            //当前地址为默认地址
            TextView textview = new TextView(context);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    UIUtils.dp2Px(30.59), UIUtils.dp2Px(16.47));
            lp.setMargins(UIUtils.dp2Px(16.47), 0, 0, 0);
            lp.addRule(RelativeLayout.RIGHT_OF, R.id.consignee_address_text_phone);
            lp.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.consignee_address_text_phone);
            textview.setBackgroundResource(R.drawable.bg_btn_normal_2);
            textview.setLayoutParams(lp);
            textview.setText(R.string.act_consignee_address_btn_default);
            textview.setTextSize(12);
            textview.setTextColor(ResUtils.getColor(R.color.textWhite));
            textview.setGravity(Gravity.CENTER);
            relativeLayout.addView(textview);
        }
    }


    public class AddressItemClickListener implements AdapterView.OnItemClickListener {

        private Address address;

        public AddressItemClickListener(Address address) {
            this.address = address;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }

    public interface clickAddressListener {
        public void clickAddress(Address address);
    }

}
