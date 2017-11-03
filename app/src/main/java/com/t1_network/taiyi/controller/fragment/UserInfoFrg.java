package com.t1_network.taiyi.controller.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.t1_network.core.app.App;
import com.t1_network.core.controller.BasicFrg;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.activity.AddressAct;
import com.t1_network.taiyi.controller.activity.AfterSaleAct;
import com.t1_network.taiyi.controller.activity.CollectAct;
import com.t1_network.taiyi.controller.activity.CompareGoodAct;
import com.t1_network.taiyi.controller.activity.CustomerAct;
import com.t1_network.taiyi.controller.activity.FootPrintsAct;
import com.t1_network.taiyi.controller.activity.HealthRecordAct;
import com.t1_network.taiyi.controller.activity.LoginAndRegisterAct;
import com.t1_network.taiyi.controller.activity.MsgCenterAct;
import com.t1_network.taiyi.controller.activity.OrderAllAct;
import com.t1_network.taiyi.controller.activity.OrderListAct;
import com.t1_network.taiyi.controller.activity.SettingAct;
import com.t1_network.taiyi.controller.activity.SuggestAct;
import com.t1_network.taiyi.controller.activity.TokenAct;
import com.t1_network.taiyi.controller.activity.UserInfoAct;
import com.t1_network.taiyi.controller.activity.WebAct;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.model.event.CollectEvent;
import com.t1_network.taiyi.model.event.LoginEvent;
import com.t1_network.taiyi.model.event.LogoutEvent;
import com.t1_network.taiyi.model.event.UpdateUserEvent;
import com.t1_network.taiyi.model.event.UserInfoEvent;
import com.t1_network.taiyi.net.api.home.HomeAPI;

import butterknife.Bind;
import butterknife.OnClick;
import cn.walink.pulltorefresh.library.PullToRefreshBase;
import cn.walink.pulltorefresh.library.PullToRefreshScrollView;
import de.greenrobot.event.EventBus;

/**
 * 个人中心
 */
public class UserInfoFrg extends BasicFrg implements HomeAPI.HomeAPIListener, PullToRefreshBase.OnRefreshListener<ScrollView> {


    @Bind(R.id.scrollview)
    ScrollView scrollView;


    @Bind(R.id.pull_to_refresh_root)
    PullToRefreshScrollView layoutRefresh;

    @Bind(R.id.frg_user_info_text_login_or_register_ll_user)
    LinearLayout llUser;

    @Bind(R.id.frg_user_info_text_login_or_register_iv_user_icon)
    ImageView ivUserIcon;

    @Bind(R.id.frg_user_info_text_login_or_register_user_name)
    TextView tvUserName;


    public UserInfoFrg() {
        super(R.layout.frg_user_info);
    }


    @Override
    public void initView(View view) {

        layoutRefresh.setOnRefreshListener(this);
        if (App.getApp().isLogin()) {
            //如果已经登录
            User user = App.getApp().getUser();
            login(user);

            layoutRefresh.setRefreshing(true);
            onRefresh(layoutRefresh);
        } else {
            //未登录状态
            logOut();
        }
    }


