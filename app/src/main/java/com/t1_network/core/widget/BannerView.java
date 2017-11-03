package com.t1_network.core.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.t1_network.core.utils.UIUtils;
import com.t1_network.taiyi.R;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/26 16:08
 * 修改记录:
 */
public class BannerView extends RelativeLayout {


    private Context context;

    private ImageLoader imageLoader;
    private DisplayImageOptions imageLoaderOptions = null;

    private ViewPager viewPager;
    private LinearLayout layoutPoint;


    private boolean isLoop = true;


    public BannerView(Context context) {
        super(context);
        init(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        imageLoader = ImageLoader.getInstance();
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


    public void setData(List<? extends BannerEntity> bannerEntityList) {
        BannerAdapter bannerAdapter = new BannerAdapter(this, bannerEntityList, isLoop);
        if (isLoop) {
            viewPager.setCurrentItem(1);
        }



        task.start();
    }

    public View getView(BannerEntity bannerEntity) {

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setOnClickListener(new OnBannerClickListener(bannerEntity, context));

        if (imageLoaderOptions != null) {
            imageLoader.displayImage(bannerEntity.getBannerImage(), imageView, imageLoaderOptions);
        } else {
            imageLoader.displayImage(bannerEntity.getBannerImage(), imageView);
        }

        return imageView;
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

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setImageLoaderOptions(DisplayImageOptions imageLoaderOptions) {
        this.imageLoaderOptions = imageLoaderOptions;
    }

    public void setIsLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }

    public LinearLayout getLayoutPoint() {
        return layoutPoint;
    }


    private AutoSwitchTask task = new AutoSwitchTask();

    /**
     * 开始和关闭轮播
     */
    class AutoSwitchTask implements Runnable {

        //开始
        public void start() {
            stop();
            UIUtils.postDelayed(this, 2000);
        }

        //停止
        public void stop() {
            UIUtils.removeCallBacks(this);
        }

        @Override
        public void run() {
            int currentItem = viewPager.getCurrentItem();
            viewPager.setCurrentItem(++currentItem);
            start();
        }
    }

}
