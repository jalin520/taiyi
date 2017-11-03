package com.t1_network.taiyi.controller.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.AlertUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.CompareGoodAdapter;
import com.t1_network.taiyi.controller.reveiver.UserCartReceiver;
import com.t1_network.taiyi.controller.reveiver.UserComparReceiver;
import com.t1_network.taiyi.db.TYSP;
import com.t1_network.taiyi.model.bean.cart.Cart;
import com.t1_network.taiyi.model.bean.good.GoodDetail;
import com.t1_network.taiyi.model.event.UpdateCartEvent;
import com.t1_network.taiyi.model.event.UpdateCompareEvent;
import com.t1_network.taiyi.net.api.shopcart.GetShopCartAPI;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class CompareGoodAct extends BasicAct implements AdapterView.OnItemLongClickListener, GetShopCartAPI.GetShopCartListener, UserCartReceiver.UserComparReceiverListener {


    @Bind(R.id.compar_good_rv)
    RecyclerView comparRv;


    private List<GoodDetail> mDatas;

    @Bind(R.id.compar_good_commit_btn)
    TextView mTv;
    @Bind(R.id.compar_good_cart_number)
    TextView tvCartNumber;


    private CompareGoodAdapter mAdapter;
    private Dialog mDialog;
    private UserCartReceiver mUserCartReceiver;


    public CompareGoodAct() {
        super(R.layout.act_compare_good, NO_TITLE, false, TOOLBAR_TYPE_NO_TOOLBAR);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CompareGoodAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {


        //添加数据
        mDatas = TYSP.getCompareGood();
        if (mDatas != null)
            initialRecycler(mDatas);


        EventBus.getDefault().register(this);
        new GetShopCartAPI(this);
    }

    private void initialRecycler(List<GoodDetail> datas) {
        comparRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new CompareGoodAdapter(datas, mTv);
        comparRv.setAdapter(mAdapter);
        mAdapter.setOnItemLongClickListener(this);
    }

    @OnClick(R.id.compar_good_title_cart)
    public void intentCart(View v) {
        CartAct.startActivity(this);
    }

    @OnClick(R.id.tool_bar_btn_back)
    public void finishAct() {
        finish();
    }

    @OnClick(R.id.compar_good_commit_btn)
    public void comparCommit() {


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final GoodDetail goodDetail = mDatas.get(position);

        mDialog = AlertUtils.comfirmDialog(this, R.string.c_alert_title, ResUtils.getString(R.string.c_alert_is_compar_content), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.getCompareList().contains(goodDetail)) {
                    //对比按钮为.normal
                    mTv.setBackgroundResource(R.drawable.btn_in_compar_full_normal);
                    mAdapter.getCompareList().remove(goodDetail);
                }
                mDialog.dismiss();
                mDatas.remove(goodDetail);
                TYSP.saveCompareGood(mDatas);
                mAdapter.getData().remove(goodDetail);
                mAdapter.notifyDataSetChanged();
                UserComparReceiver.send(CompareGoodAct.this);
                EventBus.getDefault().post(new UpdateCompareEvent());
            }
        }, true);

        return true;
    }


    @Override
    public void apiGetShopCartFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiGetShopCartSuccess(Cart cart) {
        EventBus.getDefault().post(new UpdateCartEvent(cart));
    }

    @Override
    public void receiverCompar() {
//        String size = this.getIntent().getStringExtra("SIZE");
        tvCartNumber.setText(UserCartReceiver.size);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void onEventMainThread(UpdateCartEvent event) {

        Cart cart = event.getCart();

        if (cart == null) {
            new GetShopCartAPI(this);
            return;
        }
        tvCartNumber.setText(cart.getGoodList().size() + "");
    }



}
