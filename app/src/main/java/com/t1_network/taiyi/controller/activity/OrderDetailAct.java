package com.t1_network.taiyi.controller.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.AlertUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.order.ExpressInfoBean;
import com.t1_network.taiyi.model.bean.order.Order;
import com.t1_network.taiyi.model.bean.order.OrderDetailBean;
import com.t1_network.taiyi.net.api.order.ExpressInfoAPI;
import com.t1_network.taiyi.net.api.order.OrderCancelAPI;
import com.t1_network.taiyi.net.api.order.OrderCompleteAPI;
import com.t1_network.taiyi.net.api.order.OrderDetailAPI;
import com.t1_network.taiyi.utils.VerifyUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class OrderDetailAct extends BasicAct implements OrderDetailAPI.OrderDetailAPIListener, ExpressInfoAPI.ExpressInfoAPIListener, OrderCancelAPI.OrderCancelAPIListener, OrderCompleteAPI.OrderCompleteAPIListener {

    @Bind(R.id.order_detail_receiver_name)
    TextView tvName;//收件人
    @Bind(R.id.order_detail_receiver_phone)
    TextView tvNamePhone;//收件人电话
    @Bind(R.id.order_detail_receiver_address)
    TextView tvNameAddress;//收件人地址
    @Bind(R.id.order_detail_order_id)
    TextView tvOrderNumber;//订单号码
    @Bind(R.id.order_detail_order_time)
    TextView tvOrderTime;//下单时间
    @Bind(R.id.order_detail_order_receipt)
    TextView tvOrderReceipt;//发票信息
    @Bind(R.id.order_detail_token_money)
    TextView tvTokenMoney;//代币
    @Bind(R.id.order_detail_good_money)
    TextView tvSubtotal;//商品合计
    @Bind(R.id.order_detail_total_money)
    TextView tvTotalMoney;//实收价格
    @Bind(R.id.order_detail_subtotal)
    TextView tvSubToTal;
    @Bind(R.id.comfirm_order_layout_good_content)
    LinearLayout llOrderContent;

    @Bind(R.id.comfirm_order_layout_good)
    LinearLayout llContentRoot;

    @Bind(R.id.order_detail_dilevery)
    LinearLayout llDievery;

    @Bind(R.id.order_detail_express_address)
    TextView tvAddressItem;
    @Bind(R.id.order_detail_express_time)
    TextView tvTimeItem;

    @Bind(R.id.item_order_all_list_receive_rl_input_comment)
    RelativeLayout rlComment;//去评价
    @Bind(R.id.item_order_all_list_receive_rl_input)
    RelativeLayout rlReceive;//代收货
    @Bind(R.id.item_order_all_list_pay_rl_input)
    RelativeLayout rlPay;//去付款


    private Order mOrder;
    private String mAllAddress;


    public OrderDetailAct() {
        super(R.layout.act_order_detail, R.string.title_activity_order_detail);
    }


    public static final String P_ORDER = "ORDER";
    private final static String P_ORDER_STATUS = "P_ORDER_STATUS";

    public OrderListAct.OrderStatus status;

    public static void startActivity(Context context, Order order, OrderListAct.OrderStatus status) {
        Intent intent = new Intent(context, OrderDetailAct.class);
        intent.putExtra(P_ORDER, order);
        intent.putExtra(P_ORDER_STATUS, status);
        context.startActivity(intent);
    }

    @Override
    public void initView() {

        Intent intent = getIntent();
        mOrder = intent.getParcelableExtra(P_ORDER);
        status = (OrderListAct.OrderStatus) intent.getSerializableExtra(P_ORDER_STATUS);
        dialog.show();
        new OrderDetailAPI(this, mOrder.getId());

        if (status == OrderListAct.OrderStatus.WAIT_PAY) {
            rlPay.setVisibility(View.VISIBLE);
        } else if (status == OrderListAct.OrderStatus.WAIT_RECEIVE) {
            rlReceive.setVisibility(View.VISIBLE);
            dialog.show();
            new ExpressInfoAPI(this, mOrder.getId());
        } else if (status == OrderListAct.OrderStatus.WAIT_COMMENT) {
            rlComment.setVisibility(View.VISIBLE);

        } else {
            //不处理
        }

    }


    @Override
    public void apiOrderDetailSuccess(OrderDetailBean orderDetailBean) {
        dialog.dismiss();

        OrderDetailBean.OrderEntity order = orderDetailBean.getOrder();
        initOrderData(order);
    }

    private void initOrderData(OrderDetailBean.OrderEntity order) {
        try {
            tvName.setText(ResUtils.getString(R.string.comfirm_order_act_text_consignee, order.getAddress().getName()));
            tvNamePhone.setText(order.getAddress().getPhone());
            mAllAddress = order.getAddress().getProvince() + order.getAddress().getCity() + order.getAddress().getAddress() + order.getAddress().getDistrict();
            tvNameAddress.setText(mAllAddress);
            tvOrderNumber.setText(order.getId());
            tvOrderTime.setText(order.getPaytime());

//            String receipt = order.getReceipt().getType() + "-" + order.getReceipt().getTitle() + "-" + order.getReceipt().getContent();
//            tvOrderReceipt.setText(receipt);

            tvTokenMoney.setText(ResUtils.getString(R.string.money, order.getVoucher()));
            tvSubtotal.setText(ResUtils.getString(R.string.money, order.getSubtotal()));
            tvTotalMoney.setText(ResUtils.getString(R.string.comfirm_order_act_text_total, order.getTotal()));
            tvSubToTal.setText(ResUtils.getString(R.string.act_good_list_text_total, order.getSubtotal()));
            initOrderContent(order);

        } catch (Exception e) {
            TipUtils.toast("数据有误" + e.getMessage());
        }
    }


    private void initOrderContent(OrderDetailBean.OrderEntity order) {
        List<OrderDetailBean.OrderEntity.OrderdetailEntity> orderdetail = order.getOrderdetail();

        for (int i = 0; i < orderdetail.size(); i++) {
            OrderDetailBean.OrderEntity.OrderdetailEntity orderdetailEntity = orderdetail.get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.item_order_return_item, null);
            view.findViewById(R.id.item_order_image).setVisibility(View.GONE);
            if (VerifyUtils.hasImage(orderdetailEntity)) {
                ImageView ivIcon = (ImageView) view.findViewById(R.id.good_image);
                imageLoader.displayImage(orderdetailEntity.getProductpic().get(0).getUrl(), ivIcon);
            }
            TextView tvProductname = (TextView) view.findViewById(R.id.orderName);
            TextView tvPrice = (TextView) view.findViewById(R.id.order_is_seven);
            TextView tvCount = (TextView) view.findViewById(R.id.orderAmount);
            tvProductname.setText(orderdetailEntity.getProductname());
            tvPrice.setText(ResUtils.getString(R.string.money, orderdetailEntity.getPrice()));
            tvCount.setText(ResUtils.getString(R.string.count, orderdetailEntity.getQuantity()));

            if (status == OrderListAct.OrderStatus.WAIT_COMMENT) {
                Button cancelOrder = (Button) view.findViewById(R.id.order_detail_changeOrder);
                cancelOrder.setVisibility(View.VISIBLE);
                cancelOrder.setOnClickListener(new ClickReturnOrderListener(orderdetailEntity));
            }
            view.setOnClickListener(new ClickStartOrderDetailListener(orderdetailEntity));

            llOrderContent.addView(view);

        }

    }

    public class ClickStartOrderDetailListener implements View.OnClickListener {

        private OrderDetailBean.OrderEntity.OrderdetailEntity orderdetail;

        public ClickStartOrderDetailListener(OrderDetailBean.OrderEntity.OrderdetailEntity orderdetail) {
            this.orderdetail = orderdetail;
        }

        @Override
        public void onClick(View v) {
            showTip(orderdetail.getProductname());

            GoodDetailAct.startActivity(OrderDetailAct.this, orderdetail.getProductid());
        }
    }

    public class ClickReturnOrderListener implements View.OnClickListener {

        private OrderDetailBean.OrderEntity.OrderdetailEntity orderdetail;

        public ClickReturnOrderListener(OrderDetailBean.OrderEntity.OrderdetailEntity orderdetail) {
            this.orderdetail = orderdetail;
        }

        @Override
        public void onClick(View v) {
            ReturnSaleAct.startActivity(OrderDetailAct.this, orderdetail, mAllAddress);
        }
    }


    @Override
    public void apiOrderDetailFailure(long code, String mag) {
        showTip(mag);
    }

    @Override
    public void apiExpressInfoSuccess(final ExpressInfoBean infoBean) {
        dialog.dismiss();
        llDievery.setVisibility(View.VISIBLE);
        if (infoBean == null) {
            showTip(infoBean.toString() + ":" + "没有快递数据");
            return;
        }
        List<ExpressInfoBean.InfoEntity> info = null;
        info = infoBean.getInfo();
        if (info == null || info.size() == 0) {
            tvAddressItem.setText("商品正在出货中...");
        } else {
            ExpressInfoBean.InfoEntity infoEntity = info.get(info.size() - 1);
            tvAddressItem.setText(infoEntity.getContent());
            tvTimeItem.setText(infoEntity.getTime());
            llDievery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExpressInformationAct.startActivity(OrderDetailAct.this, mOrder.getId());
                }
            });
        }


    }

    @Override
    public void apiExpressInfoFailure(long code, String msg) {
        showTip(msg);
    }

    private Dialog alertDialog;

    //去付款
    @OnClick(R.id.item_order_wait_pay_btn_pay)
    public void btnPay(View v) {

        PayAct.startActivity(this, mOrder);
        this.finish();
    }

    //取消订单
    @OnClick(R.id.item_order_wait_pay_btn_cancel_order)
    public void btnCancelOrder(View v) {
        alertDialog = AlertUtils.comfirmDialog(this, R.string.c_alert_title, ResUtils.getString(R.string.c_alert_is_cancel_order), new OnClickCancelOrderListener(mOrder.getId()), true);

    }

    //确认收货
    @OnClick(R.id.take_for_order)
    public void onClickCompleteOrder(View v) {
        new OrderCompleteAPI(this, mOrder);

    }

    //查看物流
    @OnClick(R.id.select_logistics)
    public void onClickLookLogistics(View v) {
        ExpressInformationAct.startActivity(this, mOrder.getId());
    }


    //跳转到订单列表
    @OnClick(R.id.order_detail_CommitForOrder)
    public void onClickCommentListener(View v) {
        OrderListAct.startActivity(this, OrderListAct.OrderStatus.WAIT_COMMENT);
        finish();
    }


    //已收货.跳转到已完成
    @Override
    public void apiOrderCompleteSuccess(Order order) {
        finish();
        showTip("订单已确认");
        order.setStatus("1");
        //去人收货回调
        OrderTradeSuccessAct.startActivity(this, order);
    }

    //确认收货失败
    @Override
    public void apiOrderCompleteFailure(long code, String msg) {
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
            new OrderCancelAPI(OrderDetailAct.this, orderId);
        }
    }

    @Override
    public void apiOrderCancelSuccess() {
        showTip("订单已取消");
        dialog.dismiss();
    }

    @Override
    public void apiOrderCancelFailure(long code, String msg) {
        showTip(msg);
        dialog.dismiss();
    }
    //######### 取消end ######


}
