package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.markmao.pulltorefresh.widget.XScrollView;
import com.t1_network.core.app.App;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.TimeUtils;
import com.t1_network.core.utils.UIUtils;
import com.t1_network.core.utils.share.Share;
import com.t1_network.core.utils.share.ShareUtil;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.fragment.GoodAllConsultFrg;
import com.t1_network.taiyi.controller.fragment.GoodAppraiseFrg;
import com.t1_network.taiyi.controller.fragment.GoodDetailImageFrg;
import com.t1_network.taiyi.controller.listener.AddGoodListener;
import com.t1_network.taiyi.controller.listener.DelGoodListener;
import com.t1_network.taiyi.controller.reveiver.UserComparReceiver;
import com.t1_network.taiyi.db.TYSP;
import com.t1_network.taiyi.model.bean.cart.Cart;
import com.t1_network.taiyi.model.bean.good.Good;
import com.t1_network.taiyi.model.bean.good.GoodDetail;
import com.t1_network.taiyi.model.bean.good.GoodDetailImage;
import com.t1_network.taiyi.model.bean.good.GoodDetailInfoBean;
import com.t1_network.taiyi.model.bean.good.Spec;
import com.t1_network.taiyi.model.event.CollectEvent;
import com.t1_network.taiyi.model.event.LoginEvent;
import com.t1_network.taiyi.model.event.UpdateCartEvent;
import com.t1_network.taiyi.model.event.UpdateCompareEvent;
import com.t1_network.taiyi.net.api.good.CollectAPI;
import com.t1_network.taiyi.net.api.good.GoodDetailAPI;
import com.t1_network.taiyi.net.api.good.IsCollectAPI;
import com.t1_network.taiyi.net.api.shopcart.AddCartAPI;
import com.t1_network.taiyi.net.api.shopcart.GetShopCartAPI;
import com.t1_network.taiyi.widget.MyMarkerView;
import com.t1_network.taiyi.widget.ScoreView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import de.greenrobot.event.EventBus;

/**
 * 商品详情页分为两大块:1.商品信息模块;2.商品详情模块;这两个页面用一个VerticalViewPager包装
 * <p/>
 * 商品详情模块又分为三个模块:1.详情;2.评价;3.咨询 这三个模块用FragmentTabHost包装
 */

