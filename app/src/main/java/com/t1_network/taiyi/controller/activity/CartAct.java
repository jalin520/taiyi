package com.t1_network.taiyi.controller.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.t1_network.core.app.App;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.AlertUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.SpanStringUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.reveiver.CommitOrderReveiver;
import com.t1_network.taiyi.model.bean.ShopCartNormal;
import com.t1_network.taiyi.model.bean.cart.Cart;
import com.t1_network.taiyi.model.bean.cart.GoodInCart;
import com.t1_network.taiyi.model.bean.good.Good;
import com.t1_network.taiyi.model.event.LoginEvent;
import com.t1_network.taiyi.model.event.LogoutEvent;
import com.t1_network.taiyi.model.event.UpdateCartEvent;
import com.t1_network.taiyi.model.factory.ImageOptionFactory;
import com.t1_network.taiyi.net.api.good.SupposeYouWantAPI;
import com.t1_network.taiyi.net.api.shopcart.DelShopCartAPI;
import com.t1_network.taiyi.net.api.shopcart.GetShopCartAPI;
import com.t1_network.taiyi.net.api.shopcart.SelectGoodAPI;
import com.t1_network.taiyi.net.api.shopcart.UpdateShopCartAPI;
import com.t1_network.taiyi.widget.SupposeYouWantView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.walink.pulltorefresh.library.PullToRefreshBase;
import cn.walink.pulltorefresh.library.PullToRefreshScrollView;
import de.greenrobot.event.EventBus;

