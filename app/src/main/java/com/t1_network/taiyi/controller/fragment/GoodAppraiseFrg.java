package com.t1_network.taiyi.controller.fragment;

import android.os.Bundle;
import android.view.View;

import com.markmao.pulltorefresh.widget.XListView;
import com.t1_network.core.controller.BasicFrg;
import com.t1_network.core.utils.TimeUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.GoodAppraiseAdapter;
import com.t1_network.taiyi.model.bean.good.Comment;
import com.t1_network.taiyi.net.api.good.CommentAPI;
import com.t1_network.taiyi.widget.TipView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class GoodAppraiseFrg extends BasicFrg implements CommentAPI.CommentAPIListener, XListView.IXListViewListener {


    public static final String P_GOOD_ID = "P_GOOD_ID";

    public GoodAppraiseFrg() {
        super(R.layout.frg_good_appraise);
    }

    private String goodId;

    private GoodAppraiseAdapter adapter = new GoodAppraiseAdapter(new ArrayList<Comment>());

    @Bind(R.id.tip_view)
    TipView tipView;

    @Bind(R.id.list_view)
    XListView mListView;

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


    private long limit = 0;

    private void initListView() {

        mListView.setAdapter(adapter);
        mListView.setPullRefreshEnable(true);
        mListView.setAutoLoadEnable(true);
        mListView.setPullLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(TimeUtils.getTime());

    }

    @Override
    public void onLoadMore() {
        new CommentAPI(this, goodId, limit);
    }

    @Override
    public void onRefresh() {
        limit = 0;
        clearAdapter(adapter);
        new CommentAPI(this, goodId, limit);
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(TimeUtils.getTime());
    }

    @Override
    public void apiCommentFailure(long code, String msg) {
        showTip(msg);
        onLoad();
    }

    @Override
    public void apiCommentSuccess(List<Comment> commentList) {

        if (commentList == null) {
            tipView.showError();
            return;
        }

        if (commentList.size() == 0 && limit == 0) {
            tipView.showNoData();
            return;
        }

        tipView.hide();

        limit = limit + commentList.size();
        adapter.getData().addAll((ArrayList) commentList);
        adapter.notifyDataSetChanged();

        onLoad();
    }


}
