package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.t1_network.core.app.App;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.CacheUtils;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.UIUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.event.LogoutEvent;
import com.t1_network.taiyi.net.api.user.LogoutAPI;
import com.umeng.update.UmengUpdateAgent;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class SettingAct extends BasicAct implements LogoutAPI.LogoutAPIListener {

    @Bind(R.id.setting_clean_size)
    TextView textCacheSize;
    private String mCacheSize;

    private long mFolderSize;
    private Bundle mExtras;


    @Bind(R.id.act_setting_layout_logout)
    LinearLayout layoutLogout;

    public SettingAct() {
        super(R.layout.act_setting, R.string.title_activity_setting);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingAct.class);

        context.startActivity(intent);
    }

    @Override
    public void initView() {
        try {
            mFolderSize = CacheUtils.getFolderSize(this.getCacheDir());
            LogUtils.e(mFolderSize + "");
            if (mFolderSize < 24000) {
                mCacheSize = CacheUtils.getAllCacheSize(this);
                textCacheSize.setText(mCacheSize);
                textCacheSize.setTextColor(ResUtils.getColor(R.color.text_green));
            } else {
                mCacheSize = CacheUtils.getAllCacheSize(this);
                textCacheSize.setText(mCacheSize);
                textCacheSize.setTextColor(ResUtils.getColor(R.color.text_orange));
            }

        } catch (Exception e) {
        }


        initLogoutView();

    }

    @OnClick(R.id.setting_clean_rl_clean)
    public void cleanOnclick() {

//        Intent in = new Intent();
//        in.putExtra(CleanProgressAct.CACHE_SIZE, mCacheSize);
//        in.setClass(this, CleanProgressAct.class);
//        this.startActivityForResult(in, 2);
//        CleanProgress.startActivity(this,mCacheSize);


        try {
            long cacheSize = CacheUtils.getFolderSize(getCacheDir());
            String allCacheSize = CacheUtils.getAllCacheSize(this);

            dialog.show();

            UIUtils.postDelayed(new CleanCacheRunnable(), 2000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class CleanCacheRunnable implements Runnable {

        @Override
        public void run() {
            CacheUtils.cleanAllCache(SettingAct.this);
            textCacheSize.setText(CacheUtils.getAllCacheSize(SettingAct.this));
            showTip("缓存清理完成");
            dialog.dismiss();
        }
    }


    public String getData(Intent in) {
        mExtras = in.getExtras();
        if (mExtras != null) {
            return mExtras.getString("result");
        } else {
            return null;
        }
    }


    //获取返回值部分的代码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String show = getData(data);
                //让textView显示取出来的数据
                if (show != null) {
                    textCacheSize.setText(show);
                } else {
                    textCacheSize.setText(mCacheSize);
                }
            }
        }
    }


    @OnClick(R.id.act_setting_update_password)
    public void onClickUpdatePassword(View v) {
        UpdatePasswrodAct.startActivity(this);

    }

    @OnClick(R.id.act_setting_layout_logout)
    public void logout() {
        dialog.show();
        new LogoutAPI(this);
    }

    @Override
    public void apiLogoutFailure(long code, String msg) {
        showTip(msg);
        dialog.dismiss();
    }

    @Override
    public void apiLogoutSuccess() {
        EventBus.getDefault().post(new LogoutEvent());
        initLogoutView();
        dialog.dismiss();
        showTip("登出成功");
    }

    @OnClick(R.id.setting_use_policy)
    public void toProtocol() {
        WebAct.startActivity(this, "http://t1-network.com:3000/doc/protocol.html", "使用条款");
    }

    @OnClick(R.id.setting_delivery_send)
    public void toDelivery() {
        WebAct.startActivity(this, "http://t1-network.com:3000/doc/delivery_sent_.html", "发货配送");
    }

    @OnClick(R.id.setting_customer)
    public void toCustomer() {
        WebAct.startActivity(this, "http://t1-network.com:3000/doc/customer.html", "售后服务");
    }

    @OnClick(R.id.setting_update_version_rl)
    public void toUpdateVersion() {
        LogUtils.e("update");
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.forceUpdate(this);
    }


    private void initLogoutView() {
        if (App.getApp().isLogin()) {
            layoutLogout.setVisibility(View.VISIBLE);
        } else {
            layoutLogout.setVisibility(View.GONE);
        }
    }


}
