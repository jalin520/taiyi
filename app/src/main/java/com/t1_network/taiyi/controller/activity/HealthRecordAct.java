package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.HealthRecordAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class HealthRecordAct extends BasicAct {

    public HealthRecordAct() {
        super(R.layout.act_health_record, R.string.title_activity_health_record);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HealthRecordAct.class);
        context.startActivity(intent);
    }


    @Bind(R.id.health_select_recycler_view)
    RecyclerView recyclerView;

    private List<String> mDatas;
    private HealthRecordAdapter adapter;

    @Override
    public void initView() {

        mDatas = addDAata();

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new HealthRecordAdapter(mDatas);

        recyclerView.setAdapter(adapter);

    }

    private List<String> addDAata() {
        List<String> data = new ArrayList<>();
        data.add("健康档案");
        return data;
    }


    @OnClick(R.id.act_health_add)
    public void addHealthRecord() {
        TipUtils.snackBar(this.recyclerView, "添加成功");

        mDatas.add("健康档案");

        recyclerView.getAdapter().notifyDataSetChanged();
    }


}
