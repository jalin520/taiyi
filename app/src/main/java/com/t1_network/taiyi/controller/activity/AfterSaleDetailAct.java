package com.t1_network.taiyi.controller.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.AlertUtils;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.StringUtils;
import com.t1_network.core.utils.UIUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.reveiver.UserUpdateReceiver;
import com.t1_network.taiyi.model.bean.AddressSelected;
import com.t1_network.taiyi.model.bean.AfterSale;
import com.t1_network.taiyi.model.bean.user.AfterSaleDetail;
import com.t1_network.taiyi.model.holder.CheckPassViewHolder;
import com.t1_network.taiyi.net.api.address.InputAddressNumAPI;
import com.t1_network.taiyi.net.api.address.SelectAddressAPI;
import com.t1_network.taiyi.net.api.order.AfterCancleApplyAPI;
import com.t1_network.taiyi.net.api.orderback.OrderBackDetailAPI;
import com.t1_network.taiyi.utils.VerifyUtils;

import java.util.List;

import butterknife.Bind;

public class AfterSaleDetailAct extends BasicAct implements AfterCancleApplyAPI.AfterCancleApplyAPIListener, SelectAddressAPI.SelectAddressAPIListener, OrderBackDetailAPI.OrderBackDetailAPIListener, InputAddressNumAPI.OnInputAddressNumAPIListener {


    @Bind(R.id.after_sale_detail_process_ll_express)
    LinearLayout llExpress;


    @Bind(R.id.after_sale_detail_process)
    TextView tvOrder;

    @Bind(R.id.act_after_sale_detail_ll_add_root)
    LinearLayout llAddRoot;

    @Bind(R.id.after_sale_process)
    LinearLayout llProcess;

    private AfterSale afterSale;
    /**
     * 定义4种类型的item
     */
    private final static int SALE_RETURN_FAILURE = 0;       //审批不通过
    private final static int SALE_RETURN_SUCCESS = 1;       //退货成功
    private final static int CHECK_PENDING = 2;     //待审核
    private final static int CHECK_PASS = 3;       //审核通过
    private final static int CHECK_RESCINDED = 4;       //已撤销
    private final static int VERIFICATION_PENDING = 5;       //商品寄回，待核验
    private final static int VERIFICATION_FAILURE = 6;       //校验不通过
    private int after_sale_type = 0;
    private View mView;
    private RelativeLayout mRlTitle;
    private RelativeLayout mRvContent;
    private ImageLoader mImageLoader;
    private Dialog mDialog;
    private List<AfterSaleDetail.OrderbackEntity.ProcessEntity> mProcess;


    public AfterSaleDetailAct() {
        super(R.layout.act_after_sale_detail, R.string.title_activity_after_sale_detail);
        mImageLoader = ImageLoader.getInstance();
    }


    public static void startActivity(Context context, AfterSale afterSale) {
        Intent intent = new Intent(context, AfterSaleDetailAct.class);
        intent.putExtra("AFTERSALE", afterSale);
        context.startActivity(intent);

    }


