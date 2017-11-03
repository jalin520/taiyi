package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.fragment.LoginFrg;
import com.t1_network.taiyi.controller.fragment.RegisterFrg;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.model.event.LoginEvent;
import com.t1_network.taiyi.net.api.user.LoginByOtherAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import de.greenrobot.event.EventBus;

public class LoginAndRegisterAct extends BasicAct implements PlatformActionListener, LoginByOtherAPI.LoginByOtherAPIListener {

    //创建一个集合.来保存Title名字
    private List<String> nameList;

    //创建一个集合.来保存Fragment
    private List<Fragment> loginFragmentList;

    //初始化TabLayout
    @Bind(R.id.login_act_tab_layout)
    TabLayout tabLayout;

    //初始化ViewPager
    @Bind(R.id.login_act_view_pager)
    ViewPager viewPager;

    public LoginAndRegisterAct() {
        super(R.layout.act_login_and_register, NO_TITLE, false, TOOLBAR_TYPE_NO_TOOLBAR);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginAndRegisterAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {

        //ShareSDK初始化
        ShareSDK.initSDK(this);

        //初始化title集合
        nameList = new ArrayList<String>();
        //给title集合添加数据
        nameList.add(ResUtils.getString(R.string.login));
        nameList.add(ResUtils.getString(R.string.register));
        tabLayout.addTab(tabLayout.newTab().setText(nameList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(nameList.get(1)));


        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //初始化Fragment集合
        loginFragmentList = new ArrayList<Fragment>();

        //给Fragment添加数据
        loginFragmentList.add(new LoginFrg());
        loginFragmentList.add(new RegisterFrg());
        //给Viewpager设置适配器,传入FragmentPagerAdapter,Fragment集合
        viewPager.setAdapter(new LoginPagerAdapter(getSupportFragmentManager(), loginFragmentList));
        //设置Viewpager变化的监听器,传入Viewpager的监听器
        viewPager.setOnPageChangeListener(new ViewPagerListener());


        //tabLayout和viewPager 关联起来.发生联动效果
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }

    //自定义.FragmentPagerAdapter
    public class LoginPagerAdapter extends FragmentPagerAdapter {
        //定义一个.集合
        private List<Fragment> fragmentList;

        //获取Viewpager的title
        @Override
        public CharSequence getPageTitle(int position) {
            return nameList.get(position);
        }

        //构造方法.用来接收外界穿过来的.fragment集合.
        public LoginPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        //处理每一个fragment
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        //处理fragment中的集合个数
        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    //Viewpager的监听管理器
    class ViewPagerListener implements ViewPager.OnPageChangeListener {
        //Viewpager滑动的时候触发的监听器
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        //Viewpager选中了.触发的监听器
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        //Viewpager选中了.触发的监听器
        @Override
        public void onPageSelected(int index) {
            viewPager.setCurrentItem(index);
        }
    }


    /**
     * 第三方登录----------------------------------------------------------------------------------------------------------
     */

    public final static int LOGIN_BY_WECHAT = 1;
    public final static int LOGIN_BY_SINA = 2;
    public final static int LOGIN_BY_QQ = 3;

    private String qqUserId = "";


    /**
     * 传入平台名称,进行第三方登录,并回调 PlatformActionListener 接口
     *
     * @param name
     */
    private void loginByOther(String name) {
        Platform platform = ShareSDK.getPlatform(name);
        platform.setPlatformActionListener(this);
        platform.SSOSetting(false);
        platform.showUser(null);//执行登录，登录后在回调里面获取用户资料
        qqUserId = platform.getDb().getUserId();
    }

    /**
     * 点击 微信登录
     */
    @OnClick(R.id.frg_login_by_shear_wechat)
    public void wechatLogin(View view) {
        loginByOther(Wechat.NAME);
    }

    /**
     * 点击 新浪登录
     */
    @OnClick(R.id.frg_login_by_shear_sina)
    public void sinaLogin(View view) {
        loginByOther(SinaWeibo.NAME);
    }


    /**
     * 点击 QQ登录
     */
    @OnClick(R.id.frg_login_by_shear_qq)
    public void qqLogin(View view) {
        loginByOther(QQ.NAME);
    }

    /**
     * 第三方登录的回调,三个方法都是在子线程中,需要用Handler去进行UI操作
     * <p/>
     * onComplete 回调成功
     * <p/>
     * onError 回调失败
     * <p/>
     * onCancel 回调取消
     */

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {


        Message msg = new Message();

        if (Wechat.NAME.equals(platform.getName())) {

            msg.what = LOGIN_BY_WECHAT;
        }

        if (SinaWeibo.NAME.equals(platform.getName())) {
            msg.what = LOGIN_BY_SINA;
        }

        if (QQ.NAME.equals(platform.getName())) {
            msg.what = LOGIN_BY_QQ;
        }

        msg.obj = hashMap;
        handler.sendMessage(msg);

        handlerComplete.sendEmptyMessage(1);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        handlerError.sendEmptyMessage(1);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        handlerCancel.sendEmptyMessage(1);
    }

    private Handler handlerComplete = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TipUtils.toast("onComplete");
        }
    };

    private Handler handlerError = new Handler() {
        @Override
        public void handleMessage(Message msg) {


            TipUtils.toast("onError");

        }
    };
    private Handler handlerCancel = new Handler() {
        @Override
        public void handleMessage(Message msg) {


            TipUtils.toast("onCancel");

        }
    };


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TipUtils.toast("onComplete");
            try {
                HashMap<String, Object> res = (HashMap<String, Object>) msg.obj;


                switch (msg.what) {

                    case LOGIN_BY_WECHAT:
                        fromWechat(res);
                        break;
                    case LOGIN_BY_SINA:
                        fromWeibo(res);
                        break;
                    case LOGIN_BY_QQ:
                        fromQQ(res);
                        break;
                }


            } catch (Exception e) {
                e.printStackTrace();
                showTip(e.getMessage());
            }
        }
    };


    private void fromQQ(HashMap<String, Object> res) {


        String openId = qqUserId;
        String name = res.get("nickname").toString();
        String sex = res.get("gender").toString();
        String avatar = res.get("figureurl_qq_2").toString();


        int sexInt = 1;
        if ("男".equals(sex)) {
            sexInt = 1;
        } else {
            sexInt = 2;
        }


        new LoginByOtherAPI(this, LOGIN_BY_QQ, sexInt, name, openId, avatar);
    }


    private void fromWechat(HashMap<String, Object> res) {


        String openId = res.get("openid").toString();
        String name = res.get("nickname").toString();
        String avatar = res.get("headimgurl").toString();
        String sex = res.get("sex").toString();

        int sexInt = 1;
        if ("男".equals(sex)) {
            sexInt = 1;
        } else {
            sexInt = 2;
        }

        new LoginByOtherAPI(this, LOGIN_BY_WECHAT, sexInt, name, openId, avatar);
    }

    public void fromWeibo(HashMap<String, Object> res) {

        try {
            LogUtils.e("" + (new JSONObject(new Gson().toJson(res)).toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String openId = res.get("idstr").toString();
        String name = res.get("name").toString();
        String avatar = res.get("avatar_hd").toString();
        String sex = res.get("gender").toString();

        int sexInt = 1;
        if ("m".equals(sex)) {
            sexInt = 1;
        } else {
            sexInt = 2;
        }

        new LoginByOtherAPI(this, LOGIN_BY_WECHAT, sexInt, name, openId, avatar);
    }


    @Override
    public void apiLoginByOtherSuccess(User user) {
        Toast.makeText(LoginAndRegisterAct.this, "fromWechat", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new LoginEvent(user));
        this.finish();
    }

    @Override
    public void apiLoginByOtherFailure(long code, String msg) {
        showTip(msg);
    }

    @OnClick(R.id.login_and_register_ll_back)
    public void finishAct() {
        this.finish();
    }
}
