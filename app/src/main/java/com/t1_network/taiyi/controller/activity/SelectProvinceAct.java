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
import com.t1_network.taiyi.net.api.address.ProvinceAPI;

import java.util.List;

import butterknife.Bind;

public class SelectProvinceAct extends BasicAct implements ProvinceAPI.ProvinceAPIListener {


    @Bind(R.id.act_select_province_recycler_province)
    RecyclerView recyclerProvince;

    public SelectProvinceAct() {
        super(R.layout.act_select_province, R.string.title_activity_select_province);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SelectProvinceAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerProvince.setLayoutManager(lm);

        new ProvinceAPI(this);
    }

    private List<String> data;

    @Override
    public void apiProvinceSuccess(final List<String> provinceList) {
        data = provinceList;
        DistrictAdapter adapter = new DistrictAdapter(provinceList);
        recyclerProvince.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectCityAct.startActivity(SelectProvinceAct.this, data.get(position));
            }
        });
    }

    @Override
    public void apiProvinceFailure(long code, String msg) {
        showTip(msg);
    }
}
