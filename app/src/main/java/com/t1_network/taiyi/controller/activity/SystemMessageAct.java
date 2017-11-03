package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.markmao.pulltorefresh.widget.XListView;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.TimeUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.SystemMessageAdapter;
import com.t1_network.taiyi.model.bean.user.JPushMsg;
import com.t1_network.taiyi.net.api.user.JPushMsgAPI;
import com.t1_network.taiyi.widget.TipView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class SystemMessageAct extends BasicAct implements JPushMsgAPI.JPushMsgAPIListener, XListView.IXListViewListener, AdapterView.OnItemClickListener {

    @Bind(R.id.xListView)
    XListView mListView;

    private long limit = 0;

    private SystemMessageAdapter adapter = new SystemMessageAdapter(new ArrayList<JPushMsg>());


    public SystemMessageAct() {
        super(R.layout.act_system_message, R.string.title_activity_system_message);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SystemMessageAct.class);
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
        mListView.setOnItemClickListener(this);
    }


    private boolean firstLoading = true;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && firstLoading) {
            mListView.setSelection(0);
            mListView.autoRefresh();
            firstLoading = false;
        }
    }

    @Override
    public void onLoadMore() {
        new JPushMsgAPI(this, limit);
    }

    @Override
    public void onRefresh() {
        mListView.setPullLoadEnable(false);
        limit = 0;
        clearAdapter(adapter);
        new JPushMsgAPI(this, limit);
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(TimeUtils.getTime());
    }

    @Bind(R.id.tip_view)
    TipView tipView;

    @Override
    public void apiJPushMsgFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiJPushMsgSuccess(List<JPushMsg> jPushMsgList) {

        if (jPushMsgList == null) {
            onLoad();
            return;
        }

        if (jPushMsgList.size() == 0 && limit == 0) {
            tipView.showNoData();
            onLoad();
            return;
        }

        tipView.hide();
        limit += jPushMsgList.size();
        adapter.getData().addAll((ArrayList) jPushMsgList);
        adapter.notifyDataSetChanged();

        mListView.setPullLoadEnable(true);

        onLoad();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<JPushMsg> jPushMsgList = (List<JPushMsg>) adapter.getData();

        if ((position - 1) % 2 == 0)
            MyAdviceAct.startActivity(this, jPushMsgList.get(position - 1));
        else
            AlreadyEvaluatedAct.startActivity(this, jPushMsgList.get(position - 1));
    }
}
