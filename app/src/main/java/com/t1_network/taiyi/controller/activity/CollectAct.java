package com.t1_network.taiyi.controller.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.AlertUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.SpanStringUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.CollectAdapter;
import com.t1_network.taiyi.controller.reveiver.UserComparReceiver;
import com.t1_network.taiyi.db.TYSP;
import com.t1_network.taiyi.model.bean.good.Good;
import com.t1_network.taiyi.model.bean.good.GoodDetail;
import com.t1_network.taiyi.model.event.CollectEvent;
import com.t1_network.taiyi.net.api.good.CollectAPI;
import com.t1_network.taiyi.net.api.good.GoodDetailAPI;
import com.t1_network.taiyi.net.api.user.CollectionAPI;
import com.t1_network.taiyi.widget.TipView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.walink.pulltorefresh.library.PullToRefreshBase;
import cn.walink.pulltorefresh.library.PullToRefreshListView;
import de.greenrobot.event.EventBus;

/**
 * 功能：我的收藏
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-11-10
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class CollectAct extends BasicAct implements PullToRefreshBase.OnRefreshListener2, CollectionAPI.CollectionByTYListener, CollectAdapter.AddCompareListener, GoodDetailAPI.GoodDetailAPIListener, UserComparReceiver.UserComparReceiverListener, CollectAdapter.OnLongClickItemListener, CollectAPI.CollectAPIListener {


    @Bind(R.id.common_layout_refresh)
    PullToRefreshListView layoutRefresh;

    ListView listView;

    private CollectAdapter adapter;

    @Bind(R.id.c_tip)
    TipView tipView;


    private long limit = 0;
    private UserComparReceiver mUserComparReceiver;
    private Dialog mAlertDialog;

    public CollectAct() {
        super(R.layout.act_collect, R.string.title_activity_collect);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CollectAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        initContrast(TYSP.getCompareGood().size()); // 初始化对比
        layoutRefresh.setOnRefreshListener(this);
        listView = layoutRefresh.getRefreshableView();
        layoutRefresh.setRefreshing(true);
        onPullDownToRefresh(layoutRefresh);

        adapter = new CollectAdapter(new ArrayList<Good>());
        adapter.setListener(this);
        listView.setAdapter(adapter);
        //比较数量的监听器
        compareNumberReceiver();
        adapter.setOnLongClickItemListener(this);
    }

    private void compareNumberReceiver() {
        mUserComparReceiver = new UserComparReceiver(this);
        UserComparReceiver.register(this,mUserComparReceiver);

    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        limit = 0;
        clearAdapter(adapter);
        new CollectionAPI(this, limit);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        new CollectionAPI(this, limit);
    }

    @Override
    public void apiCollectionBySuccess(List<Good> goodList) {


        if (goodList.isEmpty() && limit == 0) {
            tipView.showNoData();
        } else {

            tipView.hide();
        }

        limit += goodList.size();

        adapter.getData().addAll((ArrayList) goodList);

        adapter.notifyDataSetChanged();
        layoutRefresh.onRefreshComplete();

    }

    @Override
    public void apiCollectionByFailure(long code, String msg) {
        showTip(msg);
        layoutRefresh.onRefreshComplete();
    }

    @Override
    public void AddCompare(Good mGood) {
        List<GoodDetail> goodList = TYSP.getCompareGood();
        for (GoodDetail detail : goodList) {

            if (detail.getProduct().getId().equals(mGood.getId())) {
                showTip("此商品已添加了");
                return;
            }
        }
        new GoodDetailAPI(this, mGood.getId());

    }

    @Override
    public void apiGoodDetailAPISuccess(GoodDetail goodDetail) {
        TYSP.addCompareGood(goodDetail);
        initContrast(TYSP.getCompareGood().size()); // 初始化对比

    }

    @Override
    public void apiGoodDetailAPIFailure(long code, String msg) {
        showTip(msg);
    }

    @OnClick(R.id.act_token_text_exchange_token)
    public void toCompare() {
        CompareGoodAct.startActivity(this);
    }


    @Bind(R.id.act_token_text_exchange_token)
    TextView textContrast;

    /**
     * init 对比按钮
     *
     * @param num
     */
    private void initContrast(long num) {
        String numStr = ResUtils.getString(R.string.act_good_list_text_contrast_span, num + "");
        String str = ResUtils.getString(R.string.act_good_list_text_contrast) + numStr;
        textContrast.setText(SpanStringUtils.getHighLightText(ResUtils.getColor(R.color.colorPrimary), str, numStr));
        textContrast.setClickable(true);
        textContrast.setFocusable(true);
    }

    @Override
    public void receiverCompar() {
        initContrast(TYSP.getCompareGood().size()); // 初始化对比
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserComparReceiver.unregister(this,mUserComparReceiver);
    }



    @Override
    public void OnLongClickItemGoodListener(String id) {
        mAlertDialog = AlertUtils.comfirmDialog(this, R.string.c_alert_title, "是否删除收藏记录", new OnClickDelOrderListener(id), true);

    }

    @Override
    public void apiCollectSuccess() {
        dialog.dismiss();
        showTip("取消成功");
        layoutRefresh.setRefreshing(true);
        onPullDownToRefresh(layoutRefresh);
        EventBus.getDefault().post(new CollectEvent());
    }

    @Override
    public void apiCollectFailure(long code, String msg) {
        dialog.dismiss();
        showTip(msg);
    }

    /**
     * 点击确定删除订单
     */
    public class OnClickDelOrderListener implements View.OnClickListener {

        private String id;

        public OnClickDelOrderListener(String id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            mAlertDialog.dismiss();
            dialog.show();
            new CollectAPI(CollectAct.this,id);
        }
    }

}
