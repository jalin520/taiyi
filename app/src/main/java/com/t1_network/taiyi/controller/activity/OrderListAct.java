package com.t1_network.taiyi.controller.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.AlertUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.OrderWaitCommentAdapter;
import com.t1_network.taiyi.controller.adapter.OrderWaitPayAdapter;
import com.t1_network.taiyi.controller.adapter.OrderWaitReceiveAdapter;
import com.t1_network.taiyi.model.bean.order.Order;
import com.t1_network.taiyi.model.bean.order.OrderWaitComment;
import com.t1_network.taiyi.model.event.UpdateOrderEvent;
import com.t1_network.taiyi.model.event.UpdateUserEvent;
import com.t1_network.taiyi.net.api.order.OrderCancelAPI;
import com.t1_network.taiyi.net.api.order.OrderCompleteAPI;
import com.t1_network.taiyi.net.api.order.OrderWaitCommentAPI;
import com.t1_network.taiyi.net.api.order.OrderWaitPayAPI;
import com.t1_network.taiyi.net.api.order.OrderWaitReceiveAPI;
import com.t1_network.taiyi.widget.TipView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.walink.pulltorefresh.library.PullToRefreshBase;
import cn.walink.pulltorefresh.library.PullToRefreshListView;
import de.greenrobot.event.EventBus;

//0-待支付 1-已完成 2-待发货 3-待收货 4-已取消

public class OrderListAct extends BasicAct implements OrderWaitPayAPI.OrderWaitPayAPIListener,
        OrderWaitReceiveAPI.OrderWaitReceiveAPIListener, OrderWaitReceiveAdapter.OrderWaitReceiveListener, OrderCompleteAPI.OrderCompleteAPIListener,
        PullToRefreshBase.OnRefreshListener2<ListView>, OrderWaitPayAdapter.OrderWaitPayListener, OrderCancelAPI.OrderCancelAPIListener, OrderWaitCommentAPI.OrderWaitCommentAPIListener {

    ListView recyclerView;

    @Bind(R.id.pull_to_refresh_root)
    PullToRefreshListView layoutRefresh;

    @Bind(R.id.c_tip)
    TipView tipView;

    private long limit = 0;

    private OrderStatus orderStatus;

    public enum OrderStatus {
        //待支付
        WAIT_PAY,
        //待收货
        WAIT_RECEIVE,
        //待评价
        WAIT_COMMENT,
        //全部订单
        WAIT_All
    }

    private OrderWaitPayAdapter payAdapter;
    private OrderWaitReceiveAdapter receiveAdapter;
    private OrderWaitCommentAdapter commentAdapter;


    private final static String P_ORDER_STATUS = "P_ORDER_STATUS";

    public static void startActivity(Context context, OrderStatus status) {
        Intent intent = new Intent(context, OrderListAct.class);
        intent.putExtra(P_ORDER_STATUS, status);
        context.startActivity(intent);
    }

    public OrderListAct() {
        super(R.layout.act_order_list, R.string.title_activity_order_list);
    }


    @Override
    public void initView() {

        EventBus.getDefault().register(this);

        recyclerView = layoutRefresh.getRefreshableView();
        recyclerView.setDivider(null);

        layoutRefresh.setOnRefreshListener(this);

        payAdapter = new OrderWaitPayAdapter(new ArrayList<Order>(), this);
        receiveAdapter = new OrderWaitReceiveAdapter(new ArrayList<Order>(), this);
        commentAdapter = new OrderWaitCommentAdapter(new ArrayList<OrderWaitComment>());

        Intent intent = getIntent();
        //获取订单类型
        if (intent.hasExtra(P_ORDER_STATUS)) {
            orderStatus = (OrderStatus) intent.getSerializableExtra(P_ORDER_STATUS);

            //初始刷新
            layoutRefresh.setRefreshing(true);
            onPullDownToRefresh(layoutRefresh);

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

        limit = 0;

        switch (orderStatus) {
            case WAIT_PAY:
                clearAdapter(payAdapter);
                new OrderWaitPayAPI(this, limit);
                break;
            case WAIT_RECEIVE:
                clearAdapter(receiveAdapter);
                new OrderWaitReceiveAPI(this, limit);
                break;
            case WAIT_COMMENT:
                clearAdapter(commentAdapter);
                new OrderWaitCommentAPI(this, limit);
                break;
        }
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        switch (orderStatus) {
            case WAIT_PAY:
                new OrderWaitPayAPI(this, limit);
                break;
            case WAIT_RECEIVE:
                new OrderWaitReceiveAPI(this, limit);
                break;
            case WAIT_COMMENT:
                new OrderWaitCommentAPI(this, limit);
                break;
        }
    }


    private Dialog alertDialog;

    /**
     * 点击取消订单
     *
     * @param order
     */
    @Override
    public void onClickCancelOrder(Order order) {
        alertDialog = AlertUtils.comfirmDialog(this, R.string.c_alert_title, ResUtils.getString(R.string.c_alert_is_cancel_order), new OnClickCancelOrderListener(order.getId()), true);

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
            new OrderCancelAPI(OrderListAct.this, orderId);
        }
    }

    /**
     * api 取消订单失败
     *
     * @param code
     * @param msg
     */
    @Override
    public void apiOrderCancelFailure(long code, String msg) {
        showTip(msg);
        dialog.dismiss();

        EventBus.getDefault().post(new UpdateUserEvent()); //更新用户信息
        EventBus.getDefault().post(new UpdateOrderEvent()); //更新订单

        layoutRefresh.setRefreshing(true);
        onPullDownToRefresh(layoutRefresh);
    }

    /**
     * 取消订单成功
     */
    @Override
    public void apiOrderCancelSuccess() {
        showTip("订单已取消");
        dialog.dismiss();

        EventBus.getDefault().post(new UpdateUserEvent()); //更新用户信息
        EventBus.getDefault().post(new UpdateOrderEvent()); //更新订单

        layoutRefresh.setRefreshing(true);
        onPullDownToRefresh(layoutRefresh);
    }


    //去付款
    @Override
    public void onClickPay(Order order) {
        PayAct.startActivity(this, order);
    }


    //查看物流地址
    @Override
    public void onClickLookLogistics(Order order) {
        ExpressInformationAct.startActivity(this, order.getId());
    }

    @Override
    public void onClickCancleOrder(Order order) {
        alertDialog = AlertUtils.comfirmDialog(this, R.string.c_alert_title, ResUtils.getString(R.string.c_alert_is_cancel_order), new OnClickCancelOrderListener(order.getId()), true);
    }


    @Override
    public void onClickCompleteOrder(Order order) {
        dialog.show();
        new OrderCompleteAPI(OrderListAct.this, order);
    }


    @Override
    public void apiOrderCompleteFailure(long code, String msg) {
        dialog.dismiss();
        showTip(msg);
    }

    @Override
    public void apiOrderCompleteSuccess(Order order) {
        dialog.dismiss();
        showTip("订单已确认");
        order.setStatus("1");
        OrderTradeSuccessAct.startActivity(this, order);

        EventBus.getDefault().post(new UpdateUserEvent()); //更新用户信息
        EventBus.getDefault().post(new UpdateOrderEvent()); //更新订单

    }


    /**
     * 待支付订单
     */

    @Override
    public void apiOrderWaitPaySuccess(List<Order> orderList, long total) {

        if (orderList.size() == 0 && limit == 0) {
            tipView.showNoData();

        } else {
            tipView.hide();

            limit += orderList.size();

            payAdapter.getData().addAll((ArrayList) orderList);

            if (recyclerView.getAdapter() == null) {
                recyclerView.setAdapter(payAdapter);

                recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        List<Order> data = (List<Order>) payAdapter.getData();
                        OrderDetailAct.startActivity(OrderListAct.this, data.get(position - 1), OrderStatus.WAIT_PAY);


                    }
                });
            }

