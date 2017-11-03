package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;

import com.markmao.pulltorefresh.widget.XListView;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.TimeUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.InnerMsgAdapter;
import com.t1_network.taiyi.model.bean.user.SystemMsg;
import com.t1_network.taiyi.net.api.user.SystemMsgAPI;
import com.t1_network.taiyi.widget.TipView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class InnerActMsg extends BasicAct implements SystemMsgAPI.SystemMsgAPIListener, XListView.IXListViewListener {


    @Bind(R.id.xListView)
    XListView mListView;

    private long limit = 0;
    private InnerMsgAdapter adapter = new InnerMsgAdapter(new ArrayList<SystemMsg>());

    public InnerActMsg() {
        super(R.layout.act_inner_msg, R.string.title_activity_inner_act_msg);
    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, InnerActMsg.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        initListView();
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            mListView.setSelection(0);
            mListView.autoRefresh();
        }
    }

    @Override
    public void onLoadMore() {
        new SystemMsgAPI(this, limit);
    }

    @Override
    public void onRefresh() {
        mListView.setPullLoadEnable(false);
        limit = 0;
        clearAdapter(adapter);
        new SystemMsgAPI(this, limit);
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(TimeUtils.getTime());
    }


    @Bind(R.id.tip_view)
    TipView tipView;


    @Override
    public void apiSystemMsgFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiSystemMsgSuccess(List<SystemMsg> systemMsgLists) {
        if (systemMsgLists == null) {
            onLoad();
            return;
        }

        if (systemMsgLists.size() == 0 && limit == 0) {
            tipView.showNoData();
            onLoad();
            return;
        }

        tipView.hide();
        limit += systemMsgLists.size();
        adapter.getData().addAll((ArrayList) systemMsgLists);
        adapter.notifyDataSetChanged();

        mListView.setPullLoadEnable(true);

        onLoad();
    }
}
