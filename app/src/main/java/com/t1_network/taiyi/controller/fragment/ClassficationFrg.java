package com.t1_network.taiyi.controller.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.t1_network.core.controller.BasicFrg;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.activity.HomeSearchAct;
import com.t1_network.taiyi.controller.activity.MsgCenterAct;
import com.t1_network.taiyi.model.bean.home.Classfication;
import com.t1_network.taiyi.net.api.home.ClassficationAPI;
import com.t1_network.taiyi.widget.CustomViewpager;
import com.t1_network.taiyi.widget.TYProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by David on 15/11/27.
 */
public class ClassficationFrg extends BasicFrg implements ClassficationAPI.ClassficationAPIListener {


    //创建一个集合.来保存Title名字
    private List<String> nameList = new ArrayList<String>();

    //创建一个集合.来保存Fragment
    private List<Fragment> frgList;

    //初始化TabLayout
    @Bind(R.id.frg_class_fiction_tab)
    TabLayout tab;

    //初始化ViewPager
    @Bind(R.id.frg_class_fiction_view_pager)
    CustomViewpager viewPager;

    TYProgressDialog dialog;


    public ClassficationFrg() {
        super(R.layout.frg_classfication);
    }

    @Override
    public void initView(View view) {


        new ClassficationAPI(this);

    }


    private class ClassFicationAdapter extends FragmentPagerAdapter {

        //定义一个.集合
        private List<Fragment> fragmentList;

        public ClassFicationAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return nameList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @OnClick(R.id.frg_class_fiction_search)
    public void searchOnClick() {
        HomeSearchAct.startActivity(context);
    }

    @OnClick(R.id.frg_class_fiction_message)
    public void messageOnClick() {
        MsgCenterAct.startActivity(context);
    }


    @Override
    public void apiClassficationFailure(long code, String msg) {
        showTip(msg);

    }

    @Override
    public void apiClassficationSuccess(List<Classfication> classficationList) {


        if (classficationList == null || classficationList.size() == 0) {
            return;
        }
        frgList = new ArrayList<>();

        for (int i = 0; i < classficationList.size(); i++) {

            Classfication classfication = classficationList.get(i);
            tab.addTab(tab.newTab().setText(classfication.getName()));
            nameList.add(classfication.getName());

            //初始化Fragment的集合


            ClassFicationForBaseFrg frgBase = new ClassFicationForBaseFrg();
            Bundle bundleBase = new Bundle();
            bundleBase.putParcelableArrayList("classfication", (ArrayList<Classfication>) classficationList.get(i).getChildList());
            frgBase.setArguments(bundleBase);

            frgList.add(frgBase);

        }

        //设置Viewpager
        viewPager.setAdapter(new ClassFicationAdapter(getFragmentManager(), frgList));
        //
        tab.setupWithViewPager(viewPager);

    }
}