public class CartAct extends BasicAct implements PullToRefreshBase.OnRefreshListener, CommitOrderReveiver.CommitOrderListener,
        GetShopCartAPI.GetShopCartListener, UpdateShopCartAPI.UpdateShopCartAPIListener, DelShopCartAPI.DelShopCartAPIListener, SelectGoodAPI.SelectGoodAPIListener, SupposeYouWantAPI.SupposeYouWantAPIListener {

    public CartAct() {
        super(R.layout.act_cart, R.string.title_activity_cart);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CartAct.class);
        context.startActivity(intent);
    }


    @Bind(R.id.pull_to_refresh_root)
    PullToRefreshScrollView layoutRefresh; //下拉刷新通用组建
    ScrollView scrollView;

    @Bind(R.id.frg_shop_cart_layout_good_normal)
    LinearLayout layoutGoodNormal;//购物车列表

    @Bind(R.id.frg_shop_cart_layout_good_out_time)
    LinearLayout layoutOutTime; //无效商品的layout

    @Bind(R.id.frg_shop_cart_layout_no_good_tip)
    LinearLayout layoutNoGood;  //没有商品的提示

    @Bind(R.id.frg_shop_cart_layout_count)
    RelativeLayout layoutCount; //购物车下方结算的RelativeLayout,当购物车为空时,此layout不显示

    @Bind(R.id.frg_shop_cart_layout_btn_submit)
    Button btnSubmit; //结算按钮

    @Bind(R.id.frg_shop_cart_text_total_tip)
    TextView textTotal; //选中商品的金额总计

    @Bind(R.id.frg_shop_cart_image_select)
    ImageView imageSelectAll; //全选按钮

    private Cart cart;

    private SelectAllOnClickListener listener; //点击全选的监听


    @Override
    public void initView() {

        initRefresh();
        listener = new SelectAllOnClickListener(null, this);
        imageSelectAll.setOnClickListener(listener);


    }

    /**
     * 初始化下拉刷新
     */
    private void initRefresh() {
        scrollView = layoutRefresh.getRefreshableView();
        layoutRefresh.setOnRefreshListener(this);
        layoutRefresh.setRefreshing(true);
        onRefresh(layoutRefresh);
    }


    /**
     * 下拉刷新的回调
     *
     * @param refreshView
     */
    @Override
    public void onRefresh(PullToRefreshBase refreshView) {

        if (App.getApp().isLogin()) {
            cart = null;
            layoutGoodNormal.setVisibility(View.GONE);//
            new GetShopCartAPI(this);

        } else {
            //没有商品
            Cart cart = new Cart();
            cart.setGoodList(new ArrayList<GoodInCart>());
            apiGetShopCartSuccess(cart);
            layoutRefresh.onRefreshComplete(); //下拉刷新结束
        }

        new SupposeYouWantAPI(this);

    }


    /**
     * 接口:购物车-查看购物车
     *
     * @param cart
     */
    @Override
    public void apiGetShopCartSuccess(Cart cart) {

        this.cart = cart;

        List<GoodInCart> goodList = cart.getGoodList();

        //如果购物车为空,隐藏结算的Layout
        if (goodList.isEmpty()) {
            layoutCount.setVisibility(View.GONE);
            layoutNoGood.setVisibility(View.VISIBLE);
            hideSubmitBar();
            layoutRefresh.onRefreshComplete(); //下拉刷新结束
            return;
        } else {
            layoutCount.setVisibility(View.VISIBLE);
            layoutNoGood.setVisibility(View.GONE);
        }

        initGoodInCart(goodList);//显示购物车列表

        initGlobal(cart);//更新页面相关信息

        layoutRefresh.onRefreshComplete(); //下拉刷新结束
    }

    @Override
    public void apiGetShopCartFailure(long code, String msg) {
        showTip(msg);
        layoutRefresh.onRefreshComplete();
    }


    private void showSubmitBar() {
        RelativeLayout.LayoutParams pl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        pl.setMargins(0, 0, 0, (int) ResUtils.getDimen(R.dimen.frg_shop_cart_layout_refresh_margin_bottom));
        layoutRefresh.setLayoutParams(pl);
    }

    private void hideSubmitBar() {
        RelativeLayout.LayoutParams pl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        pl.setMargins(0, 0, 0, 0);
        layoutRefresh.setLayoutParams(pl);
    }

    /**
     * 显示购物车的商品列表
     *
     * @param goodList
     */
    private void initGoodInCart(List<GoodInCart> goodList) {
        //清空原有的数据
        layoutGoodNormal.setVisibility(View.VISIBLE);
        layoutGoodNormal.removeAllViews();

        for (int i = 0; i < goodList.size(); i++) {

            GoodInCart good = goodList.get(i);

            View view = View.inflate(this, R.layout.item_shop_cart_good_normal, null);

            ImageView imageSelect = (ImageView) view.findViewById(R.id.item_shop_cart_good_normal_image_select);
            ImageView imageGood = (ImageView) view.findViewById(R.id.item_shop_cart_good_normal_image_good);
            TextView textName = (TextView) view.findViewById(R.id.item_shop_cart_good_normal_text_name);
            TextView textMarketPrice = (TextView) view.findViewById(R.id.item_shop_cart_good_normal_text_market_price);
            TextView textPrice = (TextView) view.findViewById(R.id.item_shop_cart_good_normal_text_price);
            ImageView imageAdd = (ImageView) view.findViewById(R.id.c_num_picker_image_add);
            ImageView imageDel = (ImageView) view.findViewById(R.id.c_num_picker_image_del);
            TextView textNum = (TextView) view.findViewById(R.id.c_num_picker_text_num);

            textName.setText(good.getName());
            textMarketPrice.setText(ResUtils.getString(R.string.frg_shop_cart_text_money) + good.getMarketPrice());
            textMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            textPrice.setText(ResUtils.getString(R.string.frg_shop_cart_text_money) + good.getPrice());
            textNum.setText(good.getCount());

            if (good.isSelect()) {
                imageSelect.setImageResource(R.drawable.ic_select_selected);
            } else {
                imageSelect.setImageResource(R.drawable.ic_select_normal);
            }

            if (good.getImageList() != null && good.getImageList().size() != 0) {
                imageLoader.displayImage(good.getImageList().get(0).getUrl(), imageGood, ImageOptionFactory.getGoodOptions());
            }
            layoutGoodNormal.addView(view);

            imageSelect.setOnClickListener(new ImageSelectOnClickListener(good, imageSelect, this));
            imageAdd.setOnClickListener(new AddListener(good, textNum, this));
            imageDel.setOnClickListener(new DelListener(good, textNum, this));
            view.setOnLongClickListener(new DeleteShopCartListener(good.getId(), this));

        }
    }

    /**
     * 更新页面参数
     *
     * @param cart
     */
    private void initGlobal(Cart cart) {
        listener.setCart(cart);

        if (hasSelect(cart.getGoodList())) {
            layoutCount.setVisibility(View.VISIBLE);
            showSubmitBar();
        } else {
            layoutCount.setVisibility(View.GONE);
            hideSubmitBar();
        }

        initSelectAll(cart.getGoodList());
        initSelectNum(cart.getAllSelectCount());
        initTotalMoney(cart.getAllSelectMoney());
    }

    /**
     * 是否有选中商品
     *
     * @param goodList
     * @return
     */
    private boolean hasSelect(List<GoodInCart> goodList) {

        if (goodList == null || goodList.isEmpty()) {
            return false;
        }

        for (int i = 0; i < goodList.size(); i++) {
            if (goodList.get(i).isSelect()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 进行是否全选的设置
     */
    private void initSelectAll(List<GoodInCart> goodList) {

        boolean selectAll = true;
        for (int i = 0; i < goodList.size(); i++) {
            if (!goodList.get(i).isSelect()) {
                selectAll = false;
            }
        }

        if (selectAll) {
            imageSelectAll.setImageResource(R.drawable.ic_select_selected);
        } else {
            imageSelectAll.setImageResource(R.drawable.ic_select_normal);
        }
    }


    private class SelectAllOnClickListener implements View.OnClickListener {

        private Cart cart;
        private SelectGoodAPI.SelectGoodAPIListener listener;

        public SelectAllOnClickListener(Cart cart, SelectGoodAPI.SelectGoodAPIListener listener) {
            this.cart = cart;
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {

            if (cart == null) {
                return;
            }

            boolean selectAll = true;
            for (int i = 0; i < cart.getGoodList().size(); i++) {
                if (!cart.getGoodList().get(i).isSelect()) {
                    selectAll = false;
                }
            }
            dialog.show();
            new SelectGoodAPI(listener, cart.getGoodList(), !selectAll);
        }

        public void setCart(Cart cart) {
            this.cart = cart;
        }

        public Cart getCart() {
            return cart;
        }

    }

    /**
     * 选中商品的监听
     */
    private class ImageSelectOnClickListener implements View.OnClickListener {
        private GoodInCart good;
        private ImageView imageSelect;

        private SelectGoodAPI.SelectGoodAPIListener listener;

        public ImageSelectOnClickListener(GoodInCart good, ImageView imageSelect, SelectGoodAPI.SelectGoodAPIListener listener) {
            this.good = good;
            this.imageSelect = imageSelect;
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            dialog.show();
            new SelectGoodAPI(listener, good.getId(), !good.isSelect());
        }
    }


    /**
     * 初始化结算商品数量
     */
    private void initSelectNum(String allSelectCount) {
        String goodNum = ResUtils.getString(R.string.frg_shop_cart_layout_btn_submit, allSelectCount);
        btnSubmit.setText(goodNum);
    }

    /**
     * 初始化结算金额
     */
    private void initTotalMoney(String allSelectMoney) {
        String totalMoney = ResUtils.getString(R.string.frg_shop_cart_text_total_tip, allSelectMoney);
        textTotal.setText(totalMoney);

        CharSequence total = SpanStringUtils.getHighLightText(ResUtils.getColor(R.color.common_gray_deep), totalMoney, ResUtils.getString(R.string.frg_shop_cart_text_total_tip_span));
        textTotal.setText(total);
    }

    /**
     * 点击增加商品按钮
     */
    public class AddListener implements View.OnClickListener {
        private GoodInCart good;
        private TextView textNum;
        private UpdateShopCartAPI.UpdateShopCartAPIListener listener;

        public AddListener(GoodInCart good, TextView textNum, UpdateShopCartAPI.UpdateShopCartAPIListener listener) {
            this.good = good;
            this.textNum = textNum;
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            String strNum = textNum.getText().toString();

            int num = Integer.parseInt(strNum);

            if (num < 99) {
                num++;
                textNum.setText(num + "");
                good.setCount(num + "");
                dialog.show();
                new UpdateShopCartAPI(listener, good.getId(), good.getCount());
            }
        }
    }

    /**
     * 点击减少商品按钮
     */
    public class DelListener implements View.OnClickListener {
        private GoodInCart good;
        private TextView textNum;
        private UpdateShopCartAPI.UpdateShopCartAPIListener listener;


        public DelListener(GoodInCart good, TextView textNum, UpdateShopCartAPI.UpdateShopCartAPIListener listener) {
            this.good = good;
            this.textNum = textNum;
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {

            String strNum = textNum.getText().toString();

            int num = Integer.parseInt(strNum);

            if (num >= 2) {
                num--;
                textNum.setText(num + "");
                good.setCount(num + "");
                dialog.show();
                new UpdateShopCartAPI(listener, good.getId(), good.getCount());
            }
        }
    }


    private Dialog alertDialog;

    /**
     * 长按删除监听
     */
    private class DeleteShopCartListener implements View.OnLongClickListener {

        private String cartId;
        private DelShopCartAPI.DelShopCartAPIListener listener;

        public DeleteShopCartListener(String cartId, DelShopCartAPI.DelShopCartAPIListener listener) {
            this.cartId = cartId;
            this.listener = listener;
        }

        @Override
        public boolean onLongClick(View v) {
            alertDialog = AlertUtils.comfirmDialog(CartAct.this, R.string.c_alert_is_remove_good, new OnAlertRemoveGood(cartId), true);
            return false;
        }
    }

    public class OnAlertRemoveGood implements View.OnClickListener {

        private String cartId;

        public OnAlertRemoveGood(String cartId) {
            this.cartId = cartId;
        }

        @Override
        public void onClick(View v) {
            alertDialog.dismiss();
            dialog.show();
            new DelShopCartAPI(CartAct.this, cartId);
        }
    }


    /**
     * 点击结算按钮
     */
    @OnClick(R.id.frg_shop_cart_layout_btn_submit)
    public void submit() {

        List<GoodInCart> goodInCartList = new ArrayList<GoodInCart>();
        for (int i = 0; i < listener.getCart().getGoodList().size(); i++) {
            GoodInCart goodInCart = listener.getCart().getGoodList().get(i);
            if (goodInCart.isSelect()) {
                goodInCartList.add(goodInCart);
            }
        }

        OrderComfirmAct.startActivity(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    /**
     * 提交订单的监听-----------------------------------begin
     */


    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void receiveCommitOrder() {
        layoutRefresh.setRefreshing(true);
        onRefresh(layoutRefresh);
    }

    /**
     * 提交订单的监听-----------------------------------end
     */


    /**
     * API-购物车-更新商品数量
     *
     * @param cart
     */
    @Override
    public void apiUpdateShopCartSuccess(Cart cart) {
        this.cart = cart;

        List<GoodInCart> goodList = cart.getGoodList();

        //如果购物车为空,隐藏结算的Layout
        if (goodList.isEmpty()) {
            layoutCount.setVisibility(View.GONE);
            layoutNoGood.setVisibility(View.VISIBLE);
            hideSubmitBar();
            layoutRefresh.onRefreshComplete(); //下拉刷新结束
            return;
        } else {
            layoutCount.setVisibility(View.VISIBLE);
            layoutNoGood.setVisibility(View.GONE);
        }

        initGoodInCart(goodList);//显示购物车列表

        layoutRefresh.onRefreshComplete();
        initGlobal(cart);
        dialog.dismiss();
    }

    /**
     * API-购物车-更新商品数量失败
     *
     * @param code
     * @param msg
     */
    @Override
    public void apiUpdateShopCartFailure(long code, String msg) {
        showTip(msg);
        layoutRefresh.onRefreshComplete();
        layoutNoGood.setVisibility(View.VISIBLE);
        dialog.dismiss();
    }


    /**
     * API-选择商品成功
     *
     * @param cart
     */
    @Override
    public void apiSelectGoodSuccess(Cart cart) {

        this.cart = cart;

        List<GoodInCart> goodList = cart.getGoodList();

        //如果购物车为空,隐藏结算的Layout
        if (goodList.isEmpty()) {
            layoutCount.setVisibility(View.GONE);
            layoutNoGood.setVisibility(View.VISIBLE);
            hideSubmitBar();
            return;
        } else {
            layoutCount.setVisibility(View.VISIBLE);
            layoutNoGood.setVisibility(View.GONE);
        }

        initGoodInCart(goodList);//显示购物车列表
        initGlobal(cart);
        dialog.dismiss();
    }

    /**
     * API-选择商品失败
     *
     * @param code
     * @param msg
     */
    @Override
    public void apiSelectGoodFailure(long code, String msg) {
        showTip(msg);
        dialog.dismiss();
    }


    /**
     * API-删除商品成功
     *
     * @param shopCartNormalList
     */
    @Override
    public void apiDelShopCartSuccess(List<ShopCartNormal> shopCartNormalList) {
        initRefresh();
        dialog.dismiss();
        layoutRefresh.setRefreshing(true);
        onRefresh(layoutRefresh);
        EventBus.getDefault().post(new UpdateCartEvent(cart));
    }

    /**
     * API-删除商品失败
     *
     * @param code
     * @param msg
     */
    @Override
    public void apiDelShopCartFailure(long code, String msg) {
        showTip(msg);
        dialog.dismiss();
    }


    @Bind(R.id.frg_shop_cart_suppose_you_want)
    SupposeYouWantView supposeYouWantView;

    @Override
    public void apiSupposeYouWantFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiSupposeYouWantSuccess(List<Good> goodList) {
        supposeYouWantView.setData(goodList);
    }

    public void onEventMainThread(UpdateCartEvent event) {

        if (event.getCart() == null) {
            onRefresh(layoutRefresh);
        } else {
            apiGetShopCartSuccess(event.getCart());
        }
    }

    public void onEventMainThread(LoginEvent event) {
        onRefresh(layoutRefresh);
    }

    public void onEventMainThread(LogoutEvent event) {
        layoutGoodNormal.removeAllViews();
        onRefresh(layoutRefresh);
    }


}
