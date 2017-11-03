package com.t1_network.taiyi.controller.fragment;

import android.os.Bundle;
import android.view.View;

import com.markmao.pulltorefresh.widget.XListView;
import com.t1_network.core.controller.BasicFrg;
import com.t1_network.core.utils.TimeUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.activity.InputConsultAct;
import com.t1_network.taiyi.controller.adapter.GoodAllConsultAdapter;
import com.t1_network.taiyi.model.bean.good.Consult;
import com.t1_network.taiyi.net.api.good.ConsultAPI;
import com.t1_network.taiyi.widget.TipView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class GoodAllConsultFrg extends BasicFrg implements ConsultAPI.ConsultAPIListener, XListView.IXListViewListener {


    @Bind(R.id.listView)
    XListView mListView;


    public static final String P_GOOD_ID = "P_GOOD_ID";

    public GoodAllConsultFrg() {
        super(R.layout.frg_good_all_consult);
    }


    private String goodId;

    private GoodAllConsultAdapter adapter = new GoodAllConsultAdapter(new ArrayList<Consult>());

    private long limit = 0;

    @Bind(R.id.tip_view)
    TipView tipView;

    @Override
    public void initView(View view) {

        Bundle bundle = getArguments();

        if (bundle == null) {
            return;
        }

        goodId = bundle.getString(P_GOOD_ID);

        initListView();

        mListView.autoRefresh();
    }


    private void initListView() {

        mListView.setAdapter(adapter);
        mListView.setPullRefreshEnable(true);
        mListView.setAutoLoadEnable(true);
        mListView.setPullLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(TimeUtils.getTime());

    }

    @Override
    public void apiCousultFailure(long code, String msg) {
        showTip(msg);
        onLoad();
    }

    @Override
    public void apiCousultSuccess(List<Consult> consultList) {

        if (consultList == null) {
            onLoad();
            return;
        }

        if (consultList.size() == 0 && limit == 0) {
            tipView.showNoData();
            onLoad();
            return;
        }

        tipView.hide();

        limit = limit + consultList.size();
        adapter.getData().addAll((ArrayList) consultList);
        adapter.notifyDataSetChanged();

        onLoad();
    }

    @OnClick(R.id.compar_good_icon)
    public void inputConsult() {
        InputConsultAct.startActivity(getContext(), goodId);
    }

    @Override
    public void onLoadMore() {
        new ConsultAPI(this, goodId, limit);
    }

    @Override
    public void onRefresh() {
        limit = 0;
        clearAdapter(adapter);
        new ConsultAPI(this, goodId, limit);
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(TimeUtils.getTime());
    }
}
