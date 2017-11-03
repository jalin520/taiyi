package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.LogisticAdapter;
import com.t1_network.taiyi.model.bean.Logistic;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class LogisticsInfoAct extends BasicAct {

    @Bind(R.id.logistics_recyclerView)
    RecyclerView recyclerView;

    public LogisticsInfoAct() {
        super(R.layout.act_logistics_info, R.string.title_activity_msg_center);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LogisticsInfoAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        List<Logistic> logisticsList = new ArrayList<Logistic>();
        Logistic logistics = null;

        logistics = new Logistic();
        logistics.setLogisticState("商品已发货");
        logistics.setLogisticId("9830393939");
        logistics.setLogisticDetails("您好！您的订单" + logistics.getLogisticId() + "已经发货，感谢您对我们" +
                "的支持。欢迎再次光临，期待您对本次购物进行评价");
        logistics.setLogisticTime("2015-11-11 10:23:12");
        logisticsList.add(logistics);

        logistics = new Logistic();
        logistics.setLogisticState("商品已发货");
        logistics.setLogisticId("9830393939");
        logistics.setLogisticDetails("您好！您的订单" + logistics.getLogisticId() + "已经发货，感谢您对我们" +
                "的支持。欢迎再次光临，期待您对本次购物进行评价");
        logistics.setLogisticTime("2015-11-11 10:23:12");
        logisticsList.add(logistics);

        logistics = new Logistic();
        logistics.setLogisticState("商品已发货");
        logistics.setLogisticId("9830393939");
        logistics.setLogisticDetails("您好！您的订单"+logistics.getLogisticId()+"已经发货，感谢您对我们" +
                "的支持。欢迎再次光临，期待您对本次购物进行评价");
        logistics.setLogisticTime("2015-11-11 10:23:12");
        logisticsList.add(logistics);

        logistics = new Logistic();
        logistics.setLogisticState("商品已发货");
        logistics.setLogisticId("9830393939");
        logistics.setLogisticDetails("您好！您的订单"+logistics.getLogisticId()+"已经发货，感谢您对我们" +
                "的支持。欢迎再次光临，期待您对本次购物进行评价");
        logistics.setLogisticTime("2015-11-11 10:23:12");
        logisticsList.add(logistics);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LogisticAdapter adapter = new LogisticAdapter(logisticsList);
        recyclerView.setAdapter(adapter);
    }


}
