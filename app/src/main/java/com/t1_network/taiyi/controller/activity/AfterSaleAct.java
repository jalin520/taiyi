package com.t1_network.taiyi.controller.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.AlertUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.AfterSaleAdapter;
import com.t1_network.taiyi.controller.reveiver.UserUpdateReceiver;
import com.t1_network.taiyi.model.bean.AddressSelected;
import com.t1_network.taiyi.model.bean.AfterSale;
import com.t1_network.taiyi.net.api.address.InputAddressNumAPI;
import com.t1_network.taiyi.net.api.address.SelectAddressAPI;
import com.t1_network.taiyi.net.api.order.AfterCancleApplyAPI;
import com.t1_network.taiyi.net.api.user.AfterSaleAPI;
import com.t1_network.taiyi.widget.TipView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.walink.pulltorefresh.library.PullToRefreshBase;
import cn.walink.pulltorefresh.library.PullToRefreshListView;

public class AfterSaleAct extends BasicAct implements AfterSaleAPI.AfterSaleListener,
        PullToRefreshBase.OnRefreshListener2<ListView>, AfterSaleAdapter.AfterSaleListener,
        AfterCancleApplyAPI.AfterCancleApplyAPIListener, UserUpdateReceiver.UserUpdateListener, SelectAddressAPI.SelectAddressAPIListener, InputAddressNumAPI.OnInputAddressNumAPIListener {

    ListView mListView;

    @Bind(R.id.pull_to_refresh_root)
    PullToRefreshListView refreshLayout;

    @Bind(R.id.c_tip)
    TipView imageDefaultNoOrder;

    AfterSaleAdapter adapter;

    private int pagenum;
    private int pagesize = 10;


    /**
     * 定义4种类型的item
     */
//    private final static int CHECK_PENDING = 1;     //待审核
//    private final static int CHECK_PASS = 2;       //审核通过
//    private final static int VERIFICATION_PENDING = 3;       //商品寄回，待核验
//    private final static int SALE_RETURN_SUCCESS = 4;       //退货成功
    public AfterSaleAct() {
        super(R.layout.act_after_sale, R.string.title_activity_after_sale);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AfterSaleAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        registerReveiver();

        mListView = refreshLayout.getRefreshableView();
        mListView.setDivider(null);

        refreshLayout.setOnRefreshListener(this);


        adapter = new AfterSaleAdapter(new ArrayList<AfterSale>(), this);

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                List<AfterSale> data = adapter.getData();
//                OrderDetailAct.startActivity(AfterSaleAct.this, data.get(position));
//
//            }
//        });

        refreshLayout.setRefreshing(true);
        onPullDownToRefresh(refreshLayout);

    }


    @Override
    public void apiAfterSaleSuccess(List<AfterSale> afterSaleList) {
        if (afterSaleList.size() == 0 && pagenum == 0) {
            imageDefaultNoOrder.showNoData();

        } else {
            imageDefaultNoOrder.hide();
            pagenum += afterSaleList.size();
            adapter.getData().addAll((ArrayList) afterSaleList);
            if (mListView.getAdapter() == null)
                mListView.setAdapter(adapter);

            adapter.notifyDataSetChanged();
        }
        refreshLayout.onRefreshComplete();

    }

    @Override
    public void apiAfterSaleFailure(long code, String msg) {
        showTip(msg);
        refreshLayout.onRefreshComplete();
    }


    //下拉
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pagenum = 0;
        clearAdapter(adapter);
        new AfterSaleAPI(this, pagenum, pagesize);
    }

    //上拉
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        new AfterSaleAPI(this, pagenum, pagesize);
    }

    //创建Adapter
    private void clearAdapter(AfterSaleAdapter adapter) {
        if (adapter != null) {
            adapter.getData().clear();
        }
    }

    //点击进度查询的回调
    @Override
    public void afterSaleDetailClick(View view, AfterSale afterSale) {
        AfterSaleDetailAct.startActivity(this, afterSale);

    }

    private Dialog alertDialog;

    //点击取消申请的回调
    @Override
    public void afterCancleClick(View view, AfterSale afterSale) {

        alertDialog = AlertUtils.comfirmDialog(this, R.string.c_alert_title, ResUtils.getString(R.string.c_alert_is_cancel_after), new OnClickCancelAfterListener(afterSale.getAfter_sale_id()), true);

    }


    //点击查看邮递地址的回调
    @Override
    public void afterCheckAddressClick(View view, AfterSale afterSale) {
        dialog.show();
        new SelectAddressAPI(this);

    }

    //点击填写收货信息的回调
    @Override
    public void afterClickDeliverClick(View view,AfterSale afterSale) {
        alertDialog = AlertUtils.comfirmDialog4Input(this, R.string.c_alert_title,
                ResUtils.getString(R.string.c_alert_is_address_num), new OnClickDeliverListener(afterSale), true);
    }

    //取消申请成功的接口回调
    @Override
    public void apiAfterCancleApplySuccess() {
        showTip("取消申请 : 正在处理中..请稍后.");
        dialog.dismiss();
        UserUpdateReceiver.send(this);
        refreshLayout.setRefreshing(true);
        onPullDownToRefresh(refreshLayout);
    }

    //取消申请失败的接口回调
    @Override
    public void apiAfterCancleApplyFailure(long code, String msg) {
        showTip(msg);
        dialog.dismiss();
    }


    private UserUpdateReceiver userUpdateReceiver;

    private void registerReveiver() {
        userUpdateReceiver = new UserUpdateReceiver(this);
        UserUpdateReceiver.register(this, userUpdateReceiver);
    }

    @Override
    public void receiveUserUpdate() {
        //初始刷新
        refreshLayout.setRefreshing(true);
        onPullDownToRefresh(refreshLayout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserUpdateReceiver.unregister(this, userUpdateReceiver);
    }

    @Override
    public void apiSelectAddressSuccess(AddressSelected addressSelected) {
        dialog.dismiss();
        String addressSelectedStr = addressSelected.getBiz().getValue().getAddress();
        alertDialog = AlertUtils.comfirmDialog(this, R.string.c_alert_title, addressSelectedStr, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                dialog.dismiss();
            }
        }, false);

    }

    @Override
    public void apiSelectAddressFailure(long code, String msg) {
        dialog.dismiss();
        showTip(msg);
    }

    //点击填写收货信息的点击事件,接口返回成功
    @Override
    public void apiInputAddressNumAPISuccess() {
        showTip("处理成功");
        dialog.dismiss();
    }

    //点击填写收货信息的点击事件,接口返回失败
    @Override
    public void apiInputAddressNumAPIFailure(long code, String msg) {
        dialog.dismiss();
        showTip(msg);
    }

    public class OnClickCancelAfterListener implements View.OnClickListener {

        private String afterSaleId;

        public OnClickCancelAfterListener(String afterSaleId) {
            this.afterSaleId = afterSaleId;
        }

        @Override
        public void onClick(View v) {
            alertDialog.dismiss();
            dialog.show();
            new AfterCancleApplyAPI(AfterSaleAct.this, afterSaleId);

        }
    }

    //点击填写收货信息的点击事件
    public class OnClickDeliverListener implements AlertUtils.DialogInputListener {

        private AfterSale afterSale;
        public OnClickDeliverListener(AfterSale afterSale) {
            this.afterSale = afterSale;
        }

        @Override
        public void dialogInputClickListener(String number) {
            alertDialog.dismiss();
            dialog.show();
            String after_sale_orderid = afterSale.getAfter_sale_deliverid();
            String after_sale_remark = afterSale.getAfter_sale_remark();
            new InputAddressNumAPI(AfterSaleAct.this,number,after_sale_orderid,after_sale_remark);
        }
    }
}
