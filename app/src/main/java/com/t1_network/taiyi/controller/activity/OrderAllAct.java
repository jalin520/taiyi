package com.t1_network.taiyi.controller.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.AlertUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.OrderAllAdapter;
import com.t1_network.taiyi.controller.reveiver.UserUpdateReceiver;
import com.t1_network.taiyi.model.bean.order.Order;
import com.t1_network.taiyi.net.api.order.AllOrderAPI;
import com.t1_network.taiyi.net.api.order.OrderCancelAPI;
import com.t1_network.taiyi.net.api.order.OrderCompleteAPI;
import com.t1_network.taiyi.net.api.order.OrderDeleteAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.walink.pulltorefresh.library.PullToRefreshBase;
import cn.walink.pulltorefresh.library.PullToRefreshListView;

public class OrderAllAct extends BasicAct implements AllOrderAPI.AllOrderAPIListener, PullToRefreshBase.OnRefreshListener2, OrderAllAdapter.OrderWaitPayListener, OrderCancelAPI.OrderCancelAPIListener, OrderCompleteAPI.OrderCompleteAPIListener, OrderDeleteAPI.OrderDeleteAPIListener {

    ListView mRecyclerView;
    List<Order> orderList;
    private long limit;

    private OrderAllAdapter mAdapter;

    @Bind(R.id.act_order_All_pull_to_refresh_root)
    PullToRefreshListView layoutRefresh;


    @Bind(R.id.act_order_list_image_default_no_order)
    ImageView imageDefaultNoOrder;

    public OrderAllAct() {
        super(R.layout.act_order_all, R.string.title_activity_order_all);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, OrderAllAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        mRecyclerView = layoutRefresh.getRefreshableView();

        layoutRefresh.setOnRefreshListener(this);

        mAdapter = new OrderAllAdapter(this, new ArrayList<Order>());
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<Order> data = (List<Order>) mAdapter.getData();
                String status = data.get(position - 1).getStatus();
                //0-待支付 1-已完成 2-待发货 3-待收货 4-已取消
                switch (status) {
                    case "0":
                        OrderDetailAct.startActivity(OrderAllAct.this, data.get(position - 1), OrderListAct.OrderStatus.WAIT_PAY);
                        break;
                    case "1":
                        OrderDetailAct.startActivity(OrderAllAct.this, data.get(position - 1), OrderListAct.OrderStatus.WAIT_COMMENT);
                        break;
                    case "3":
                        OrderDetailAct.startActivity(OrderAllAct.this, data.get(position - 1), OrderListAct.OrderStatus.WAIT_RECEIVE);
                        break;
                    default:
                        OrderDetailAct.startActivity(OrderAllAct.this, data.get(position - 1), OrderListAct.OrderStatus.WAIT_All);
                        break;
                }
            }
        });
        //初始刷新
        layoutRefresh.setRefreshing(true);
        onPullDownToRefresh(layoutRefresh);

        mRecyclerView.setOnItemLongClickListener(new OnItemLongClickDeleteListener());

    }

    //成功
    @Override
    public void apiAllOrderSuccess(List<Order> orderList, long total) {
        if (orderList.size() == 0 && limit == 0) {
            imageDefaultNoOrder.setVisibility(View.VISIBLE);
            layoutRefresh.onRefreshComplete();
        } else {
            imageDefaultNoOrder.setVisibility(View.GONE);

            limit += orderList.size();
            mAdapter.getData().addAll((ArrayList) orderList);

            if (mRecyclerView.getAdapter() == null) {
                mRecyclerView.setAdapter(mAdapter);
            }
            layoutRefresh.onRefreshComplete();
            mAdapter.notifyDataSetChanged();


        }
    }

    //失败
    @Override
    public void apiAllOrderFailure(long code, String msg) {
        showTip(msg);
        layoutRefresh.onRefreshComplete();
    }

    //下拉
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        limit = 0;
        clearAdapter(mAdapter);
        new AllOrderAPI(this, limit);
    }

    //上拉
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        new AllOrderAPI(this, limit);
    }

    private Dialog alertDialog;

    //取消订单
    @Override
    public void onClickCancelOrder(Order order) {
        alertDialog = AlertUtils.comfirmDialog(this, R.string.c_alert_title, ResUtils.getString(R.string.c_alert_is_cancel_order), new OnClickCancelOrderListener(order.getId()), true);
    }

    //去付款
    @Override
    public void onClickPay(Order order) {
        PayAct.startActivity(this, order);
    }

    //确认收货
    @Override
    public void onClickCompleteOrder(Order order) {
        dialog.show();
        new OrderCompleteAPI(this, order);
    }

    //查看物流
    @Override
    public void onClickLookLogistics(Order order) {
        ExpressInformationAct.startActivity(this, order.getId());
    }

    //去评价
    @Override
    public void onClickCommentListener(Order order) {
        OrderListAct.startActivity(this, OrderListAct.OrderStatus.WAIT_COMMENT);
        this.finish();
    }

    //取消订单
    @Override
    public void apiOrderCancelSuccess() {
        showTip("订单已取消");
        dialog.dismiss();
        UserUpdateReceiver.send(this);
        layoutRefresh.setRefreshing(true);
        onPullDownToRefresh(layoutRefresh);
    }

    @Override
    public void apiOrderCancelFailure(long code, String msg) {
        showTip(msg);
        dialog.dismiss();
    }

    //确认收货
    @Override
    public void apiOrderCompleteSuccess(Order order) {
        dialog.dismiss();
        order.setStatus("1");
        UserUpdateReceiver.send(this);
        layoutRefresh.setRefreshing(true);
        onPullDownToRefresh(layoutRefresh);
        //去人收货回调
        OrderTradeSuccessAct.startActivity(this, order);
        this.finish();
    }

    //确认收货失败
    @Override
    public void apiOrderCompleteFailure(long code, String msg) {
        dialog.dismiss();
        showTip(msg);
    }

    @Override
    public void apiOrderDeleteAPISuccess(Order order) {
        showTip("订单删除成功");
        dialog.dismiss();
        mAdapter.getData().remove(order);
        mAdapter.notifyDataSetChanged();
        layoutRefresh.setRefreshing(true);
        onPullDownToRefresh(layoutRefresh);
    }

    @Override
    public void apiOrderDeleteAPIFailure(long code, String msg) {
        dialog.dismiss();
        showTip(msg);
    }


    /**
     * 点击确定取消按钮
     */
    public class OnClickCancelOrderListener implements View.OnClickListener {

        private String orderId;

        public OnClickCancelOrderListener(String orderId) {
            this.orderId = orderId;
        }

        @Override
        public void onClick(View v) {
            alertDialog.dismiss();
            dialog.show();
            new OrderCancelAPI(OrderAllAct.this, orderId);
        }
    }
    public class OnItemLongClickDeleteListener implements AdapterView.OnItemLongClickListener{

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Order order = (Order) mAdapter.getData().get(position -1);
            alertDialog = AlertUtils.comfirmDialog(OrderAllAct.this, R.string.c_alert_title, "是否删除订单", new OnClickDelOrderListener(order), true);
            return true;
        }
    }

    /**
     * 点击确定删除订单
     */
    public class OnClickDelOrderListener implements View.OnClickListener {

        private Order order;

        public OnClickDelOrderListener(Order order) {
            this.order = order;
        }

        @Override
        public void onClick(View v) {
            alertDialog.dismiss();
            dialog.show();
            new OrderDeleteAPI(OrderAllAct.this,order);
        }
    }

}
