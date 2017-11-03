package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.t1_network.core.app.App;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.db.TYSP;
import com.t1_network.taiyi.model.event.CollectEvent;
import com.t1_network.taiyi.model.event.LoginEvent;
import com.t1_network.taiyi.net.api.good.CollectAPI;
import com.t1_network.taiyi.net.api.good.IsCollectAPI;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class GoodDetailAct2 extends BasicAct implements IsCollectAPI.IsCollectAPIListener, CollectAPI.CollectAPIListener {

    /**
     * 商品id
     */
    private String goodId;


    public GoodDetailAct2() {
        super(R.layout.activity_good_detail_act2, NO_TITLE);
    }

    private static final String P_GOOD_ID = "P_GOOD_ID";

    public static void startActivity(Context context, String goodId) {
        Intent intent = new Intent(context, GoodDetailAct2.class);
        intent.putExtra(P_GOOD_ID, goodId);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        goodId = intent.getStringExtra(P_GOOD_ID);

        initCompareCount(); // init 对比 数量
        initIsCollect(); //init 是否收藏


        EventBus.getDefault().register(this);
    }


    /**
     * 对比模块 begin--------------------------------------------------------------------------------------
     */

    /**
     * 对比数量 textView
     */
    @Bind(R.id.act_good_detail_add_compare_number)
    TextView textCompareCount;

    /**
     * init 对比数量
     */
    private void initCompareCount() {
        textCompareCount.setText(TYSP.getCompareGood().size() + "");
    }

    /**
     * 对比模块 end--------------------------------------------------------------------------------------
     */


    /**
     * 收藏模块 begin--------------------------------------------------------------------------------------
     */


    /**
     * 是否收藏
     */
    @Bind(R.id.act_good_detail_image_is_collect)
    ImageView imageIsCollect; //是否收藏图片

    /**
     * init 是否收藏
     */
    private void initIsCollect() {
        new IsCollectAPI(this, goodId);
    }

    private boolean isCollect = false;

    @OnClick(R.id.act_good_detail_image_is_collect)
    public void collect() {

        if (!App.getApp().isLogin()) {
            LoginAndRegisterAct.startActivity(this);
            return;
        }
        new CollectAPI(this, goodId);
    }

    @Override
    public void apiIsCollectFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiIsCollectSuccess(boolean isCollect) {
        this.isCollect = isCollect;
        if (isCollect) {
            imageIsCollect.setImageResource(R.drawable.ic_favourite_selected);
        } else {
            imageIsCollect.setImageResource(R.drawable.ic_favourite_normal);
        }
        EventBus.getDefault().post(new CollectEvent());
    }

    @Override
    public void apiCollectFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiCollectSuccess() {
        isCollect = !isCollect;
        apiIsCollectSuccess(isCollect);
    }

    /**
     * 收藏模块 end--------------------------------------------------------------------------------------
     */


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 登录事件
     *
     * @param event
     */
    public void onEventMainThread(LoginEvent event) {

    }
}
