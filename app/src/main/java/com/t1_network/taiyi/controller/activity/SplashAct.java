package com.t1_network.taiyi.controller.activity;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.t1_network.core.app.App;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.db.TYSP;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.net.api.user.TokenLoginAPI;

import butterknife.Bind;

/**
 * 进行是否第一次使用App判断
 * 进行是否token登录的判断
 */
public class SplashAct extends BasicAct implements TokenLoginAPI.TokenLoginAPIListener {

    @Bind(R.id.splash_root)
    RelativeLayout rvHome;


    public SplashAct() {
        super(R.layout.act_splash, NO_TITLE, false, TOOLBAR_TYPE_FULL_SCREEN);
    }

    @Override
    public void initView() {



        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1f);
        alphaAnimation.setDuration(2500);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                new TokenLoginAPI(SplashAct.this);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                    Thread.sleep(800);
                    if (TYSP.isFirstUse()) {
                        //跳转到引导页
                        SplashAct2.startActivity(SplashAct.this);
                        finish();
                    } else {
                        MainAct.startActivity(SplashAct.this);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rvHome.startAnimation(alphaAnimation);

    }


    @Override
    public void apiTokenLoginSuccess(User user) {
        App.getApp().setUser(user);


    }

    @Override
    public void apiTokenLoginFailure(long code, String msg) {
//        showTip(msg);

    }


}
