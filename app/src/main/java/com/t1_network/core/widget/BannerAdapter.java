package com.t1_network.core.widget;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.t1_network.core.utils.UIUtils;
import com.t1_network.taiyi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/26 16:31
 * 修改记录:
 */
public class BannerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private List<View> viewList;

    private List<ImageView> pointList;

    private List<? extends BannerEntity> bannerEntityList;

    private BannerView bannerView;

    private boolean isLoop;

    public BannerAdapter(BannerView bannerView, List<? extends BannerEntity> bannerEntityList, boolean isLoop) {

        this.bannerEntityList = bannerEntityList;
        this.bannerView = bannerView;
        this.isLoop = isLoop;

        initViewList(bannerEntityList);
        bannerView.getViewPager().setAdapter(this);
        bannerView.getViewPager().setOnPageChangeListener(this);
    }

    private void initViewList(List<? extends BannerEntity> bannerEntityList) {

        if (bannerEntityList == null) {
            return;
        }

        viewList = new ArrayList<View>();

        if (bannerEntityList.size() != 0 && isLoop) {
            viewList.add(bannerView.getView(bannerEntityList.get(bannerEntityList.size() - 1)));
        }

        for (int i = 0; i < bannerEntityList.size(); i++) {
            BannerEntity bannerEntity = bannerEntityList.get(i);
            viewList.add(bannerView.getView(bannerEntity));
        }

        if (bannerEntityList.size() != 0 && isLoop) {
            viewList.add(bannerView.getView(bannerEntityList.get(0)));
        }

        initPoint();

    }

    private void initPoint() {
        bannerView.getLayoutPoint().removeAllViews();

        int count = bannerEntityList.size();

        pointList = new ArrayList<ImageView>();

        for (int i = 0; i < count; i++) {

            ImageView point = bannerView.getPoint(i);
            pointList.add(point);

            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(UIUtils.dp2Px(8), UIUtils.dp2Px(8));
            ll.leftMargin = UIUtils.dp2Px(14);

            bannerView.getLayoutPoint().addView(point, ll);
        }
    }


    @Override
    public int getCount() {
        if (viewList == null) {
            return 0;
        }
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (isLoop) {

            if (position == 0) {
                bannerView.getViewPager().setCurrentItem(viewList.size() - 2, false);
                return;
            } else if (position == viewList.size() - 1) {
                bannerView.getViewPager().setCurrentItem(1, false);
                return;
            }
        }

        for (int i = 0; i < pointList.size(); i++) {

            if (i == position - 1) {
                pointList.get(i).setImageResource(R.drawable.home_shape_oval_selected);
            } else {
                pointList.get(i).setImageResource(R.drawable.home_shape_oval_normal);
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
