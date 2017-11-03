package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.SplashAdapter;
import com.t1_network.taiyi.db.TYSP;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 进行是否第一次使用App判断
 * 进行是否token登录的判断
 */
public class SplashAct2 extends BasicAct  {



    @Bind(R.id.c_banner_view_pager)
    ViewPager mPager;

    @Bind(R.id.c_banner_layout_point)
    LinearLayout llPoint;

    @Bind(R.id.splash_btn_go_play)
    LinearLayout llPlay;




    private List<Integer> mImageList;


    public SplashAct2() {
        super(R.layout.act_splash2, NO_TITLE, false, TOOLBAR_TYPE_FULL_SCREEN);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context,SplashAct2.class);
         context.startActivity(intent);
    }

    @Override
    public void initView() {
        initBanner();

        SplashAdapter adapter = new SplashAdapter(mImageList, llPoint, mPager, llPlay);

    }

    /**
     * init Banner
     */
    private void initBanner() {
        mImageList = new ArrayList<>();
        mImageList.add(R.drawable.ic_splash1);
        mImageList.add(R.drawable.ic_splash2);
        mImageList.add(R.drawable.ic_splash3);

    }



    @OnClick(R.id.splash_btn_go_play)
    public void onClickHome(View v) {
        TYSP.setNotFirstUse();
        MainAct.startActivity(SplashAct2.this);
        finish();
    }
}
