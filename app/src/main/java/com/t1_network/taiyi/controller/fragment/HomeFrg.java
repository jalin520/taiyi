package com.t1_network.taiyi.controller.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.markmao.pulltorefresh.widget.XScrollView;
import com.t1_network.core.controller.BasicFrg;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.TimeUtils;
import com.t1_network.core.utils.UIUtils;
import com.t1_network.core.widget.BannerView;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.activity.GoodDetailAct;
import com.t1_network.taiyi.controller.activity.HomeSearchAct;
import com.t1_network.taiyi.controller.activity.MsgCenterAct;
import com.t1_network.taiyi.controller.activity.WebAct;
import com.t1_network.taiyi.model.bean.home.Activity;
import com.t1_network.taiyi.model.bean.home.SpecialGood;
import com.t1_network.taiyi.model.factory.ImageOptionFactory;
import com.t1_network.taiyi.net.api.home.ActivityAPI;
import com.t1_network.taiyi.net.api.home.BannerAPI;
import com.t1_network.taiyi.net.api.home.SpecialGoodAPI;
import com.t1_network.taiyi.widget.XCRoundRectImageView;
import com.t1_network.taiyi.widget.bargainGood.BargainGoodView;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by David on 15/10/13.
 */
public class HomeFrg extends BasicFrg implements XScrollView.IXScrollViewListener, BannerAPI.BannerAPIListener, ActivityAPI.ActivityAPIListener, SpecialGoodAPI.SpecialGoodAPIListener {

    private List<Integer> imageIds;

    private List<Integer> icons;

    private List<Integer> imageList;

    public HomeFrg() {
        super(R.layout.frg_home);
    }

    @Override
    public void initView(View view) {

        View content = initXScrollView();

//        initMyScrollView(content);

        bind(content); //绑定view与id

        mScrollView.autoRefresh(); //刷新以更新页面数据
    }

    @Bind(R.id.xScollView)
    XScrollView mScrollView;

    private View content;

