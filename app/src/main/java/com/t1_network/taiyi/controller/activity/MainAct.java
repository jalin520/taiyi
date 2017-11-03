package com.t1_network.taiyi.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.blueware.agent.android.BlueWare;
import com.blueware.agent.android.util.ContextConfig;
import com.oneapm.agent.android.OneApmAgent;
import com.t1_network.core.app.App;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.fragment.ClassficationFrg;
import com.t1_network.taiyi.controller.fragment.HomeFrg;
import com.t1_network.taiyi.controller.fragment.ShopCartFrg;
import com.t1_network.taiyi.controller.fragment.UserInfoFrg;
import com.t1_network.taiyi.model.bean.User;
import com.umeng.update.UmengUpdateAgent;

import java.util.HashMap;

import butterknife.Bind;

public class MainAct extends BasicAct {


    private Class[] tabFragmentArray = {HomeFrg.class, ClassficationFrg.class, ShopCartFrg.class, UserInfoFrg.class};
    private int[] tabStringArray = {R.string.frg_home_title, R.string.frg_classfication_title, R.string.frg_shop_cart_title, R.string.frg_user_info_title};
    private int[] tabImageNormalArray = {R.drawable.ic_tab_home_normal, R.drawable.ic_tab_classfication_normal, R.drawable.ic_tab_shoppingcart_normal, R.drawable.ic_tab_userinfo_normal};
    private int[] tabImageSelectedArray = {R.drawable.ic_tab_home_selected, R.drawable.ic_tab_classfication_selected, R.drawable.ic_tab_shoppingcart_selected, R.drawable.ic_tab_userinfo_selected};


    public MainAct() {
        super(R.layout.act_main, NO_TITLE, false, TOOLBAR_TYPE_NO_TOOLBAR);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainAct.class);
        context.startActivity(intent);
    }

    @Bind(R.id.act_main_tab_host)
    FragmentTabHost fragmentTabHost;

    @Bind(R.id.act_main_view_pager)
    ViewPager viewPager;

    private LayoutInflater layoutInflater;

    private Fragment[] fragments;

    @Override
    public void initView() {

        if (App.getApp().isLogin()){
            //OneApm start
            User user = App.getApp().getUser();
            HashMap<String, String> extraData = new HashMap<String, String>();
            extraData.put("电话", user.getConsumer().getPhone());
            extraData.put("ID", user.getConsumer().getId());
            extraData.put("邮箱", user.getConsumer().getEmail());
            ContextConfig contextConfig = new ContextConfig();
            contextConfig.setExtra(extraData);
            contextConfig.setSearchField("电话");

            BlueWare.withContextConfig(contextConfig);
            OneApmAgent.init(this).setToken("C1EE0839075016DAD0996786B003C00606").start();
            //OneApm end
        }else{
            //OneApm start
            HashMap<String, String> extraData = new HashMap<String, String>();
            extraData.put("电话", "15818952200");
            extraData.put("ID", "123456");
            extraData.put("邮箱", "289069103@qq.com");
            ContextConfig contextConfig = new ContextConfig();
            contextConfig.setExtra(extraData);
            contextConfig.setSearchField("电话");

            BlueWare.withContextConfig(contextConfig);
            OneApmAgent.init(this).setToken("C1EE0839075016DAD0996786B003C00606").start();
            //OneApm end
        }



        UmengUpdateAgent.update(this);

        layoutInflater = LayoutInflater.from(this);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.act_main_view_pager);

        int count = tabFragmentArray.length;

        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec;

            if (i == 0) {
                //判断是否第一个 是  让图标变成选中状态...
                tabSpec = fragmentTabHost.newTabSpec(getString(tabStringArray[i])).setIndicator(getTabItemView(tabImageSelectedArray[i], tabStringArray[i]));
            } else {
                //不是.图标 返回Normal
                tabSpec = fragmentTabHost.newTabSpec(getString(tabStringArray[i])).setIndicator(getTabItemView(tabImageNormalArray[i], tabStringArray[i]));
            }

            //给tabSpec'添加.一个新的Fragment
            fragmentTabHost.addTab(tabSpec, tabFragmentArray[i], null);
//          fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);

            //去除.分割线
            fragmentTabHost.getTabWidget().setDividerDrawable(null);

            //给每个FragmentTabHost.添加点击事件....
            fragmentTabHost.getTabWidget().getChildAt(i).setOnClickListener(new TabClickListener(i, fragmentTabHost));
        }

        //当点击Tab的时候，调用ViewPager切换
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                // TODO Auto-generated method stub
                int position = fragmentTabHost.getCurrentTab();
                viewPager.setCurrentItem(position);
            }
        });


        //将4个Fragment放进

        HomeFrg homeTabFrg = new HomeFrg();
        ClassficationFrg classficationFrg = new ClassficationFrg();
        ShopCartFrg shoppingCartFrg = new ShopCartFrg();
        UserInfoFrg userInfoFrg = new UserInfoFrg();

        fragments = new Fragment[]{homeTabFrg, classficationFrg, shoppingCartFrg, userInfoFrg};

        fragmentTabHost.setCurrentTab(0);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new MenuAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPagerListener());

    }

    public View getTabItemView(int imageResId, int stringResId) {
        View view = layoutInflater.inflate(R.layout.item_main_menu_tab, null);

        ImageView image = (ImageView) view.findViewById(R.id.tab_item_image);
        image.setImageResource(imageResId);

        TextView text = (TextView) view.findViewById(R.id.tab_item_text);
        text.setText(stringResId);

        return view;
    }


    class MenuAdapter extends FragmentPagerAdapter {

        public MenuAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragments[arg0];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }

    class ViewPagerListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int index) {
            for (int i = 0; i < fragmentTabHost.getTabWidget().getTabCount(); i++) {
                View view = fragmentTabHost.getTabWidget().getChildAt(i);
                ImageView image = (ImageView) view.findViewById(R.id.tab_item_image);
                if (i == index) {
                    image.setImageResource(tabImageSelectedArray[i]);
                    setTitle(ResUtils.getString(tabStringArray[i]));
                } else {
                    image.setImageResource(tabImageNormalArray[i]);
                }
            }

            switch (index) {

                case 0:
                case 1:
                case 2:
//                    setStatusBarColor(R.color.clear);
                    break;
                case 3:
//                    setStatusBarColor(R.color.clear);

                    break;
            }


            fragmentTabHost.setCurrentTab(index);
        }
    }

    class TabClickListener implements View.OnClickListener {

        private int index;
        private FragmentTabHost fragmentTabHost;

        public TabClickListener(int index, FragmentTabHost fragmentTabHost) {
            this.index = index;
            this.fragmentTabHost = fragmentTabHost;
        }

        @Override
        public void onClick(View v) {

            for (int i = 0; i < fragmentTabHost.getTabWidget().getTabCount(); i++) {
                View view = fragmentTabHost.getTabWidget().getChildAt(i);
                ImageView image = (ImageView) view.findViewById(R.id.tab_item_image);
                if (i == index) {
                    image.setImageResource(tabImageSelectedArray[i]);
                } else {
                    image.setImageResource(tabImageNormalArray[i]);
                }
            }
            fragmentTabHost.setCurrentTab(index);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            LogUtils.e(scanResult);
            WebAct.startActivity(this, scanResult, "");
        }
    }

}