    public void login(User user) {
        imageLoader.displayImage(user.getConsumer().getPhoto(), ivUserIcon);
        tvUserName.setText(user.getConsumer().getNickname());
        llUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoAct.startActivity(context);
            }
        });
    }

    public void logOut() {
        ivUserIcon.setImageResource(R.drawable.ic_user_image);
        tvUserName.setText(ResUtils.getString(R.string.frg_user_info_text_login_or_register));
        llUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAndRegisterAct.startActivity(context);
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 点击事件---------------------------------------------------------------------------------------------------------------------
     */

//    //点击 登陆/注册
//    @OnClick(R.id.frg_user_info_text_login_or_register_ll_user)
//    public void toLoginOrRegisterAct() {
//        if (!App.getApp().isLogin()) {
//            LoginAndRegisterAct.startActivity(context);
//        }
//    }
//
//    //点击头像.进去修改界面.
//    @OnClick(R.id.frg_user_info_text_login_or_register_ll_user)
//    public void toUpdateUserInfo(View view) {
//        UserInfoAct.startActivity(context);
//    }


    //    点击收藏
    @OnClick(R.id.frg_user_info_layout_collect)
    public void toCollectAct() {
        if (!App.getApp().isLogin()) {
            LoginAndRegisterAct.startActivity(context);
            return;
        }
        CollectAct.startActivity(context);
    }

    //点击代币
    @OnClick(R.id.frg_user_info_layout_token)
    public void toTokenAct() {
        if (!App.getApp().isLogin()) {
            LoginAndRegisterAct.startActivity(context);
            return;
        }
        TokenAct.startActivity(context);
    }

    //点击足迹
    @OnClick(R.id.frg_user_info_layout_footprints)
    public void toFootPrintsAct() {
        if (!App.getApp().isLogin()) {
            LoginAndRegisterAct.startActivity(context);
            return;
        }
        FootPrintsAct.startActivity(context);
    }

    //点击待支付订单
    @OnClick(R.id.frg_user_info_layout_wait_pay)
    public void toOrderWaitPay() {
        if (!App.getApp().isLogin()) {
            LoginAndRegisterAct.startActivity(context);
            return;
        }
        OrderListAct.startActivity(context, OrderListAct.OrderStatus.WAIT_PAY);
    }

    //点击待收货订单
    @OnClick(R.id.frg_user_info_layout_wait_receive)
    public void toOrderWaitReceive() {
        if (!App.getApp().isLogin()) {
            LoginAndRegisterAct.startActivity(context);
            return;
        }
        OrderListAct.startActivity(context, OrderListAct.OrderStatus.WAIT_RECEIVE);
    }

    //待评价订单
    @OnClick(R.id.frg_user_info_layout_wait_commit)
    public void toOrderWaitCommit() {
        if (!App.getApp().isLogin()) {
            LoginAndRegisterAct.startActivity(context);
            return;
        }
        OrderListAct.startActivity(context, OrderListAct.OrderStatus.WAIT_COMMENT);
    }

    //售后服务订单
    @OnClick(R.id.frg_user_info_layout_wait_return)
    public void toOrderWaitReturn() {
        if (!App.getApp().isLogin()) {
            LoginAndRegisterAct.startActivity(context);
            return;
        }
        AfterSaleAct.startActivity(context);
    }

    //    点击全部订单
    @OnClick(R.id.frg_user_info_text_my_all_order)
    public void toMyAllOrderAct() {
        if (!App.getApp().isLogin()) {
            LoginAndRegisterAct.startActivity(context);
            return;
        }
        OrderAllAct.startActivity(context);
    }

    //点击健康档案
    @OnClick(R.id.frg_user_info_text_my_health_record)
    public void toMyHealthRecordAct() {
        if (!App.getApp().isLogin()) {
            LoginAndRegisterAct.startActivity(context);
            return;
        }
        HealthRecordAct.startActivity(context);
    }

    //点击地址管理
    @OnClick(R.id.frg_user_info_text_address)
    public void toAddressAct() {

        if (!App.getApp().isLogin()) {
            LoginAndRegisterAct.startActivity(context);
            return;
        }

        AddressAct.startActivity(context);
    }

    //点击联系客服
    @OnClick(R.id.frg_user_info_text_customer)
    public void toCustomerAct() {
        CustomerAct.startActivity(context);
    }

    //点击意见反馈
    @OnClick(R.id.frg_user_info_text_suggest)
    public void toSuggestAct() {
        SuggestAct.startActivity(context);
    }

    //点击关于我们
    @OnClick(R.id.frg_user_info_text_about_us)
    public void toAboutUsAct() {
        WebAct.startActivity(context, "http://t1-network.com:3000/doc/about.html", "关于我们");

    }

    @OnClick(R.id.frg_user_info_text_compare)
    public void toCompareAct() {
        CompareGoodAct.startActivity(context);
    }

    //    点击设置
    @OnClick(R.id.frg_user_info_text_setting)
    public void toSettingAct() {
        SettingAct.startActivity(context);

//        TestAct.startActivity(context);
//        GoodDetailAct2.startActivity(context, "16000291");
    }


    //点击消息中心
    @OnClick(R.id.frg_user_info_image_msg)
    public void toMsgCenterAct() {
        if (!App.getApp().isLogin()) {
            LoginAndRegisterAct.startActivity(context);
            return;
        }
        MsgCenterAct.startActivity(context);
    }


    @Override
    public void apiHomeFailure(long code, String msg) {
        showTip(msg);
        layoutRefresh.onRefreshComplete();
    }

    @Override
    public void apiHomeSuccess(String waitPayNum, String waitReceiveNum, String waitCommentNum, String collectNum, String msgNum, String voucherNum) {
        textVoucher.setText(voucherNum);

        int integer = Integer.parseInt(collectNum);
        int count = integer -1;
        textCollect.setText(count+"");
//        textCollect.setText(collectNum);
        User user = App.getApp().getUser();
        login(user);

        setNum(textNumWaitPay, waitPayNum);
        setNum(textNumWaitReveive, waitReceiveNum);
        setNum(textNumWaitComment, waitCommentNum);

        layoutRefresh.onRefreshComplete();
    }


    private void setNum(TextView textView, String numStr) {

        if ("0".equals(numStr)) {
            textView.setVisibility(View.GONE);
        } else {

            long num = Long.parseLong(numStr);

            if (num > 9) {
                numStr = "9+";
            }

            textView.setText(numStr);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {

        if (App.getApp().isLogin()) {
            new HomeAPI(this);
        } else {
            layoutRefresh.onRefreshComplete();
        }
    }


    @Bind(R.id.frg_user_info_text_voucher)
    TextView textVoucher;

    @Bind(R.id.frg_user_info_text_collect)
    TextView textCollect;

    @Bind(R.id.frg_user_info_text_num_wait_pay)
    TextView textNumWaitPay;

    @Bind(R.id.frg_user_info_text_num_wait_receive)
    TextView textNumWaitReveive;

    @Bind(R.id.frg_user_info_text_num_wait_comment)
    TextView textNumWaitComment;

    @Bind(R.id.frg_user_info_text_num_return)
    TextView textNumWaitReturn;


    public void onEventMainThread(LogoutEvent event) {
        textCollect.setText("0");
        textVoucher.setText("0");
        textNumWaitPay.setVisibility(View.GONE);
        textNumWaitReveive.setVisibility(View.GONE);
        textNumWaitComment.setVisibility(View.GONE);
        textNumWaitReturn.setVisibility(View.GONE);
        logOut();
    }

    public void onEventMainThread(LoginEvent event) {
        User user = event.getUser();
        login(user);

        onRefresh(layoutRefresh);
    }

    public void onEventMainThread(UpdateUserEvent event) {
        layoutRefresh.setRefreshing(true);
        onRefresh(layoutRefresh);
    }

    public void onEventMainThread(CollectEvent event) {
        layoutRefresh.setRefreshing(true);
        onRefresh(layoutRefresh);
    }

    public void onEventMainThread(UserInfoEvent event) {
        layoutRefresh.setRefreshing(true);
        onRefresh(layoutRefresh);
    }

}
