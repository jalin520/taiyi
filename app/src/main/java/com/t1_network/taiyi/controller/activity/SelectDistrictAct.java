package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.DistrictAdapter;
import com.t1_network.taiyi.net.api.address.DistrictAPI;

import java.util.List;

import butterknife.Bind;

public class SelectDistrictAct extends BasicAct implements DistrictAPI.DistrictAPIListener {


    @Bind(R.id.act_select_province_recycler_district)
    RecyclerView recyclerProvince;

    public SelectDistrictAct() {
        super(R.layout.act_select_district, R.string.title_activity_select_district);
    }

    public final static String P_CITY = "P_CITY";
    public final static String P_PROVINCE = "P_PROVINCE";

    public static void startActivity(Context context, String province, String city) {

        Intent intent = new Intent(context, SelectDistrictAct.class);
        intent.putExtra(P_CITY, city);
        intent.putExtra(P_PROVINCE, province);
        context.startActivity(intent);

    }


    String city = "";
    String province = "";

    @Override
    public void initView() {

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerProvince.setLayoutManager(lm);

        Intent intent = getIntent();
        city = intent.getStringExtra(P_CITY);
        province = intent.getStringExtra(P_PROVINCE);

        new DistrictAPI(this, province, city);
    }


    private List<String> data;

    @Override
    public void apiDistrictSuccess(List<String> districtList) {
        data = districtList;
        DistrictAdapter adapter = new DistrictAdapter(districtList);
        recyclerProvince.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AddressEditAct.startActivity(SelectDistrictAct.this, province, city, data.get(position));

            }
        });
    }

    @Override
    public void apiDistrictFailure(long code, String msg) {
        showTip(msg);
    }
}