public class GoodDetailAct extends BasicAct implements OnChartValueSelectedListener, AddCartAPI.AddCartAPIListener
        , GoodDetailAPI.GoodDetailAPIListener, IsCollectAPI.IsCollectAPIListener
        , CollectAPI.CollectAPIListener, GetShopCartAPI.GetShopCartListener
        , UserComparReceiver.UserComparReceiverListener, XScrollView.IXScrollViewListener {

    /**
     * 商品详情的ViewPager 用于弹性拉动
     */
//    @Bind(R.id.act_good_detail_view_pager)
//    VerticalViewPager viewPager;

//            update
//    @Bind(R.id.act_good_detail_view_pager)
//    LinearLayout viewPager;

    @Bind(R.id.act_good_detail_scroll_view)
    XScrollView mScrollView;

//    update end

    @Bind(R.id.act_good_detail_add_compare_number)
    TextView tvCompareNumber;

    private UserComparReceiver mUserComparReceiver;
    private TextView mTvAddCompare;
    private View mGoodInfo;
    private View mGoodExtra;

    public GoodDetailAct() {
        super(R.layout.act_good_detail, NO_TITLE);
    }

    private static final String P_GOOD_ID = "P_GOOD_ID";
    private String goodId;

    public static void startActivity(Context context, String goodId) {
        Intent intent = new Intent(context, GoodDetailAct.class);
        intent.putExtra(P_GOOD_ID, goodId);
        context.startActivity(intent);
    }

    private List<View> viewList = new ArrayList<View>();

    @Override
    public void initView() {

        Intent intent = getIntent();
        goodId = intent.getStringExtra(P_GOOD_ID);

        tvCompareNumber.setText(TYSP.getCompareGood().size() + "");

        dialog.show();

        new GoodDetailAPI(this, goodId);
        new IsCollectAPI(this, goodId);
        new GetShopCartAPI(this);

        compareNumberReceiver();

        EventBus.getDefault().register(this);
    }

    private void compareNumberReceiver() {
        mUserComparReceiver = new UserComparReceiver(this);
        UserComparReceiver.register(this, mUserComparReceiver);
    }

    @Override
    public void receiverCompar() {
        tvCompareNumber.setText(TYSP.getCompareGood().size() + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserComparReceiver.unregister(this, mUserComparReceiver);
        EventBus.getDefault().unregister(this);
    }

    /**
     * 初始化页面的两大模块
     *
     * @param goodDetail
     */
//    private void initViewPager(GoodDetail goodDetail) {
//
//        viewList.clear();
//
//        //页面整体设置
//        View goodInfo = getLayoutInflater().inflate(R.layout.frg_good_detail_info, null);
//        View goodExtra = getLayoutInflater().inflate(R.layout.frg_good_detail_extra, null);
//
//        viewList.add(goodInfo);
//        viewList.add(goodExtra);
//
//        //初始化商品信息
//        initGoodInfoModule(goodInfo, goodDetail);
//
//        //初始化商品详情
//        initGoodExtra(goodExtra, goodDetail);
//
//        //初始化商品性能
//        initGoodInfoArr(goodDetail, goodInfo);
//
//        viewPager.setAdapter(new PagerAdapter() {
//            @Override
//            public int getCount() {
//                return viewList.size();
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//                return view == object;
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                ((VerticalViewPager) container).removeView(viewList.get(position));
//            }
//
//            //每次滑动的时候生成的组件
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                ((VerticalViewPager) container).addView(viewList.get(position));
//                return viewList.get(position);
//            }
//        });
//
//
//    }

    /**
     * 初始化页面的两大模块
     *
     * @param goodDetail
     */
    private void initViewPager(GoodDetail goodDetail) {

        mScrollView.setPullRefreshEnable(true);
        mScrollView.setPullLoadEnable(true);
        mScrollView.setAutoLoadEnable(false);
        mScrollView.setIXScrollViewListener(this);
        mScrollView.setRefreshTime(TimeUtils.getTime());

        viewList.clear();

        //页面整体设置
        mGoodInfo = getLayoutInflater().inflate(R.layout.frg_good_detail_info, null);
        mGoodExtra = getLayoutInflater().inflate(R.layout.frg_good_detail_extra, null);

        viewList.add(mGoodInfo);
        viewList.add(mGoodExtra);

        //初始化商品信息
        initGoodInfoModule(mGoodInfo, goodDetail);

        //初始化商品详情
        initGoodExtra(mGoodExtra, goodDetail);

        //初始化商品性能
        initGoodInfoArr(goodDetail, mGoodInfo);

//        viewPager.addView(goodInfo);

        mScrollView.setView(mGoodInfo);
        mScrollView.setView(mGoodExtra);
        mGoodExtra.setVisibility(View.GONE);
        mScrollView.setScrollViewListener(new XScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(XScrollView scrollView, int x, int y, int oldx, int oldy) {
                LogUtils.e("x " + x + " y" + y + " oldx" + oldx + " oldy" + oldy);

            }
        });

    }

    @Override
    public void onRefresh() {
        onLoad();
        LogUtils.e("onRefresh");
        mGoodInfo.setVisibility(View.VISIBLE);
        mGoodExtra.setVisibility(View.GONE);
        mScrollView.setPullLoadEnable(true);
    }

    @Override
    public void onLoadMore() {
        onLoad();
        mScrollView.setPullLoadEnable(false);
        mGoodInfo.setVisibility(View.GONE);
        mGoodExtra.setVisibility(View.VISIBLE);
        mScrollView.setScrollY(0);


    }
    private void onLoad() {
        mScrollView.stopRefresh();
        mScrollView.stopLoadMore();
        mScrollView.setRefreshTime(TimeUtils.getTime());
    }


    private boolean flag = false;

    private void initGoodInfoArr(final GoodDetail goodDetail, View goodInfo) {
        List<GoodDetailInfoBean> goodDetailInfoBeanList = goodDetail.getGoodDetailInfoBeanList();
        final LinearLayout llContent = (LinearLayout) goodInfo.findViewById(R.id.frg_good_detail_good_info_all_content);
        LinearLayout llMore = (LinearLayout) goodInfo.findViewById(R.id.frg_good_detail_good_info_show_more);
        final ImageView ivUPDown = (ImageView) goodInfo.findViewById(R.id.frg_good_detail_good_info_show_more_iv_icon);

        LogUtils.e(goodDetailInfoBeanList.size() + "");
        if (goodDetailInfoBeanList == null || goodDetailInfoBeanList.size() == 0) {
            return;
        }
        for (int i = 0; i < goodDetailInfoBeanList.size(); i++) {
            String name = goodDetailInfoBeanList.get(i).getName();
            List<GoodDetailInfoBean.CategoryEntity> category = goodDetailInfoBeanList.get(i).getCategory();
            if (category == null) {
                continue;
            } else {
                View infoView = LayoutInflater.from(this).inflate(R.layout.item_good_detail_info_title, null);
                ((TextView) infoView.findViewById(R.id.item_good_detail_info_tv_title)).setText(name);
                llContent.addView(infoView);
                for (GoodDetailInfoBean.CategoryEntity categoryEntity : category) {
                    View content = LayoutInflater.from(this).inflate(R.layout.item_good_detail_info_content, null);
                    String categoryEntityName = categoryEntity.getName();
                    String categoryEntityValue = categoryEntity.getValue();
                    ((TextView) content.findViewById(R.id.item_good_detail_info_tv_content_name)).setText(categoryEntityName);
                    ((TextView) content.findViewById(R.id.item_good_detail_info_tv_content_value)).setText(categoryEntityValue);
                    llContent.addView(content);
                }
            }
        }

        if (llContent.getChildCount() - 1 == 0) {
            llMore.setVisibility(View.GONE);
        } else if (llContent.getChildCount() <= 3) {
            llMore.setVisibility(View.GONE);
        } else if (llContent.getChildCount() > 3) {
            llMore.setVisibility(View.VISIBLE);
            for (int i = 0; i < llContent.getChildCount(); i++) {
                if (i > 3) {
                    llContent.getChildAt(i).setVisibility(View.GONE);
                }
            }
        }

        llMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (llContent.getChildCount() - 1 <= 3) {
                    showTip("已经没有更多的信息");
                    return;
                }
                flag = !flag;

                if (flag) {
                    for (int i = 0; i < llContent.getChildCount(); i++) {
                        if (llContent.getChildAt(i).getVisibility() == View.GONE) {
                            llContent.getChildAt(i).setVisibility(View.VISIBLE);
                        }
                    }
                    ivUPDown.setImageResource(R.drawable.ic_pack_up_2x);
                } else {
                    for (int i = 0; i < llContent.getChildCount(); i++) {
                        if (i > 3) {
                            llContent.getChildAt(i).setVisibility(View.GONE);
                        }
                    }
                    ivUPDown.setImageResource(R.drawable.ic_arrow_down_green);
                }

            }
        });

    }


    /**
     * 商品详情头部的 tab
     */
    private FragmentTabHost fragmentTabHost;

    /**
     * 商品详情头部tab的文本
     */
    private String[] titles = {"详情", "评论", "咨询"};

    /**
     * 商品详情 三个模块对应的Fragment
     */
    private Class[] tabFragmentArray = {GoodDetailImageFrg.class, GoodAppraiseFrg.class, GoodAllConsultFrg.class};


    public void initGoodExtra(View view, GoodDetail goodDetail) {

        fragmentTabHost = (FragmentTabHost) view.findViewById(R.id.frg_good_extra_tab_host);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.act_main_layout_frame);

        int count = tabFragmentArray.length;

        for (int i = 0; i < count; i++) {

            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(titles[i]).setIndicator(getTabItemView(titles[i], i));

            //给tabSpec添加.一个新的Fragment

            Bundle bundle = new Bundle();

            switch (i) {
                case 0:
                    bundle.putParcelableArrayList(GoodDetailImageFrg.P_GOOD_DETAIL_IMAGE, (ArrayList) goodDetail.getProduct().getGoodDetailImageList());
                    break;
                case 1:
                    bundle.putString(GoodAppraiseFrg.P_GOOD_ID, goodDetail.getProduct().getId());
                    break;
                case 2:
                    bundle.putString(GoodAllConsultFrg.P_GOOD_ID, goodDetail.getProduct().getId());
                    break;
            }

            fragmentTabHost.addTab(tabSpec, tabFragmentArray[i], bundle);

            //去除.分割线
            fragmentTabHost.getTabWidget().setDividerDrawable(null);

            //添加点击监听
            fragmentTabHost.getTabWidget().getChildAt(i).setOnClickListener(new TabClickListener(i, fragmentTabHost));
        }