    /**
     * init XScrollView
     */
    private View initXScrollView() {

        mScrollView.setPullRefreshEnable(true);
        mScrollView.setPullLoadEnable(false);
        mScrollView.setAutoLoadEnable(true);
        mScrollView.setIXScrollViewListener(this);
        mScrollView.setRefreshTime(TimeUtils.getTime());

        content = LayoutInflater.from(context).inflate(R.layout.content_frg_home, null);

        if (null != content) {

        }
        /**
         * init 自定义滚动视图,用于监听滑动时 更改 toolbar的颜色
         *
         */
        mScrollView.setView(content);
        mScrollView.setScrollViewListener(new XScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(XScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (scrollView == mScrollView) {
                    if (y > 350) {
                        mTitleRoot.setBackgroundColor(ResUtils.getColor(R.color.frg_home_title_color));
                    } else {
                        mTitleRoot.setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        });
        return content;
    }

    @Override
    public void onLoadMore() {
        onLoad();
    }

    @Override
    public void onRefresh() {
        mTitleRoot.setVisibility(View.GONE);
        new BannerAPI(this);
        new ActivityAPI(this);
        new SpecialGoodAPI(this);
    }

    private void onLoad() {
        mScrollView.stopRefresh();
        mScrollView.stopLoadMore();
        mScrollView.setRefreshTime(TimeUtils.getTime());
        mTitleRoot.setVisibility(View.VISIBLE);
    }


    @Bind(R.id.frg_home_title_root)
    RelativeLayout mTitleRoot;


    private void bind(View view) {
        banner = (BannerView) view.findViewById(R.id.banner);


        bargainGoodView = (BargainGoodView) view.findViewById(R.id.frg_home_bargain_good_view);


        layoutModule1 = (LinearLayout) view.findViewById(R.id.layout_module1);
        layoutModule2 = (LinearLayout) view.findViewById(R.id.layout_module2);
        layoutModule3 = (GridLayout) view.findViewById(R.id.frg_home_good_gl);
        layoutModule4 = (GridLayout) view.findViewById(R.id.frg_home_good_gl_2);
        layoutModule5 = (LinearLayout) view.findViewById(R.id.frg_home_layout_activity);
    }


    private BannerView banner;

    /**
     * init Banner
     */
    private void initBanner(List<com.t1_network.taiyi.model.bean.home.Banner> bannerList) {
        banner.setImageLoaderOptions(ImageOptionFactory.getBannerOptions());
        banner.setData(bannerList);
    }

    @Override
    public void apiBannerFailure(long code, String msg) {
        showTip(msg);
        onLoad();
    }


    @Override
    public void apiBannerSuccess(List<com.t1_network.taiyi.model.bean.home.Banner> bannerList) {
        initBanner(bannerList);
        onLoad();
    }

    /**
     * init 特价商品
     *
     * @param goodList
     */
    private void initBargainGood(List<SpecialGood> goodList) {
        bargainGoodView.setData(goodList);
    }

    @Override
    public void apiSpecialGoodFailure(long code, String msg) {
        showTip(msg);
        onLoad();
    }

    @Override
    public void apiSpecialGoodSuccess(List<SpecialGood> goodList) {
        initBargainGood(goodList);
        onLoad();
    }

    /**
     * init 活动五大模块
     */
    private BargainGoodView bargainGoodView;

    LinearLayout layoutModule1;
    LinearLayout layoutModule2;
    private GridLayout layoutModule3;
    private GridLayout layoutModule4;
    private LinearLayout layoutModule5;

    @Override
    public void apiActivityFailure(long code, String msg) {
        showTip(msg);
        onLoad();
    }


    @Override
    public void apiActivitySuccess(List<Activity> module1, List<Activity> module2, List<Activity> module3, List<Activity> module4, List<Activity> module5) {
        initModule1(module1);
        initModule2(module2);
        initModule3(module3);
        initModule4(module4);
        initModule5(module5);
        onLoad();
    }


    /**
     * init module1 横屏圆角大图
     *
     * @param actList
     */
    private void initModule1(List<Activity> actList) {
        if (actList == null) {
            return;
        }

        layoutModule1.removeAllViews();

        for (int i = 0; i < actList.size(); i++) {

            Activity act = actList.get(i);
            LogUtils.e(act.getImage());
            View view = LayoutInflater.from(context).inflate(R.layout.item_home_module_1, null);

            XCRoundRectImageView imageView = (XCRoundRectImageView) view.findViewById(R.id.item_home_module_1);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageLoader.displayImage(act.getImage(), imageView, ImageOptionFactory.getBannerOptions());
            imageView.setOnClickListener(new OnClickActivityListener(act));

            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ResUtils.getDimen(R.dimen.home_dotLayout_adv_height));
            ll.setMargins((int) ResUtils.getDimen(R.dimen.common_margin), (int) ResUtils.getDimen(R.dimen.home_dotLayout_adv_margin_top), (int) ResUtils.getDimen(R.dimen.common_margin), 0);

            layoutModule1.addView(imageView, ll);
        }
    }

    /**
     * init module2 横屏小图
     *
     * @param actList
     */
    private void initModule2(List<Activity> actList) {
        if (actList == null) {
            return;
        }
        layoutModule2.removeAllViews();

        for (int i = 0; i < actList.size(); i++) {

            Activity act = actList.get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.item_home_module_2, null);

            ImageView imageView = (ImageView) view.findViewById(R.id.item_home_module_2);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageLoader.displayImage(act.getImage(), imageView, ImageOptionFactory.getBannerOptions());
            imageView.setOnClickListener(new OnClickActivityListener(act));

            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ResUtils.getDimen(R.dimen.home_dotLayout_adv_height_small));
            ll.setMargins(0, (int) ResUtils.getDimen(R.dimen.home_dotLayout_adv_margin_top), 0, 0);

            layoutModule2.addView(imageView, ll);
        }
    }

    /**
     * init module 3 6格图
     *
     * @param actList
     */
    private void initModule3(List<Activity> actList) {
        if (actList == null) {
            return;
        }

        int imageWidth = UIUtils.getWidthByPx() / 3;

        for (int i = 0; i < actList.size(); i++) {


            Activity act = actList.get(i);

            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            imageLoader.displayImage(act.getImage(), imageView, ImageOptionFactory.getGoodOptions());

            imageView.setOnClickListener(new OnClickActivityListener(act));

            GridLayout.LayoutParams gl = new GridLayout.LayoutParams();
            gl.width = imageWidth;
            gl.height = imageWidth;

            layoutModule3.addView(imageView, gl);
        }
    }

    /**
     * init module4 12格图
     *
     * @param actList
     */
    private void initModule4(List<Activity> actList) {
        if (actList == null) {
            return;
        }

        int imageWidth = UIUtils.getWidthByPx() / 4;

        for (int i = 0; i < actList.size(); i++) {

            Activity act = actList.get(i);

            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageLoader.displayImage(act.getImage(), imageView, ImageOptionFactory.getGoodOptions());

            imageView.setOnClickListener(new OnClickActivityListener(act));

            GridLayout.LayoutParams gl = new GridLayout.LayoutParams();
            gl.width = imageWidth;
            gl.height = imageWidth;

            layoutModule4.addView(imageView, gl);
        }
    }

    /**
     * init module5 横向活动图
     *
     * @param actList
     */
    private void initModule5(List<Activity> actList) {

        if (actList == null) {
            return;
        }

        layoutModule5.removeAllViews();

        for (int i = 0; i < actList.size(); i++) {

            Activity act = actList.get(i);

            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageLoader.displayImage(act.getImage(), imageView, ImageOptionFactory.getBannerOptions());
            imageView.setOnClickListener(new OnClickActivityListener(act));
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ResUtils.getDimen(R.dimen.home_dotLayout_adv_height_min));

            ll.setMargins(0, (int) ResUtils.getDimen(R.dimen.ty_30px), 0, 0);

            layoutModule5.addView(imageView, ll);

        }
    }

    /**
     * 点击监听
     */
    public class OnClickActivityListener implements View.OnClickListener {

        private Activity activity;

        public OnClickActivityListener(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onClick(View v) {


            if (!TextUtils.isEmpty(activity.getUrl())) {
                if (!TextUtils.isEmpty(activity.getUrl().trim())) {
                    WebAct.startActivity(context, activity.getUrl(), activity.getKeyword());
                    return;
                }
            }

            LogUtils.e(activity.getGoodId());

            if (!TextUtils.isEmpty(activity.getGoodId())) {
                GoodDetailAct.startActivity(context, activity.getGoodId());
            }
        }
    }

    //************************************轮播图 start


    @OnClick(R.id.frg_title_good_list_search_rl)
    public void onClickSearch() {
        HomeSearchAct.startActivity(context);
    }


    @OnClick(R.id.frg_home_title_scan)
    public void scan() {
        //打开扫描界面扫描条形码或二维码
        Intent openCameraIntent = new Intent(context, CaptureActivity.class);
        startActivityForResult(openCameraIntent, 0);
    }

    @OnClick(R.id.frg_home_title_message)
    public void toMsgAct() {
        MsgCenterAct.startActivity(context);
    }


}


