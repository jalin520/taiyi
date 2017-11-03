package com.t1_network.taiyi.widget.ImageLooker;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.imagezoom.ImageViewTouch;
import com.imagezoom.ImageViewTouchBase;
import com.t1_network.core.utils.UIUtils;
import com.t1_network.taiyi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/2/2 14:16
 * 修改记录:
 */
public class ImageLookerView extends RelativeLayout {


    private Context context;
    private ViewPager viewPager;
    private LinearLayout layoutPoint;

    private List<View> viewList;

    private List<ImageView> pointList;


    public ImageLookerView(Context context) {
        super(context);
        init(context);
    }

    public ImageLookerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        initView();
    }

    /**
     * init view
     */
    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.c_banner, this);
        viewPager = (ViewPager) view.findViewById(R.id.c_banner_view_pager);
        layoutPoint = (LinearLayout) view.findViewById(R.id.c_banner_layout_point);
    }

    private int tipPointSelected = R.drawable.home_shape_oval_selected;

    private int tipPointNormal = R.drawable.home_shape_oval_normal;

    public ImageView getPoint(int index) {

        ImageView point = new ImageView(context);

        if (0 == index) {
            point.setImageResource(tipPointSelected);
        } else {
            point.setImageResource(tipPointNormal);
        }
        return point;
    }

    private void initPoint() {
        layoutPoint.removeAllViews();

        int count = viewList.size();

        pointList = new ArrayList<ImageView>();

        for (int i = 0; i < count; i++) {

            ImageView point = getPoint(i);
            pointList.add(point);

            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(UIUtils.dp2Px(8), UIUtils.dp2Px(8));
            ll.leftMargin = UIUtils.dp2Px(14);
            layoutPoint.addView(point, ll);
        }
    }


    public void setData(List<ImageView> imageList) {

        viewList = getViewList(imageList);
        viewPager.setAdapter(new ImageLookerPagerAdapter(viewList));
        initPoint();
    }


//    public List<View> getViewList(List<Bitmap> bitmapList) {
//
//        List<View> viewList = new ArrayList<View>();
//
//
//        for (int i = 0; i < bitmapList.size(); i++) {
//
//
//
//
//        }
//
//        return viewList;
//    }


    public List<View> getViewList(List<ImageView> imageViewList) {

        List<View> viewList = new ArrayList<View>();

        for (int i = 0; i < imageViewList.size(); i++) {

            ImageView imageView = imageViewList.get(i);

            imageView.setDrawingCacheEnabled(true);
            Bitmap bitmap = imageView.getDrawingCache();
            imageView.setDrawingCacheEnabled(false);

            ImageViewTouch image = new ImageViewTouch(context);
            image.setImageBitmap(bitmap);
            image.setDisplayType(ImageViewTouchBase.DisplayType.FIT_IF_BIGGER);

            viewList.add(image);
        }

        return viewList;
    }


    public class ImageLookerPagerAdapter extends PagerAdapter {

        private List<View> viewList;

        public ImageLookerPagerAdapter(List<View> viewList) {
            this.viewList = viewList;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