//            payAdapter.setData(data);
            payAdapter.notifyDataSetChanged();

        }
        layoutRefresh.onRefreshComplete();
    }

    @Override
    public void apiOrderWaitPayFailure(long code, String msg) {
        showTip(msg);
        layoutRefresh.onRefreshComplete();
    }


    /**
     * 待收货订单
     */
    @Override
    public void apiOrderWaitReceiveSuccess(List<Order> orderList, long total) {
        if (orderList.size() == 0 && limit == 0) {
            tipView.showNoData();
        } else {
            tipView.hide();

            limit += orderList.size();

            receiveAdapter.getData().addAll((ArrayList) orderList);

            if (recyclerView.getAdapter() == null) {
                recyclerView.setAdapter(receiveAdapter);
                recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        List<Order> data = (List<Order>) receiveAdapter.getData();
                        OrderDetailAct.startActivity(OrderListAct.this, data.get(position - 1), OrderStatus.WAIT_RECEIVE);
                    }
                });
            }

            receiveAdapter.notifyDataSetChanged();
        }
        layoutRefresh.onRefreshComplete();
    }

    @Override
    public void apiOrderWaitReceiveFailure(long code, String msg) {
        showTip(msg);
        layoutRefresh.onRefreshComplete();
    }

    /**
     * 待评价订单
     */
    @Override
    public void apiOrderWaitCommentFailure(long code, String msg) {
        showTip(msg);
        layoutRefresh.onRefreshComplete();
    }

    @Override
    public void apiOrderWaitCommentSuccess(List<OrderWaitComment> orderList) {
        if (orderList.size() == 0 && limit == 0) {
            tipView.showNoData();
            recyclerView.setVisibility(View.GONE);
        } else {
            tipView.hide();
            recyclerView.setVisibility(View.VISIBLE);

            limit += orderList.size();

            commentAdapter.getData().addAll((ArrayList) orderList);

            if (recyclerView.getAdapter() == null) {
                recyclerView.setAdapter(commentAdapter);

            }

            commentAdapter.notifyDataSetChanged();
        }
        layoutRefresh.onRefreshComplete();
    }


    public void onEventMainThread(UpdateUserEvent event) {
        //初始刷新
        layoutRefresh.setRefreshing(true);
        onPullDownToRefresh(layoutRefresh);
    }


}

