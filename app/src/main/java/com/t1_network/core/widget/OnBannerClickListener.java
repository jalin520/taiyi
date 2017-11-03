package com.t1_network.core.widget;

import android.content.Context;
import android.view.View;

import com.t1_network.taiyi.controller.activity.WebAct;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/27 10:18
 * 修改记录:
 */
public class OnBannerClickListener implements View.OnClickListener {


    private BannerEntity bannerEntity;

    private Context context;

    public OnBannerClickListener(BannerEntity bannerEntity, Context context) {
        this.bannerEntity = bannerEntity;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        WebAct.startActivity(context, bannerEntity.getBannerURL(), bannerEntity.getBannerTitle());
    }


}
