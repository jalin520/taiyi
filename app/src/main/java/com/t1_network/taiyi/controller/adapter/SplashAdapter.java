package com.t1_network.taiyi.controller.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.t1_network.core.app.App;
import com.t1_network.core.utils.UIUtils;
import com.t1_network.taiyi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 2016/2/3.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class SplashAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
    private List<Integer> imageList;

    private LinearLayout llPoint;

    private List<ImageView> pointList;

    private List<String> imageUrlList;

    private ViewPager mPager;

    private LinearLayout llPlay;
    private final ImageLoader mImageLoader;

    public SplashAdapter(List<Integer> imageList, LinearLayout llPoint, ViewPager mPager, LinearLayout llPlay) {
        this.imageList = imageList;
        this.llPoint = llPoint;
        this.mPager = mPager;
        this.llPlay = llPlay;

        initViewList(imageList);
        mPager.setAdapter(this);
        mPager.setOnPageChangeListener(this);
        mImageLoader = ImageLoader.getInstance();
    }

    private void initViewList(List<Integer> imageList) {

        if (imageList == null) {
            return;
        }
        imageUrlList = new ArrayList();

        if (imageList.size() != 0) {

            for (int i = 0; i < imageList.size(); i++) {

//                ivIcon.setImageResource(imageList.get(i));
                String imageUri = "drawable://"+imageList.get(i); //  drawable文件
                imageUrlList.add(imageUri);
            }

        }
        initPoint();
    }

    private void initPoint() {

        llPoint.removeAllViews();

        int count = imageList.size();

        pointList = new ArrayList<ImageView>();

        for (int i = 0; i < count; i++) {
            ImageView point = getPoint(i);
            pointList.add(point);
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(UIUtils.dp2Px(10), UIUtils.dp2Px(10));
            if (i != 0)
                ll.leftMargin = UIUtils.dp2Px(20);
            llPoint.addView(point, ll);
        }
    }


    @Override
    public int getCount() {
        if (imageList == null)
            return 0;
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView ivIcon = new ImageView(App.context);
        ivIcon.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageLoader.displayImage(imageUrlList.get(position),ivIcon);
        container.addView(ivIcon);
        return ivIcon;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private int tipPointSelected = R.drawable.home_shape_oval_selected;

    private int tipPointNormal = R.drawable.home_shape_oval_normal;

    public ImageView getPoint(int index) {

        ImageView point = new ImageView(App.context);

        if (0 == index) {
            point.setImageResource(tipPointSelected);
        } else {
            point.setImageResource(tipPointNormal);
        }
        return point;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < pointList.size(); i++) {
            if (position == pointList.size() - 1) {
                llPlay.setVisibility(View.VISIBLE);
            } else {
                llPlay.setVisibility(View.GONE);
            }

            if (i == position) {
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
