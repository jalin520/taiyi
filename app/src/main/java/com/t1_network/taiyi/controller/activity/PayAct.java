package com.t1_network.taiyi.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;

import com.pingplusplus.android.PaymentActivity;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.order.Order;
import com.t1_network.taiyi.model.event.UpdateUserEvent;
import com.t1_network.taiyi.net.api.order.PayAPI;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class PayAct extends BasicAct implements PayAPI.PayAPIListener {

    @Bind(R.id.pay_act_image_select_ali)
    ImageView imageSelectAli;

    @Bind(R.id.pay_act_image_select_wechat)
    ImageView imageSelectWechat;

    @Bind(R.id.pay_act_image_select_baiduWallet)
    ImageView imageSelectBaiduWallet;

    @Bind(R.id.pay_act_image_select_JDWallet)
    ImageView imageSelectJDWallet;

    @Bind(R.id.pay_act_image_select_quick)
    ImageView imageSelectQuickPay;

    @Bind(R.id.pay_act_image_select_union)
    ImageView imageSelectUnionWay;

    /**
     * 六种支付方式
     */
    private static final int ALI_PAY = 1;     //支付宝支付
    private static final int WECHAT_PAY = 2;     //微信支付
    private static final int BAIDUWALLET_PAY = 3;   //百度钱包支付
    private static final int JDWALLET_PAY = 4;      //京东钱包支付
    private static final int QUICK_PAY = 5;       //快捷支付
    private static final int UNION_PAY = 6;       //银联支付


    private static final String ALI_PAY_NAME = "alipay";
    private static final String WECHAT_PAY_NAME = "wx";
    private static final String BAIDU_PAY_NAME = "bfb";
    private static final String JD_PAY_NAME = "bfb";
    private static final String QUICK_PAY_NAME = "bfb";
    private static final String UNION_PAY_NAME = "upacp";


    //默认以支付宝支付
    private int payWay = ALI_PAY;     //支付方式
    private String payName = ALI_PAY_NAME;


    public PayAct() {
        super(R.layout.act_pay, R.string.title_activity_pay);
    }


    public static final String P_ORDER = "P_ORDER";

    public static void startActivity(Context context, Order order) {
        Intent intent = new Intent(context, PayAct.class);
        intent.putExtra(P_ORDER, order);
        context.startActivity(intent);
    }

    private Order order;

    @Override
    public void initView() {

        Intent intent = getIntent();
        order = intent.getParcelableExtra(P_ORDER);

    }

    @OnClick(R.id.pay_act_layout_ali_pay)
    public void selectAliPay() {

        initSelect();
        imageSelectAli.setImageResource(R.drawable.ic_check_pay_select);
        payWay = ALI_PAY;
        payName = ALI_PAY_NAME;
    }

    @OnClick(R.id.pay_act_layout_wechat_pay)
    public void selectWechatPay() {
        initSelect();
        imageSelectWechat.setImageResource(R.drawable.ic_check_pay_select);
        payWay = WECHAT_PAY;
        payName = WECHAT_PAY_NAME;
    }

    @OnClick(R.id.pay_act_layout_baiduWallet_pay)
    public void selectBaiduWalletPay() {
        initSelect();
        imageSelectBaiduWallet.setImageResource(R.drawable.ic_check_pay_select);
        payWay = BAIDUWALLET_PAY;
        payName = BAIDU_PAY_NAME;
        LogUtils.e("baidu");
    }

    @OnClick(R.id.pay_act_layout_JDWallet_pay)
    public void selectJDWalletPay() {
        initSelect();
        imageSelectJDWallet.setImageResource(R.drawable.ic_check_pay_select);
        payWay = JDWALLET_PAY;
        payName = JD_PAY_NAME;
    }

    @OnClick(R.id.pay_act_layout_quick_pay)
    public void selectQuickPay() {
        initSelect();
        imageSelectQuickPay.setImageResource(R.drawable.ic_check_pay_select);
        payWay = QUICK_PAY;
        payName = QUICK_PAY_NAME;
    }

    @OnClick(R.id.pay_act_layout_union_pay)
    public void selectUnionPay() {
        initSelect();
        imageSelectUnionWay.setImageResource(R.drawable.ic_check_pay_select);
        payWay = UNION_PAY;
        payName = UNION_PAY_NAME;
    }

    private void initSelect() {
        imageSelectAli.setImageResource(R.drawable.ic_check_pay_normal);
        imageSelectWechat.setImageResource(R.drawable.ic_check_pay_normal);
        imageSelectBaiduWallet.setImageResource(R.drawable.ic_check_pay_normal);
        imageSelectJDWallet.setImageResource(R.drawable.ic_check_pay_normal);
        imageSelectQuickPay.setImageResource(R.drawable.ic_check_pay_normal);
        imageSelectUnionWay.setImageResource(R.drawable.ic_check_pay_normal);
    }


    @OnClick(R.id.pay_act_btn_pay)
    public void pay() {
        this.dialog.show();
        new PayAPI(this, order.getId(), payName);
    }

    @Override
    public void apiPaySuccess(String change) {

        Log.e("Potato", "支付Charge:" + change);
        this.dialog.dismiss();
//        Intent intent = new Intent();
//        String packageName = getPackageName();
//        ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
//        intent.setComponent(componentName);
//        intent.putExtra(PaymentActivity.EXTRA_CHARGE, change);
//        startActivityForResult(intent, 1);

//        说明: 上述发起方式是 Ping++ client-sdk 唯一公开调用方式， “.wxapi.WXPayEntryActivity“ 是所有渠道支付的入口，并非只是微信支付入口。


        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PaymentActivity.EXTRA_CHARGE, change);
        startActivityForResult(intent, 1);


    }

    @Override
    public void apiPayFailure(long code, String msg) {
        this.dialog.dismiss();
        showTip(msg);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - payment succeed
             * "fail"    - payment failed
             * "cancel"  - user canceld
             * "invalid" - payment plugin not installed
             *
             * 如果是银联渠道返回 invalid，调用 UPPayAssistEx.installUPPayPlugin(this); 安装银联安全支付控件。
             */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
//                showMsg(result, errorMsg, extraMsg);


                TipUtils.toast("result:" + result + " " + errorMsg + " " + extraMsg);

                if ("success".equals(result)) {
                    OrderListAct.startActivity(this, OrderListAct.OrderStatus.WAIT_RECEIVE);
                    EventBus.getDefault().post(new UpdateUserEvent());
                } else {
                    OrderListAct.startActivity(this, OrderListAct.OrderStatus.WAIT_PAY);
                }
                finish();

            }
        }
    }


}
