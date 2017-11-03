package com.t1_network.taiyi.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.t1_network.core.app.App;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.TokenAdapter;
import com.t1_network.taiyi.model.bean.Voucher;
import com.t1_network.taiyi.net.api.home.HomeAPI;
import com.t1_network.taiyi.net.api.voucher.VoucherAPI;
import com.t1_network.taiyi.widget.TipView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.walink.pulltorefresh.library.PullToRefreshBase;
import cn.walink.pulltorefresh.library.PullToRefreshListView;

public class TokenAct extends BasicAct implements VoucherAPI.VoucherAPIListener, PullToRefreshBase.OnRefreshListener2, HomeAPI.HomeAPIListener {

    @Bind(R.id.pull_to_refresh_root)
    PullToRefreshListView pullToRefreshRecyclerView;
    ListView recyclerView;

    private TokenAdapter adapter;

    @Bind(R.id.act_token_text_token_num)
    TextView textTokenNum;

    @Bind(R.id.act_token_layout_trade_tip)
    RelativeLayout layoutTradeTip;

    private long limit = 0;


    public TokenAct() {
        super(R.layout.act_token, R.string.title_activity_token);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TokenAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        recyclerView = pullToRefreshRecyclerView.getRefreshableView();
        pullToRefreshRecyclerView.setOnRefreshListener(this);


        adapter = new TokenAdapter(new ArrayList<Voucher>());
        recyclerView.setAdapter(adapter);

        pullToRefreshRecyclerView.setRefreshing(true);
        onPullDownToRefresh(pullToRefreshRecyclerView);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        limit = 0;
        new VoucherAPI(this, limit);
        new HomeAPI(this);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        new VoucherAPI(this, limit);
    }

    @Bind(R.id.c_tip)
    TipView layoutTip;

    @Override
    public void apiVoucherSuccess(List<Voucher> voucherList) {
        textTokenNum.setText(App.getApp().getUser().getConsumer().getVoucher());

        if (voucherList.size() == 0 && limit == 0) {
            layoutTip.showNoData();
            layoutTradeTip.setVisibility(View.GONE);
        } else {
            layoutTip.hide();
            layoutTradeTip.setVisibility(View.VISIBLE);
        }

        LogUtils.e("voucherList:" + voucherList.size());

        limit += voucherList.size();

        adapter.getData().addAll((ArrayList) voucherList);
        adapter.notifyDataSetChanged();

        pullToRefreshRecyclerView.onRefreshComplete();
    }

    @Override
    public void apiVoucherFailure(long code, String msg) {
        showTip(msg);
        pullToRefreshRecyclerView.onRefreshComplete();
    }

    @OnClick(R.id.act_token_text_exchange_token)
    public void toExchangeTokenAct() {
        ExchangeTokenAct.startActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            pullToRefreshRecyclerView.setRefreshing(true);
            onPullDownToRefresh(pullToRefreshRecyclerView);
        }
    }

    @OnClick(R.id.act_token_text_help)
    public void toHelp() {
        WebAct.startActivity(this, "http://t1-network.com:3000/doc/vocher_use_help.html", "使用帮助");
    }


    @Override
    public void apiHomeFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiHomeSuccess(String waitPayNum, String waitReceiveNum, String waitCommentNum, String collectNum, String msgNum, String voucherNum) {
        textTokenNum.setText(voucherNum);
    }
}
