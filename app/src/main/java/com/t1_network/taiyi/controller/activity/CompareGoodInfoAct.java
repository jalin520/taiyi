package com.t1_network.taiyi.controller.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.t1_network.core.app.App;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.AlertUtils;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.reveiver.UserCartReceiver;
import com.t1_network.taiyi.controller.reveiver.UserComparReceiver;
import com.t1_network.taiyi.db.TYSP;
import com.t1_network.taiyi.model.bean.cart.Cart;
import com.t1_network.taiyi.model.bean.good.CompareBean;
import com.t1_network.taiyi.model.bean.good.GoodDetail;
import com.t1_network.taiyi.model.bean.good.Product;
import com.t1_network.taiyi.model.event.UpdateCartEvent;
import com.t1_network.taiyi.net.api.good.CompareAPI;
import com.t1_network.taiyi.net.api.shopcart.AddCartAPI;
import com.t1_network.taiyi.net.api.shopcart.GetSelectCartAPI;
import com.t1_network.taiyi.utils.VerifyUtils;
import com.t1_network.taiyi.widget.ComparInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class CompareGoodInfoAct extends BasicAct implements CompareAPI.CompareAPIListener, UserComparReceiver.UserComparReceiverListener, AddCartAPI.AddCartAPIListener, GetSelectCartAPI.GetSelectCartAPIListener {

    private ArrayList<GoodDetail> mCompareList;


    @Bind(R.id.compar_good_left_tv_title)
    TextView tvLeftTitle;
    @Bind(R.id.compar_good_right_tv_title)
    TextView tvRightTitle;
    @Bind(R.id.compar_good_left_iv_icon)
    ImageView ivLeftIcon;
    @Bind(R.id.compar_good_right_iv_icon)
    ImageView ivRightIcon;
    @Bind(R.id.compar_good_left_tv_price)
    TextView tvLeftPrice;
    @Bind(R.id.compar_good_right_tv_price)
    TextView tvRightPrice;
    @Bind(R.id.compar_good_left_tv_add_cart)
    TextView tvLeftAddCart;
    @Bind(R.id.compar_good_right_tv_add_cart)
    TextView tvRightAddCart;
    @Bind(R.id.compar_good_right_ll_list)
    LinearLayout llList;
    @Bind(R.id.compar_good_compare_number)
    TextView tvCompareNumber;
    @Bind(R.id.compar_good_cart_number)
    TextView tvCartNumber;
    @Bind(R.id.compar_good_title_duibi)
    RelativeLayout rlCompare;//title对比按钮
    @Bind(R.id.compar_good_title_cart)
    RelativeLayout rlAddCart;

    private Cart cart;
    private UserComparReceiver mUserComparReceiver;

    public CompareGoodInfoAct() {
        super(R.layout.act_compare_good_info, R.string.title_activity_compare_good_info);
    }

    public static void startActivity(Context context, ArrayList<GoodDetail> mCompareList) {
        Intent intent = new Intent(context, CompareGoodInfoAct.class);
        intent.putParcelableArrayListExtra("CompareData", mCompareList);
        context.startActivity(intent);
    }

    Product product;

    @Override
    public void initView() {
        new GetSelectCartAPI(this);
        tvCompareNumber.setText(TYSP.getCompareGood().size() + "");
        Intent intent = getIntent();
        mCompareList = intent.getParcelableArrayListExtra("CompareData");

        product = mCompareList.get(0).getProduct();
        String srcID = product.getId();
        tvLeftTitle.setText(product.getName());
        if (VerifyUtils.hasImage(product)) {
            imageLoader.displayImage(product.getGoodImageList().get(0).getUrl(), ivLeftIcon);
        }

        tvLeftPrice.setText(ResUtils.getString(R.string.money, product.getPrice()));
        tvLeftAddCart.setOnClickListener(new OnClickAddCartListener(srcID));

        product = mCompareList.get(1).getProduct();
        String descID = product.getId();
        tvRightTitle.setText(product.getName());
        if (VerifyUtils.hasImage(product)) {
            imageLoader.displayImage(product.getGoodImageList().get(0).getUrl(), ivRightIcon);
        }
        tvRightPrice.setText(ResUtils.getString(R.string.money, product.getPrice()));
        tvRightAddCart.setOnClickListener(new OnClickAddCartListener(descID));

        initAPIData(srcID, descID);
        compareNumberReceiver();
        //等数据
    }

    private void compareNumberReceiver() {
        mUserComparReceiver = new UserComparReceiver(this);
        UserComparReceiver.register(this, mUserComparReceiver);
    }

    private void initAPIData(String srcID, String descID) {
        new CompareAPI(this, srcID, descID);
    }


    @Override
    public void apiCompareAPISuccess(CompareBean compareBean) {
        List<CompareBean.SpecEntity> spec = compareBean.getSpec();
        for (CompareBean.SpecEntity specEntity : spec) {
            ComparInfoView comparInfoView = new ComparInfoView(this);
            comparInfoView.setTitle(specEntity.getName());
            comparInfoView.setLeftName(specEntity.getSrc());
            LogUtils.e(specEntity.getSrc());
            comparInfoView.setRightName(specEntity.getDest());
            llList.addView(comparInfoView);
        }
    }

    @Override
    public void apiCompareAPIFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void receiverCompar() {
        tvCompareNumber.setText(TYSP.getCompareGood().size() + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserComparReceiver.unregister(this, mUserComparReceiver);
    }

    @OnClick(R.id.compar_good_title_duibi)
    public void onClickCompare() {
//        CompareGoodAct.startActivity(this);
        EventBus.getDefault().post(new UpdateCartEvent(cart));
        finish();
    }

    @OnClick(R.id.compar_good_title_cart)
    public void onClickAddCart() {
        CartAct.startActivity(this);
        finish();
    }


    @Override
    public void apiAddCartSuccess(Cart cart) {
        int size = cart.getGoodList().size();
        this.cart = cart;
        tvCartNumber.setText(size + "");
        UserCartReceiver.send(this, size + "");
        showTip("商品已加到购物车");
    }

    @Override
    public void apiAddCartFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiGetSelectCartSuccess(Cart cart) {
        tvCartNumber.setText(cart.getGoodList().size() + "");
        UserCartReceiver.send(this, cart.getGoodList().size() + "");
    }

    @Override
    public void apiGetSelectCartFailure(long code, String msg) {
        showTip(msg);
    }

    private Dialog mDialog;

    public class OnClickAddCartListener implements View.OnClickListener {

        private String id;


        public OnClickAddCartListener(String id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            if (App.getApp().getUser() == null) {
                mDialog = AlertUtils.comfirmDialog(CompareGoodInfoAct.this, R.string.c_alert_title,
                        R.string.c_alert_title_content, new OnClickLoginActListener(), true);
                return;
            }
            new AddCartAPI(CompareGoodInfoAct.this, id, "1");
        }
    }

    public class OnClickLoginActListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            LoginAndRegisterAct.startActivity(CompareGoodInfoAct.this);
            mDialog.dismiss();
        }
    }

}