    @Override
    public void initView() {
//
//        LogUtils.e(afterSale.getAfter_sale_id());
        //获取点击“进度查询”的item类型
        afterSale = this.getIntent().getParcelableExtra("AFTERSALE");
        String after_sale_status = afterSale.getAfter_sale_status();
        after_sale_type = Integer.parseInt(after_sale_status);

        new OrderBackDetailAPI(this, afterSale.getAfter_sale_id());

        LogUtils.e(afterSale.getAfter_sale_id());
        String statusType;
        CharSequence highLightText;

        switch (after_sale_type) {

            case VERIFICATION_PENDING:
            case CHECK_PENDING:
//                //待审核

                mView = LayoutInflater.from(this).inflate(R.layout.item_after_sale_check_pass, null);
                LinearLayout llQuery = (LinearLayout) mView.findViewById(R.id.check_pending_query);

                llQuery.setVisibility(View.GONE);
                TextView tvId = (TextView) mView.findViewById(R.id.after_sale_id);
                TextView tvName = (TextView) mView.findViewById(R.id.orderName);
                mView.findViewById(R.id.item_after_sale_iv_goto_image).setVisibility(View.GONE);

                TextView tvAmount = (TextView) mView.findViewById(R.id.orderAmount);
                TextView tvAuditTime = (TextView) mView.findViewById(R.id.act_after_sale_item_audit_time);
                TextView tvStatus = (TextView) mView.findViewById(R.id.act_after_sale_item_status);
                LinearLayout tvCancle = (LinearLayout) mView.findViewById(R.id.item_after_sale_check_pending_cancle);
                LinearLayout llInputCancled = (LinearLayout) mView.findViewById(R.id.item_after_sale_input_ptn);
                if (VerifyUtils.hasImage(afterSale)) {
                    ImageView ivIcon = (ImageView) mView.findViewById(R.id.good_image);
                    mImageLoader.displayImage(afterSale.getProductpic().get(0).getUrl(), ivIcon);
                }

                tvAuditTime.setText(ResUtils.getString(R.string.title_activity_after_sale_audit_time, afterSale.getAfter_sale_audittime()));
                tvAmount.setText(ResUtils.getString(R.string.title_activity_after_sale_quantity, afterSale.getAfter_sale_quantity()));

                tvId.setText(ResUtils.getString(R.string.title_activity_after_sale_id, afterSale.getAfter_sale_orderid()));
                tvName.setText(afterSale.getAfter_sale_productname());


                if (CHECK_PENDING == after_sale_type) {
                    statusType = "状态 : 待审核";
                    highLightText = StringUtils.getHighLightText(statusType, ResUtils.getColor(R.color.text_orange), 4, statusType.length());
                    tvStatus.setText(highLightText);

                    tvCancle.setVisibility(View.GONE);
                    llInputCancled.setVisibility(View.GONE);

                } else if (VERIFICATION_PENDING == after_sale_type) {
                    statusType = "状态 : 商品寄回，待核验";
                    highLightText = StringUtils.getHighLightText(statusType, ResUtils.getColor(R.color.text_orange), 4, statusType.length());
                    tvStatus.setText(highLightText);
                    tvCancle.setVisibility(View.GONE);
                    llInputCancled.setVisibility(View.GONE);
                }


                if (!tvCancle.hasOnClickListeners())
                    tvCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog = AlertUtils.comfirmDialog(AfterSaleDetailAct.this, R.string.c_alert_title, ResUtils.getString(R.string.c_alert_is_cancel_after),
                                    new OnClickCancelAfterDetailListener(afterSale.getAfter_sale_id()), true);
                        }
                    });


                llAddRoot.addView(mView);
                break;
            case SALE_RETURN_SUCCESS:
            case VERIFICATION_FAILURE:
            case CHECK_PASS:
            case SALE_RETURN_FAILURE:
            case CHECK_RESCINDED:
                //审核通过
                mView = LayoutInflater.from(this).inflate(R.layout.item_after_sale_check_pending, null);
                LinearLayout llCancle = (LinearLayout) mView.findViewById(R.id.item_after_sale_check_pending_cancle);
                LinearLayout llInputCancle = (LinearLayout) mView.findViewById(R.id.item_after_sale_input_ptn);
                mView.findViewById(R.id.item_after_sale_iv_goto_image).setVisibility(View.GONE);

                CheckPassViewHolder viewHolder2 = new CheckPassViewHolder(mView);
                viewHolder2.check_pass_query.setVisibility(View.GONE);
                viewHolder2.llCancle.setVisibility(View.VISIBLE);
                viewHolder2.after_sale_id_text.setText(ResUtils.getString(R.string.title_activity_after_sale_id, afterSale.getAfter_sale_orderid()));
                if (VerifyUtils.hasImage(afterSale))
                    mImageLoader.displayImage(afterSale.getProductpic().get(0).getUrl(), viewHolder2.ivIcon);


                viewHolder2.tvOrderName.setText(afterSale.getAfter_sale_productname());
                viewHolder2.tvQuantity.setText(ResUtils.getString(R.string.title_activity_after_sale_quantity, afterSale.getAfter_sale_quantity()));
                viewHolder2.tvAuditTime.setText(ResUtils.getString(R.string.title_activity_after_sale_audit_time, afterSale.getAfter_sale_audittime()));
                if (CHECK_PASS == after_sale_type) {
                    statusType = "状态 : 审核通过";
                    highLightText = StringUtils.getHighLightText(statusType, ResUtils.getColor(R.color.text_green), 4, statusType.length());
                    viewHolder2.tvStatus.setText(highLightText);
                    llInputCancle.setVisibility(View.VISIBLE);
                    llCancle.setVisibility(View.GONE);
                } else if (SALE_RETURN_FAILURE == after_sale_type) {
                    statusType = "状态 : 审核不通过";
                    highLightText = StringUtils.getHighLightText(statusType, ResUtils.getColor(R.color.text_red), 4, statusType.length());
                    viewHolder2.tvStatus.setText(highLightText);
                    llInputCancle.setVisibility(View.GONE);
                    llCancle.setVisibility(View.GONE);
                } else if (SALE_RETURN_SUCCESS == after_sale_type) {
                    statusType = "状态 : 退货成功";
                    highLightText = StringUtils.getHighLightText(statusType, ResUtils.getColor(R.color.text_green), 4, statusType.length());
                    viewHolder2.tvStatus.setText(highLightText);
                    llInputCancle.setVisibility(View.GONE);
                    llCancle.setVisibility(View.GONE);
                } else if (VERIFICATION_FAILURE == after_sale_type) {
                    statusType = "状态 : 校验不通过";
                    highLightText = StringUtils.getHighLightText(statusType, ResUtils.getColor(R.color.text_red), 4, statusType.length());
                    viewHolder2.tvStatus.setText(highLightText);
                    llInputCancle.setVisibility(View.GONE);
                    llCancle.setVisibility(View.GONE);
                } else{
                    statusType = "状态 : 已撤销";
                    highLightText = StringUtils.getHighLightText(statusType, ResUtils.getColor(R.color.text_green), 4, statusType.length());
                    viewHolder2.tvStatus.setText(highLightText);
                    llInputCancle.setVisibility(View.GONE);
                    llCancle.setVisibility(View.GONE);
                }

                viewHolder2.check_deliver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog = AlertUtils.comfirmDialog4Input(AfterSaleDetailAct.this, R.string.c_alert_title,
                                ResUtils.getString(R.string.c_alert_is_address_num), new OnClickDeliverListener(afterSale), true);
                    }
                });

                viewHolder2.llAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.show();
                        new SelectAddressAPI(AfterSaleDetailAct.this);
                    }
                });

                viewHolder2.llCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog = AlertUtils.comfirmDialog(AfterSaleDetailAct.this, R.string.c_alert_title, ResUtils.getString(R.string.c_alert_is_cancel_after),
                                new OnClickCancelAfterDetailListener(afterSale.getAfter_sale_id()), true);
                    }
                });

                llAddRoot.addView(mView);
                break;


        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        llAddRoot.removeAllViews();
    }

    @Override
    public void apiAfterCancleApplySuccess() {
        showTip("取消申请 : 正在处理中..请稍后.");
        dialog.dismiss();
        UserUpdateReceiver.send(this);

    }

    @Override
    public void apiAfterCancleApplyFailure(long code, String msg) {
        showTip(msg);
        dialog.dismiss();

    }

    @Override
    public void apiSelectAddressSuccess(AddressSelected addressSelected) {
        dialog.dismiss();
        String addressSelectedStr = addressSelected.getBiz().getValue().getAddress();
        mDialog = AlertUtils.comfirmDialog(this, R.string.c_alert_title, addressSelectedStr, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                dialog.dismiss();
            }
        }, false);
    }


    @Override
    public void apiSelectAddressFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiOrderBackDetailSuccess(AfterSaleDetail afterSale) {
        mProcess = afterSale.getOrderback().getProcess();
        //列表为空
        if (mProcess == null || mProcess.size() == 0) {
            return;
        }
        //当列表只有一项时
        else if (mProcess.size() == 1) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_after_sale_process, null);
            RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.after_sale_process_views);
            ImageView circle_view = new ImageView(this);
            ImageView line_view = new ImageView(this);
            RelativeLayout.LayoutParams first_circle_lp = new RelativeLayout.LayoutParams(
                    UIUtils.dp2Px(12), UIUtils.dp2Px(12));
            first_circle_lp.setMargins(UIUtils.dp2Px(6), UIUtils.dp2Px(20), 0, 0);
            first_circle_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            circle_view.setBackgroundResource(R.drawable.ic_check_pay_select);
            circle_view.setLayoutParams(first_circle_lp);
            circle_view.setId(R.id.after_sale_process_first_circle_id);
            relativeLayout.addView(circle_view);
            llExpress.addView(view);
        }
        //列表不止一项
        else {
            for (int i = mProcess.size() - 1; i >= 0; i--) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_after_sale_process, null);
                RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.after_sale_process_views);
                ImageView circle_view = new ImageView(this);
                ImageView line_view = new ImageView(this);
                ((TextView) view.findViewById(R.id.after_sale_process_state)).setText(mProcess.get(i).getContent());
                ((TextView) view.findViewById(R.id.after_sale_process_time)).setText(mProcess.get(i).getTime());
                //列表的第一个item
                if (i == mProcess.size() - 1) {
                    RelativeLayout.LayoutParams first_circle_lp = new RelativeLayout.LayoutParams(
                            UIUtils.dp2Px(12.94), UIUtils.dp2Px(12.94));
                    first_circle_lp.setMargins(UIUtils.dp2Px(6), UIUtils.dp2Px(20), 0, 0);
                    first_circle_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    circle_view.setBackgroundResource(R.drawable.ic_check_pay_select);
                    circle_view.setLayoutParams(first_circle_lp);
                    circle_view.setId(R.id.after_sale_process_first_circle_id);

                    RelativeLayout.LayoutParams first_line_lp = new RelativeLayout.LayoutParams(
                            UIUtils.dp2Px(2), UIUtils.dp2Px(60));
                    first_line_lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    first_line_lp.addRule(RelativeLayout.BELOW, R.id.after_sale_process_first_circle_id);
                    first_line_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    line_view.setBackgroundColor(ResUtils.getColor(R.color.bg_divide_line));
                    line_view.setLayoutParams(first_line_lp);
                }
                //列表的最后一个item
                else if (0 == i) {
                    RelativeLayout.LayoutParams last_circle_lp = new RelativeLayout.LayoutParams(
                            UIUtils.dp2Px(8.23), UIUtils.dp2Px(8.23));
                    last_circle_lp.setMargins(UIUtils.dp2Px(2), UIUtils.dp2Px(22), 0, 0);
                    last_circle_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    circle_view.setBackgroundResource(R.drawable.order_detail_express_circle);
                    circle_view.setLayoutParams(last_circle_lp);
                    circle_view.setId(R.id.after_sale_process_last_circle_id);

                    RelativeLayout.LayoutParams last_line_lp = new RelativeLayout.LayoutParams(
                            UIUtils.dp2Px(2), UIUtils.dp2Px(23));
                    last_line_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    line_view.setBackgroundColor(ResUtils.getColor(R.color.bg_divide_line));
                    line_view.setLayoutParams(last_line_lp);
                }
                //其他item
                else {
                    RelativeLayout.LayoutParams circle_lp = new RelativeLayout.LayoutParams(
                            UIUtils.dp2Px(8.23), UIUtils.dp2Px(8.23));
                    circle_lp.setMargins(UIUtils.dp2Px(2), UIUtils.dp2Px(22), 0, 0);
                    circle_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    circle_view.setBackgroundResource(R.drawable.order_detail_express_circle);
                    circle_view.setLayoutParams(circle_lp);
                    circle_view.setId(R.id.after_sale_process_circle_id);

                    RelativeLayout.LayoutParams line_lp = new RelativeLayout.LayoutParams(
                            UIUtils.dp2Px(2), UIUtils.dp2Px(R.dimen.after_sale_process_relativelayout_height));
                    line_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    line_view.setBackgroundColor(ResUtils.getColor(R.color.bg_divide_line));
                    line_view.setLayoutParams(line_lp);
                }

                relativeLayout.addView(circle_view);
                relativeLayout.addView(line_view);

                llExpress.addView(view);
            }
        }

    }

    @Override
    public void apiOrderBackDetailFailure(long code, String msg) {
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


    public class OnClickCancelAfterDetailListener implements View.OnClickListener {

        private String AfterId;

        public OnClickCancelAfterDetailListener(String AfterId) {
            this.AfterId = AfterId;
        }

        @Override
        public void onClick(View v) {
            mDialog.dismiss();
            dialog.show();
            new AfterCancleApplyAPI(AfterSaleDetailAct.this, AfterId);
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
            mDialog.dismiss();
            dialog.show();
            String after_sale_orderid = afterSale.getAfter_sale_deliverid();
            String after_sale_remark = afterSale.getAfter_sale_remark();
            new InputAddressNumAPI(AfterSaleDetailAct.this, number, after_sale_orderid, after_sale_remark);
        }
    }

}