//        fragmentTabHost.offsetLeftAndRight(2);

    }


    @Override
    public void apiGoodDetailAPISuccess(GoodDetail goodDetail) {
        //init 商品信息,商品详情
        this.goodDetail = goodDetail;
        initViewPager(goodDetail);
        dialog.dismiss();
    }


    private GoodDetail goodDetail;


    @Override
    public void apiGoodDetailAPIFailure(long code, String msg) {
        showTip(msg);
        dialog.dismiss();
    }


    /**
     * 获取 商品详情的首部tab 既 详情 评论 咨询三个tab
     *
     * @param str   tab的文字
     * @param index true为选中,false为不选中
     * @return
     */
    public View getTabItemView(String str, int index) {

        View view = getLayoutInflater().inflate(R.layout.item_good_extra_tab, null);
        TextView text = (TextView) view.findViewById(R.id.tab_item_text);

        text.setText(str);

        if (index == 0) {
            text.setBackgroundColor(ResUtils.getColor(R.color.colorPrimary));
            text.setTextColor(ResUtils.getColor(R.color.text_white));
        } else {
            text.setBackgroundColor(ResUtils.getColor(R.color.bg_layout));
            text.setTextColor(ResUtils.getColor(R.color.text_gray_mid));
        }

        return view;
    }




    /**
     * 商品详情额外信息的tab切换监听
     */
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

                TextView text = (TextView) view.findViewById(R.id.tab_item_text);

                if (i == index) {
                    text.setBackgroundColor(ResUtils.getColor(R.color.colorPrimary));
                    text.setTextColor(ResUtils.getColor(R.color.text_white));
                } else {
                    text.setBackgroundColor(ResUtils.getColor(R.color.bg_layout));
                    text.setTextColor(ResUtils.getColor(R.color.text_gray_mid));
                }
            }
            fragmentTabHost.setCurrentTab(index);
        }
    }


    /**
     * 收藏模块 begin--------------------------------------------------------------------------------------
     */


    @Bind(R.id.act_good_detail_image_is_collect)
    ImageView imageIsCollect; //是否收藏图片

    private boolean isCollect = false;

    @OnClick(R.id.act_good_detail_image_is_collect)
    public void collect() {

        if (!App.getApp().isLogin()) {
            LoginAndRegisterAct.startActivity(this);
            return;
        }

        new CollectAPI(this, goodId);
    }

    @Override
    public void apiIsCollectFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiIsCollectSuccess(boolean isCollect) {
        this.isCollect = isCollect;
        if (isCollect) {
            imageIsCollect.setImageResource(R.drawable.ic_favourite_selected);
        } else {
            imageIsCollect.setImageResource(R.drawable.ic_favourite_normal);
        }

        EventBus.getDefault().post(new CollectEvent());
    }


    @Override
    public void apiCollectFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiCollectSuccess() {
        isCollect = !isCollect;
        apiIsCollectSuccess(isCollect);
    }

    /**
     * 收藏模块 end--------------------------------------------------------------------------------------
     */


    /**
     * 客服模块 begin--------------------------------------------------------------------------------
     */

    @OnClick(R.id.act_good_detail_layout_customer)
    public void toCustomerAct() {
        CustomerAct.startActivity(this);
    }

    /**
     * 客服模块 end--------------------------------------------------------------------------------
     */

    /**
     * 对比模块 begin--------------------------------------------------------------------------------
     */
    @OnClick(R.id.act_good_detail_layout_constract)
    public void toConstractAct() {
        CompareGoodAct.startActivity(this);
    }

    /**
     * 对比模块 begin--------------------------------------------------------------------------------
     */

    /**
     * 购物车模块 begin--------------------------------------------------------------------------------
     */


    @Bind(R.id.act_good_detail_text_cart_num)
    TextView textCartNum;


    @Override
    public void apiGetShopCartFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiGetShopCartSuccess(Cart cart) {
        EventBus.getDefault().post(new UpdateCartEvent(cart));
    }

    @OnClick(R.id.act_good_detail_text_add_to_cart)
    public void addToCart() {

        if (!App.getApp().isLogin()) {
            LoginAndRegisterAct.startActivity(this);
            return;
        }


        String count = textNum.getText().toString();
        new AddCartAPI(this, goodId, count);
    }

    @Override
    public void apiAddCartFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiAddCartSuccess(Cart cart) {
        showTip(ResUtils.getString(R.string.act_good_detail_tip_add_to_cart_success));
        apiGetShopCartSuccess(cart);
        EventBus.getDefault().post(new UpdateCartEvent(cart));
    }

    /**
     * 购物车模块 end--------------------------------------------------------------------------------
     */


    /**
     * 商品信息 begin--------------------------------------------------------------------------------
     */

    public void initGoodInfoModule(View view, GoodDetail goodDetail) {

        initGoodImage(view, goodDetail); //init 商品轮播图片
        initGoodinfo(view, goodDetail);//init 商品信息
        initGoodScore(view, goodDetail); //设置商品的分数
        initGoodCount(view, goodDetail);
        initGoodBasicInfo(view, goodDetail);
        initRegister(view, goodDetail);

        initChartRadar(view, goodDetail);
        initBarChart(view, goodDetail);
    }

    /**
     * init 商品轮播图片
     */
    List<View> goodIVList = new ArrayList<View>();
    private List<ImageView> pointList;
    private LinearLayout layoutPoint;
    private int size;

    private void initGoodImage(View view, GoodDetail goodDetail) {

        if (goodDetail == null) {
            return;
        }

        goodIVList.clear();
        layoutPoint = (LinearLayout) view.findViewById(R.id.c_banner_layout_point);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.frg_good_detail_view_pager_good_image);
        size = goodDetail.getProduct().getGoodImageList().size();

        for (int i = 0; i < size; i++) {
            GoodDetailImage image = goodDetail.getProduct().getGoodImageList().get(i);

            View viewImage = getLayoutInflater().inflate(R.layout.item_good_detail_image, null);
            ImageView iv = (ImageView) viewImage.findViewById(R.id.item_good_detail_image);

            imageLoader.displayImage(image.getUrl(), iv);
            goodIVList.add(viewImage);
        }
        initPoint(size);

        //添加adapter
        viewPager.setAdapter(new android.support.v4.view.PagerAdapter() {
            @Override
            public int getCount() {
                return goodIVList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView(goodIVList.get(position));
            }

            //每次滑动的时候生成的组件
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(goodIVList.get(position));
                return goodIVList.get(position);
            }

        });

        //添加滑动监听器
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                textImagePage.setText((position + 1) + "/" + goodIVList.size());
                for (int i = 0; i < pointList.size(); i++) {

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
        });

