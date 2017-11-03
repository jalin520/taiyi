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
import com.t1_network.taiyi.net.api.address.CityAPI;

import java.util.List;

import butterknife.Bind;

public class SelectCityAct extends BasicAct implements CityAPI.CityAPIListener {


    @Bind(R.id.act_select_province_recycler_city)
    RecyclerView recyclerProvince;


    public SelectCityAct() {
        super(R.layout.act_select_city, R.string.title_activity_select_city);
    }

    public final static String P_PROVINCE = "P_PROVINCE";

    public static void startActivity(Context context, String province) {

        Intent intent = new Intent(context, SelectCityAct.class);
        intent.putExtra(P_PROVINCE, province);
        context.startActivity(intent);

    }

    private String province = "";

    @Override
    public void initView() {

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerProvince.setLayoutManager(lm);

        Intent intent = getIntent();
        province = intent.getStringExtra(P_PROVINCE);

        new CityAPI(this, province);
    }


    private List<String> data;

    @Override
    public void apiCitySuccess(List<String> cityList) {
        data = cityList;
        DistrictAdapter adapter = new DistrictAdapter(cityList);
        recyclerProvince.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectDistrictAct.startActivity(SelectCityAct.this, province, data.get(position));
            }
        });
    }

    @Override
    public void apiCityFailure(long code, String msg) {
        showTip(msg);
    }
}
