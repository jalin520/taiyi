package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.order.ExpressInfoBean;
import com.t1_network.taiyi.net.api.order.ExpressInfoAPI;

import java.util.List;

import butterknife.Bind;

public class ExpressInformationAct extends BasicAct implements ExpressInfoAPI.ExpressInfoAPIListener {

    @Bind(R.id.express_info_ll)
    LinearLayout llInfo;

    @Bind(R.id.act_express_information_company)
    TextView tvCompany;
    @Bind(R.id.act_express_information_number)
    TextView tvNumber;
    @Bind(R.id.express_info_status)
    TextView tvStatus;
    private ExpressInfoBean infoBean;

    public ExpressInformationAct() {
        super(R.layout.act_express_information, R.string.title_activity_express_information);
    }


    public static void startActivity(Context context, String id) {
        Intent intent = new Intent(context, ExpressInformationAct.class);
        intent.putExtra("EXPRESS", id);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("EXPRESS");
        dialog.show();
        new ExpressInfoAPI(this, id);
    }


    @Override
    public void apiExpressInfoSuccess(ExpressInfoBean infoBean) {
        dialog.dismiss();
        tvCompany.setText(ResUtils.getString(R.string.title_activity_express_company, infoBean.getCompany()));
        tvNumber.setText(ResUtils.getString(R.string.title_activity_express_company_number, infoBean.getDelivery()));
        initInfoContent(infoBean);
    }

    private void initInfoContent(ExpressInfoBean infoBean) {
        try {
            List<ExpressInfoBean.InfoEntity> info = infoBean.getInfo();
            if (info == null || info.size() == 0) {
                tvStatus.setText("物流快递正在处理中.....");
                return;
            }
            tvCompany.setVisibility(View.VISIBLE);
            tvNumber.setVisibility(View.VISIBLE);
            for (int i = 0; i < info.size(); i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_express_info_show_time, null);
                ((TextView) view.findViewById(R.id.item_express_text_title)).setText(info.get(i).getContent());
                ((TextView) view.findViewById(R.id.item_express_text_show_time)).setText(info.get(i).getTime());
                if (i == 0) {
                    ((ImageView) view.findViewById(R.id.item_express_image)).setImageResource(R.drawable.ic_received);
                    view.findViewById(R.id.item_express_view_top).setVisibility(View.INVISIBLE);
                }
                if (info.size() - 1 == i) {
                    view.findViewById(R.id.item_express_view_below).setVisibility(View.INVISIBLE);
                    ((ImageView) view.findViewById(R.id.item_express_image)).setImageResource(R.drawable.ic_logistics);
                }
                llInfo.addView(view);
            }
        } catch (Exception e) {

        }

    }

    @Override
    public void apiExpressInfoFailure(long code, String msg) {
        dialog.dismiss();
        showTip(msg);
    }
}