//        textImagePage.setText("1/" + goodIVList.size());
    }

    private int tipPointSelected = R.drawable.home_shape_oval_selected;

    private int tipPointNormal = R.drawable.home_shape_oval_normal;

    public ImageView getPoint(int index) {

        ImageView point = new ImageView(this);

        if (0 == index) {
            point.setImageResource(tipPointSelected);
        } else {
            point.setImageResource(tipPointNormal);
        }
        return point;
    }

    private void initPoint(int size) {
        layoutPoint.removeAllViews();

        pointList = new ArrayList<ImageView>();

        for (int i = 0; i < size; i++) {

            ImageView point = getPoint(i);
            pointList.add(point);

            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(UIUtils.dp2Px(8), UIUtils.dp2Px(8));
            ll.leftMargin = UIUtils.dp2Px(8);

            layoutPoint.addView(point, ll);
        }
    }


    /**
     * init 商品信息
     *
     * @param view
     * @param goodDetail
     */
    private void initGoodinfo(View view, GoodDetail goodDetail) {

        TextView textMarketPrice = (TextView) view.findViewById(R.id.act_good_detail_text_market_price);
        TextView textPrice = (TextView) view.findViewById(R.id.act_good_detail_text_price);
        TextView textName = (TextView) view.findViewById(R.id.act_good_detail_text_name);
        mTvAddCompare = (TextView) view.findViewById(R.id.act_good_detail_add_compare);
        textMarketPrice.setText(ResUtils.getString(R.string.act_good_detail_text_market_price, goodDetail.getProduct().getMarketPrice()));
        textMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        textPrice.setText(goodDetail.getProduct().getPrice());
        textName.setText(goodDetail.getProduct().getName());
        addItemIsInster();
        mTvAddCompare.setOnClickListener(new CollectionListener(goodDetail));

    }


    /**
     * 自定义加入对比的点击事件
     */
    private class CollectionListener implements View.OnClickListener {
        private GoodDetail goodDetail;

        public CollectionListener(GoodDetail goodDetail) {
            this.goodDetail = goodDetail;
        }

        @Override
        public void onClick(View v) {
            List<GoodDetail> compareGood = TYSP.getCompareGood();
            Iterator<GoodDetail> iterator = compareGood.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                if (iterator.next().getProduct().getId().equals(goodDetail.getProduct().getId())) {
                    compareGood.remove(i);
                    TYSP.saveCompareGood(compareGood);
                    addItemIsInster();
                    UserComparReceiver.send(GoodDetailAct.this);
                    return;
                }
                i++;
            }
            TYSP.addCompareGood(goodDetail);
            addItemIsInster();
            UserComparReceiver.send(GoodDetailAct.this);
        }
    }

    private void addItemIsInster() {
        List<GoodDetail> goodList = TYSP.getCompareGood();

        for (GoodDetail detail : goodList) {

            if (detail.getProduct().getId().equals(goodDetail.getProduct().getId())) {
                mTvAddCompare.setTextColor(ResUtils.getColor(R.color.text_white));
                mTvAddCompare.setBackgroundResource(R.drawable.bg_btn_add_to_constracted);
                return;
            }
        }
        mTvAddCompare.setTextColor(ResUtils.getColor(R.color.text_black));
        mTvAddCompare.setBackgroundResource(R.drawable.bg_btn_add_to_constract);
    }

    /**
     * 设置商品的分数
     *
     * @param view
     * @param goodDetail
     */
    private void initGoodScore(View view, GoodDetail goodDetail) {

        TextView textScorePerference = (TextView) view.findViewById(R.id.act_good_detail_text_score_perference);
        TextView textScoreOperate = (TextView) view.findViewById(R.id.act_good_detail_text_score_operator);
        TextView textScoreBrand = (TextView) view.findViewById(R.id.act_good_detail_text_score_pro_score);
        TextView textScoreCost = (TextView) view.findViewById(R.id.act_good_detail_text_score_cost_perference);
        TextView textScoreExpert = (TextView) view.findViewById(R.id.act_good_detail_text_score_export_score);

        textScorePerference.setText(goodDetail.getProduct().getScorePerformance() == null ? "0" : goodDetail.getProduct().getScorePerformance());
        textScoreOperate.setText(goodDetail.getProduct().getScoreOperate() == null ? "0" : goodDetail.getProduct().getScoreOperate());
        textScoreBrand.setText(goodDetail.getProduct().getScoreBrand() == null ? "0" : goodDetail.getProduct().getScoreBrand());
        textScoreCost.setText(goodDetail.getProduct().getScoreCost() == null ? "0" : goodDetail.getProduct().getScoreCost());
        textScoreExpert.setText(goodDetail.getProduct().getScoreExpert() == null ? "0" : goodDetail.getProduct().getScoreExpert());

        ScoreView viewScorePerference = (ScoreView) view.findViewById(R.id.act_good_detail_view_score_perference);
        ScoreView viewScoreOperate = (ScoreView) view.findViewById(R.id.act_good_detail_view_score_operator);
        ScoreView viewScoreBrand = (ScoreView) view.findViewById(R.id.act_good_detail_view_score_pro_score);
        ScoreView viewScoreCost = (ScoreView) view.findViewById(R.id.act_good_detail_view_score_cost_perference);
        ScoreView viewScoreExpert = (ScoreView) view.findViewById(R.id.act_good_detail_view_score_export_score);

        viewScorePerference.setScore(Integer.parseInt(goodDetail.getProduct().getScorePerformance() == null ? "0" : goodDetail.getProduct().getScorePerformance()));
        viewScoreOperate.setScore(Integer.parseInt(goodDetail.getProduct().getScoreOperate() == null ? "0" : goodDetail.getProduct().getScoreOperate()));
        viewScoreBrand.setScore(Integer.parseInt(goodDetail.getProduct().getScoreBrand() == null ? "0" : goodDetail.getProduct().getScoreBrand()));
        viewScoreCost.setScore(Integer.parseInt(goodDetail.getProduct().getScoreCost() == null ? "0" : goodDetail.getProduct().getScoreCost()));
        viewScoreExpert.setScore(Integer.parseInt(goodDetail.getProduct().getScoreExpert() == null ? "0" : goodDetail.getProduct().getScoreExpert()));
    }

    private TextView textNum;

    private void initGoodCount(View view, GoodDetail goodDetail) {

        TextView textSales = (TextView) view.findViewById(R.id.act_good_detail_text_sale);
        textSales.setText(ResUtils.getString(R.string.act_good_detail_text_sales, goodDetail.getProduct().getSaleCount()));

        ImageView imageDel = (ImageView) view.findViewById(R.id.c_num_picker_image_del);
        ImageView imageAdd = (ImageView) view.findViewById(R.id.c_num_picker_image_add);
        textNum = (TextView) view.findViewById(R.id.c_num_picker_text_num);

        imageAdd.setOnClickListener(new AddGoodListener(textNum));
        imageDel.setOnClickListener(new DelGoodListener(textNum));
    }


    /**
     * init 商品基本信息
     *
     * @param view
     * @param goodDetail
     */
    private void initGoodBasicInfo(View view, GoodDetail goodDetail) {


        LinearLayout layoutBasicInfo = (LinearLayout) view.findViewById(R.id.frg_good_detail_info_layout_basic_info);
        LinearLayout layoutBasicInfoTitle = (LinearLayout) view.findViewById(R.id.frg_good_detail_info_layout_basic_info_title);
        LinearLayout layoutLoadMore = (LinearLayout) view.findViewById(R.id.frg_good_detail_info_layout_load_more);

        List<Spec> specList = goodDetail.getSpecList();

        if (specList == null || specList.size() == 0) {
            return;
        }

        for (int i = 0; i < specList.size(); i++) {

            Spec spec = specList.get(i);

            View temp = LayoutInflater.from(this).inflate(R.layout.item_spec, null);

            TextView textKey = (TextView) temp.findViewById(R.id.item_spec_text_key);
            TextView textValue = (TextView) temp.findViewById(R.id.item_spec_text_value);

            textKey.setText(spec.getKey());
            textValue.setText(spec.getValue());

            layoutBasicInfo.addView(temp);


            if (i > 3) {
                temp.setVisibility(View.GONE);
            }
        }

        //是否显示
        layoutBasicInfo.setVisibility(View.VISIBLE);
        layoutBasicInfoTitle.setVisibility(View.VISIBLE);


        //是否开放查看更多

        if (specList.size() > 4) {
            layoutLoadMore.setVisibility(View.VISIBLE);
        }

        layoutLoadMore.setOnClickListener(new OnLoadMoreLick(layoutBasicInfo));
    }


    private class OnLoadMoreLick implements View.OnClickListener {

        private boolean flag = false;

        private LinearLayout layout;

        public OnLoadMoreLick(LinearLayout layout) {
            this.layout = layout;
        }

        @Override
        public void onClick(View v) {

            if (layout == null) {
                return;
            }

            if (flag) {

                for (int i = 0; i < layout.getChildCount(); i++) {

                    layout.getChildAt(i).setVisibility(View.VISIBLE);

                }

            } else {
                for (int i = 0; i < layout.getChildCount(); i++) {

                    if (i > 3) {
                        layout.getChildAt(i).setVisibility(View.GONE);
                    }
                }
            }

            flag = !flag;
        }
    }


    /**
     * init 认证,如3C认证
     */
    private void initRegister(View view, GoodDetail goodDetail) {

        LinearLayout layoutRegisterMakeInChina = (LinearLayout) view.findViewById(R.id.act_good_detail_layout_register_made_in_china);
        setVisible(layoutRegisterMakeInChina, goodDetail.getProduct().hasRegisterMadeInChina());

        ImageView ivIOS9001 = (ImageView) view.findViewById(R.id.ic_register_iso9001);
        ImageView ivIOS13485 = (ImageView) view.findViewById(R.id.ic_register_ios13485);
        ImageView iv3C = (ImageView) view.findViewById(R.id.ic_register_3c);
        ImageView ivWeb = (ImageView) view.findViewById(R.id.ic_register_web);
        ImageView ivHealth = (ImageView) view.findViewById(R.id.ic_register_health);
        ImageView ivMadeInChina = (ImageView) view.findViewById(R.id.ic_register_made_in_china_selected);
        TextView textHealth = (TextView) view.findViewById(R.id.text_register_health);


        initRegisterIOS9001(ivIOS9001, goodDetail.getProduct().getRegisterS9001());
        initRegisterIOS13485(ivIOS13485, goodDetail.getProduct().getRegisterS13485());
        initRegister3C(iv3C, goodDetail.getProduct().getRegister3C());
        initRegisterHealth(ivHealth, textHealth, goodDetail.getProduct().getRegisterHealth());
        initRegisterWeb(ivWeb, goodDetail.getProduct().getRegisterWeb());
        initRegisterMadeInChina(ivMadeInChina, goodDetail.getProduct().getRegisterMadeinchina());

    }

    /**
     * init icon IOS9001
     *
     * @param imageView
     * @param status
     */
    private void initRegisterIOS9001(ImageView imageView, String status) {
        if ("0".equals(status)) {
            imageView.setImageResource(R.drawable.ic_register_iso9001_0);
            return;
        }

        if ("1".equals(status)) {
            imageView.setImageResource(R.drawable.ic_register_iso9001_1);
            return;
        }

        if ("2".equals(status)) {
            imageView.setImageResource(R.drawable.ic_register_iso9001_2);
            return;
        }
    }

    /**
     * init icon ios13485
     *
     * @param imageView
     * @param status
     */
    private void initRegisterIOS13485(ImageView imageView, String status) {
        if ("0".equals(status)) {
            imageView.setImageResource(R.drawable.ic_register_iso13485_0);
            return;
        }

        if ("1".equals(status)) {
            imageView.setImageResource(R.drawable.ic_register_iso13485_1);
            return;
        }

        if ("2".equals(status)) {
            imageView.setImageResource(R.drawable.ic_register_iso13485_2);
            return;
        }
    }


    private void initRegister3C(ImageView imageView, String status) {
        if ("0".equals(status)) {
            imageView.setImageResource(R.drawable.ic_register_3c_selected);
            return;
        }

        if ("1".equals(status)) {
            imageView.setImageResource(R.drawable.ic_register_3c_selected);
            return;
        }
    }


    private void initRegisterHealth(ImageView imageView, TextView textView, String status) {
        if ("1".equals(status)) {
            imageView.setImageResource(R.drawable.ic_register_health_selected);
            return;
        }

        if ("2".equals(status)) {
            imageView.setImageResource(R.drawable.ic_register_health_selected);
            return;
        }
    }

    private void initRegisterWeb(ImageView imageView, String status) {
        if ("0".equals(status)) {
            imageView.setImageResource(R.drawable.ic_register_web_normal);
            return;
        }

        if ("1".equals(status)) {
            imageView.setImageResource(R.drawable.ic_register_web_selected);
            return;
        }
    }

    private void initRegisterMadeInChina(ImageView imageView, String status) {

        if ("0".equals(status)) {
            imageView.setVisibility(View.GONE);
            return;
        }

        if ("1".equals(status)) {
//            imageView.setImageResource(R.drawable.ic_register_made_in_china_selected);
            imageView.setVisibility(View.VISIBLE);
            return;
        }
    }


    private void setVisible(View view, boolean isVisible) {
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    RadarChart radarChart;
    private Typeface tf;

    /**
     * init 雷达图
     *
     * @param view
     * @param goodDetail
     */
    private void initChartRadar(View view, GoodDetail goodDetail) {

        radarChart = (RadarChart) view.findViewById(R.id.chart_radar);

        radarChart.setDescription("");

        radarChart.setWebLineWidth(1.5f);
        radarChart.setWebLineWidthInner(0.75f);
        radarChart.setWebAlpha(100);

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        radarChart.setMarkerView(mv);

        setData(goodDetail);

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setTypeface(tf);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(ResUtils.getColor(R.color.colorPrimary));

        YAxis yAxis = radarChart.getYAxis();
        yAxis.setTypeface(tf);
        yAxis.setLabelCount(5, false);
        yAxis.setTextColor(ResUtils.getColor(R.color.transparent));
        yAxis.setTextSize(0f);
        yAxis.setStartAtZero(true);

    }

    private String[] mParties = new String[]{
            "性能得分", "易操作性", "品牌实力", "抽检得分", "专家评分"};

    public void setData(GoodDetail goodDetail) {

        float mult = 150;
        int cnt = 5;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        yVals1.add(new Entry(Float.parseFloat(goodDetail.getProduct().getScorePerformance() == null ? "0" : goodDetail.getProduct().getScorePerformance()), 0));
        yVals1.add(new Entry(Float.parseFloat(goodDetail.getProduct().getScoreOperate() == null ? "0" : goodDetail.getProduct().getScoreOperate()), 1));
        yVals1.add(new Entry(Float.parseFloat(goodDetail.getProduct().getScoreBrand() == null ? "0" : goodDetail.getProduct().getScoreBrand()), 2));
        yVals1.add(new Entry(Float.parseFloat(goodDetail.getProduct().getScoreCost() == null ? "0" : goodDetail.getProduct().getScoreCost()), 3));
        yVals1.add(new Entry(Float.parseFloat(goodDetail.getProduct().getScoreExpert() == null ? "0" : goodDetail.getProduct().getScoreExpert()), 4));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < cnt; i++)
            xVals.add(mParties[i % mParties.length]);

        RadarDataSet set1 = new RadarDataSet(yVals1, "评分");
        set1.setColor(ResUtils.getColor(R.color.text_orange));
        set1.setDrawFilled(true);
        set1.setLineWidth(2f);

        RadarData data = new RadarData(xVals, set1);
        data.setValueTypeface(tf);
        data.setValueTextSize(8f);
        data.setDrawValues(false);

        radarChart.setData(data);
        radarChart.invalidate();
    }


    private BubbleChart bubbleChart;

    /**
     * init 条形图
     *
     * @param view
     * @param goodDetail
     */
    private void initBarChart(View view, GoodDetail goodDetail) {

        bubbleChart = (BubbleChart) view.findViewById(R.id.chart_bubble);
        bubbleChart.setDescription("");
        Typeface tf;
        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        bubbleChart.setOnChartValueSelectedListener(this);

        bubbleChart.setDrawGridBackground(false);

        bubbleChart.setTouchEnabled(true);

        // enable scaling and dragging
        bubbleChart.setDragEnabled(true);
        bubbleChart.setScaleEnabled(true);

        bubbleChart.setMaxVisibleValueCount(200);
        bubbleChart.setPinchZoom(true);

        bubbleChart.getAxisLeft().setStartAtZero(false);
        bubbleChart.getAxisRight().setStartAtZero(false);

        setBubbleData(goodDetail);

//        Legend l = bubbleChart.getLegend();
//        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
//        l.setTypeface(tf);


        YAxis yl = bubbleChart.getAxisLeft();
        yl.setTypeface(tf);
        yl.setSpaceTop(30f);
        yl.setStartAtZero(false);
        yl.setSpaceBottom(30f);

        bubbleChart.getAxisRight().setEnabled(false);

        XAxis xl = bubbleChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tf);

    }

    private void setBubbleData(GoodDetail goodDetail) {

        List<Good> goodList = goodDetail.getBubbleGoodList();

        if (goodList == null) {
            return;
        }

        int count = goodList.size();

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BubbleEntry> yVals1 = new ArrayList<BubbleEntry>();

        float maxPrice = 0;

        for (int i = 0; i < goodList.size(); i++) {
            float price = Float.parseFloat(goodList.get(i).getPrice());

            if (maxPrice < price) {
                maxPrice = price;
            }
        }

        for (int i = 0; i < goodList.size(); i++) {

            if (i == 0) {
                xVals.add("0");
                continue;
            }

            if (i == goodList.size() - 1) {
                xVals.add(maxPrice + "");
                continue;
            }


        }


        for (int i = 0; i < goodList.size(); i++) {
            float price = Float.parseFloat(goodList.get(i).getPrice());

            int score = (int) Float.parseFloat(goodDetail.getProduct().getScorePerformance());

            xVals.add(price + "");
            yVals1.add(new BubbleEntry(i, 2, 2));

        }

        xVals.add("500");
        yVals1.add(new BubbleEntry(goodList.size(), 2, 2));


        // create a dataset and give it a type
        BubbleDataSet set1 = new BubbleDataSet(yVals1, "性能得分-价格");
        set1.setColor(ColorTemplate.COLORFUL_COLORS[2], 130);
        set1.setDrawValues(true);

        // create a data object with the datasets
        BubbleData data = new BubbleData(xVals, set1);
        data.setValueTypeface(tf);
        data.setValueTextSize(8f);
        data.setValueTextColor(Color.WHITE);
        data.setHighlightCircleWidth(1.5f);

        bubbleChart.setData(data);
        bubbleChart.invalidate();
    }


    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    /**
     * 商品信息 end--------------------------------------------------------------------------------
     */

    @OnClick(R.id.act_good_detail_layout_cart)
    public void toCartAct() {
        CartAct.startActivity(this);
    }


    /**
     * 更新购物车事件
     *
     * @param event
     */
    public void onEventMainThread(UpdateCartEvent event) {

        if (event.getCart() == null) {
            new GetShopCartAPI(this);
            return;
        }

        long num = event.getCart().getGoodList().size();

        if (num == 0) {
            textCartNum.setVisibility(View.GONE);
        } else {
            textCartNum.setVisibility(View.VISIBLE);
        }

        textCartNum.setText(num + "");
    }

    /**
     * 登录事件
     *
     * @param event
     */
    public void onEventMainThread(LoginEvent event) {

    }

    public void onEventMainThread(UpdateCompareEvent event) {
        addItemIsInster();
    }


    @OnClick(R.id.act_good_detail_image_share_sina)
    public void onClickDetailImage(View v) {
        ShareAct.startActivity(this, goodDetail);
    }


    @OnClick(R.id.act_good_detail_image_share_friend_circle)
    public void onClickFriendShare(View v) {

        Share share = new Share();
        share.setTitle(goodDetail.getProduct().getName());
        share.setImageUrl(goodDetail.getProduct().getGoodImageList().get(0).getUrl());
        share.setText(goodDetail.getProduct().getSummary());
        share.setUrl("http://tyijian.com/product?id=" + goodDetail.getProduct().getId());
        ShareUtil.share(WechatMoments.NAME, this, share);
    }

    @OnClick(R.id.act_good_detail_image_share_wechat)
    public void onClickWechatShare(View v) {

        Share share = new Share();
        share.setTitle(goodDetail.getProduct().getName());
        share.setImageUrl(goodDetail.getProduct().getGoodImageList().get(0).getUrl());
        share.setText(goodDetail.getProduct().getSummary());
        share.setUrl("http://tyijian.com/product?id=" + goodDetail.getProduct().getId());

        ShareUtil.share(Wechat.NAME, this, share);
    }
}
