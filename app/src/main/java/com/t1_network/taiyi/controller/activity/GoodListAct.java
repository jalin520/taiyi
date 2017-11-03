package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.markmao.pulltorefresh.widget.XListView;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.SpanStringUtils;
import com.t1_network.core.utils.TimeUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.GoodListAdapter;
import com.t1_network.taiyi.controller.adapter.GoodListPopupAdapter;
import com.t1_network.taiyi.controller.reveiver.UserComparReceiver;
import com.t1_network.taiyi.db.TYSP;
import com.t1_network.taiyi.model.bean.good.Feature;
import com.t1_network.taiyi.model.bean.good.Good;
import com.t1_network.taiyi.model.bean.good.GoodDetail;
import com.t1_network.taiyi.net.api.good.FeatureAPI;
import com.t1_network.taiyi.net.api.good.GoodDetailAPI;
import com.t1_network.taiyi.net.api.good.GoodListByClassficationAPI;
import com.t1_network.taiyi.net.api.good.GoodListBySearchAPI;
import com.t1_network.taiyi.widget.TipView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class GoodListAct extends BasicAct implements FeatureAPI.FeatureAPIListener, GoodListByClassficationAPI.GoodListByClassficationAPIListener, XListView.IXListViewListener, GoodListBySearchAPI.GoodListBySearchAPIListener, UserComparReceiver.UserComparReceiverListener, GoodListAdapter.AddCompareListener, GoodDetailAPI.GoodDetailAPIListener {

    @Bind(R.id.act_good_list_rv)
    XListView mListView;

    private GoodListAdapter goodAdapter = new GoodListAdapter(new ArrayList<Good>());

    @Bind(R.id.act_good_list_title_text)
    TextView textTitle;


    private String[] mStringArray;

    private ArrayAdapter<String> mAdapter;


    //title TextView
    private Bundle mExtras;
    @Bind(R.id.act_good_list_taiyi_feature)
    TextView mTvFeature;
    @Bind(R.id.act_good_list_iv_feature)
    ImageView mIvPopFaeture;

    @Bind(R.id.act_good_list_tv_all)
    TextView mTvPopAll;
    @Bind(R.id.act_good_list_iv_all)
    ImageView mIvPopAll;

    //mTvPopAll 孩子的TextView

    private List<String> mDatas;
    private UserComparReceiver mUserComparReceiver;


    public GoodListAct() {
        super(R.layout.act_good_list, R.string.title_activity_good_list);
    }

    public static final String P_KEY_WORD = "P_KEY_WORD";
    public static final String P_CLASSFICATION_ID = "P_CLASSFICATION_ID";
    public static final String P_FROM = "P_FROM";

    private static final int FROM_CLASSFICATION = 1;
    private static final int FROM_SEACH = 2;
    private int from = FROM_SEACH;


    /**
     * 从 搜索进入商品列表页
     *
     * @param context
     * @param searchWord
     */
    public static void startActivity(Context context, String searchWord) {
        Intent intent = new Intent(context, GoodListAct.class);
        intent.putExtra(P_KEY_WORD, searchWord);
        intent.putExtra(P_FROM, FROM_SEACH);
        context.startActivity(intent);
    }

    /**
     * 从分类进入商品列表页
     *
     * @param context
     * @param classficationId
     */
    public static void startActivityFromClassfication(Context context, String classficationId, String searchWord) {
        Intent intent = new Intent(context, GoodListAct.class);
        intent.putExtra(P_CLASSFICATION_ID, classficationId);
        intent.putExtra(P_KEY_WORD, searchWord);
        intent.putExtra(P_FROM, FROM_CLASSFICATION);
        context.startActivity(intent);
    }


    private String keyWork = "";
    private String classficationId = "";
    private long limit = 0;


    private static final String SORT_ALL = "weight"; //综合排序
    private static final String SORT_PRICE = "price"; //价格排序
    private static final String SORT_PERFORMANCE = "performance"; //性能排序
    private static final String SORT_PRAISE = "praise";//好评排序
    private static final String SORT_EASY = "usability";//易用排序
    private static final String SORT_SCORE = "expert";//打分排序

    private String sort = SORT_ALL;


    @Override
    public void initView() {

        Intent intent = getIntent();

        from = intent.getIntExtra(P_FROM, FROM_SEACH);


        switch (from) {

            case FROM_SEACH:
                if (intent.hasExtra(P_KEY_WORD)) {
                    keyWork = intent.getStringExtra(P_KEY_WORD);
                }
                layoutFeature.setVisibility(View.GONE);

                break;
            case FROM_CLASSFICATION:
                if (intent.hasExtra(P_CLASSFICATION_ID)) {
                    classficationId = intent.getStringExtra(P_CLASSFICATION_ID);
                }
                if (intent.hasExtra(P_KEY_WORD)) {
                    keyWork = intent.getStringExtra(P_KEY_WORD);
                }
                layoutFeature.setVisibility(View.GONE);
//                new FeatureAPI(this, classficationId);
                break;
        }

        initToolBarTitle(keyWork); //初始化 搜索框的内容
        initContrast(TYSP.getCompareGood().size()); // 初始化对比
        initListView();//初始化RecyclerView
        initPopupWindow();
//        initPopupWindowTrait();
        mListView.autoRefresh();
        goodAdapter.setListener(this);
        compareNumberReceiver();

    }


    private boolean firstLoading = true;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && firstLoading) {
            mListView.setSelection(0);
            mListView.autoRefresh();
            firstLoading = false;
        }
    }


    private void compareNumberReceiver() {
        mUserComparReceiver = new UserComparReceiver(this);
        UserComparReceiver.register(this, mUserComparReceiver);

    }

    PopupWindow mPop;
    PopupWindow mAutoPop;
    TextView mTvAll;
    TextView mTvPrice;
    TextView mTvProperty;
    TextView mTvGood;
    TextView mTvEasy;
    TextView mTvGrade;


    private String stateSort = null;


    //
    private void initItemView(View view) {
        /**
         * sort by 综合
         */
        mTvAll = (TextView) view.findViewById(R.id.item_good_list_all_sort);
        mTvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateSort = mTvAll.getText().toString();
                mTvPopAll.setText(stateSort);
                mPop.dismiss();
                sort = SORT_ALL;


                mListView.setSelection(0);
                mListView.autoRefresh();
            }
        });
        /**
         * sort by 价格
         */
        mTvPrice = (TextView) view.findViewById(R.id.item_good_list_price_sort);

        mTvPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateSort = mTvPrice.getText().toString();
                mTvPopAll.setText(stateSort);
                mPop.dismiss();
                sort = SORT_PRICE;

                mListView.setSelection(0);
                mListView.autoRefresh();
            }
        });


        /**
         * sort by 性能
         */
        mTvProperty = (TextView) view.findViewById(R.id.item_good_list_property_sort);
        mTvProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateSort = mTvProperty.getText().toString();
                mTvPopAll.setText(stateSort);
                mPop.dismiss();
                sort = SORT_PERFORMANCE;
                mListView.setSelection(0);
                mListView.autoRefresh();
            }
        });

        /**
         * sort by 好评
         */
        mTvGood = (TextView) view.findViewById(R.id.item_good_list_good_sort);
        mTvGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateSort = mTvGood.getText().toString();
                mTvPopAll.setText(stateSort);
                mPop.dismiss();
                sort = SORT_PRAISE;
                mListView.setSelection(0);
                mListView.autoRefresh();
            }
        });
        /**
         * sort by 易用
         */
        mTvEasy = (TextView) view.findViewById(R.id.item_good_list_easy_sort);
        mTvEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateSort = mTvEasy.getText().toString();
                mTvPopAll.setText(stateSort);
                mPop.dismiss();
                sort = SORT_EASY;
                mListView.setSelection(0);
                mListView.autoRefresh();
            }
        });
        /**
         * sort by 打分
         */
        mTvGrade = (TextView) view.findViewById(R.id.item_good_list_grade_sort);
        mTvGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateSort = mTvGrade.getText().toString();
                mTvPopAll.setText(stateSort);
                mPop.dismiss();
                sort = SORT_SCORE;
                mListView.setSelection(0);
                mListView.autoRefresh();
            }
        });


    }

    private void initItemViewTrait(View view, final List<Feature> featureList) {


        ListView lv = (ListView) view.findViewById(R.id.item_good_list_popup_trait_lv);

        GoodListPopupAdapter adapter = new GoodListPopupAdapter(this, featureList);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Feature feature = featureList.get(position);
                featureId = feature.getId();

                if (feature.getId() == null) {
                    mTvFeature.setText("特征筛选");
                } else {
                    mTvFeature.setText(feature.getName());
                }

                mAutoPop.dismiss();
                mListView.setSelection(0);
                mListView.autoRefresh();
            }
        });
    }


    @Override
    public void receiverCompar() {
        initContrast(TYSP.getCompareGood().size()); // 初始化对比
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserComparReceiver.unregister(this, mUserComparReceiver);
    }

    @Override
    public void AddCompare(Good mGood) {
        List<GoodDetail> goodList = TYSP.getCompareGood();
        for (GoodDetail detail : goodList) {

            if (detail.getProduct().getId().equals(mGood.getId())) {
                showTip("此商品已添加了");
                return;
            }
        }
        new GoodDetailAPI(this, mGood.getId());
    }

    @Override
    public void apiGoodDetailAPISuccess(GoodDetail goodDetail) {
        TYSP.addCompareGood(goodDetail);
        initContrast(TYSP.getCompareGood().size()); // 初始化对比
    }

    @Override
    public void apiGoodDetailAPIFailure(long code, String msg) {
        showTip(msg);
    }

    private class ItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {

//            mTvAuth.setTextColor(ResUtils.getColor(R.color.text_gray_deep));
            mTvFeature.setTextColor(ResUtils.getColor(R.color.text_gray_deep));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
//            GoodDetailAct.startActivity(GoodListAct.this, "10174");
        }

    }


    public void getGoodDetail(View view) {
        GoodDetailAct.startActivity(this, "10174");
    }


    public String getData(Intent in) {
        mExtras = in.getExtras();
        if (mExtras != null) {
            return mExtras.getString("searchWord");
        } else {
            return null;
        }
    }


    //获取返回值部分的代码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String show = getData(data);
                //让textView显示取出来的数据
                if (show != null) {
                    textTitle.setText(show);
                } else {
                    textTitle.setText("nothing");
                }
            }
        }
    }


    /**
     * init XList
     */
    private void initListView() {
        mListView.setAdapter(goodAdapter);
        mListView.setPullRefreshEnable(true);
        mListView.setAutoLoadEnable(true);
        mListView.setPullLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(TimeUtils.getTime());
    }


    @Bind(R.id.act_good_list_ll_feature)
    LinearLayout layoutPopFeature;


    /**
     * init 排序弹出框
     */
    private void initPopupWindow() {

        LayoutInflater inflater = LayoutInflater.from(this);
        // 引入窗口配置文件
        final View view = inflater.inflate(R.layout.item_good_list_popup, null);
        initItemView(view);
        LinearLayout llAll = (LinearLayout) findViewById(R.id.act_good_list_ll_all_sort);
        int width = llAll.getLayoutParams().width;

        // 创建PopupWindow对象
        mPop = new PopupWindow(view, width, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        mPop.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        mPop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        mPop.setFocusable(true);
        mPop.setAnimationStyle(R.style.PopupAnimation);

        llAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mPop.isShowing()) {
                    // 隐藏窗口，如果设置了点击窗口外小时即不需要此方式隐藏
                    mPop.dismiss();
                } else {
                    // 显示窗口
                    mPop.showAsDropDown(v);
                    mTvPopAll.setTextColor(ResUtils.getColor(R.color.text_green));
                    mIvPopAll.setImageResource(R.drawable.ic_drop_down_2x);

                    mIvPopFaeture.setImageResource(R.drawable.ic_drop_down1_2x);
                    mTvFeature.setTextColor(ResUtils.getColor(R.color.text_gray_deep));

                }
            }
        });

    }

    /**
     * init 排序弹出框
     */
    private void initPopupWindowTrait(List<Feature> featureList) {
        LayoutInflater inflater = LayoutInflater.from(this);
        // 引入窗口配置文件
        View mView = inflater.inflate(R.layout.item_good_list_popup_trait, null);

        initItemViewTrait(mView, featureList);

        LinearLayout llAll = (LinearLayout) findViewById(R.id.act_good_list_ll_feature);
        int width = llAll.getLayoutParams().width;

        // 创建PopupWindow对象
        mAutoPop = new PopupWindow(mView, width, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        mAutoPop.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        mAutoPop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        mAutoPop.setFocusable(true);
        mAutoPop.setAnimationStyle(R.style.PopupAnimation);

        llAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mAutoPop.isShowing()) {
                    // 隐藏窗口，如果设置了点击窗口外小时即不需要此方式隐藏
                    mAutoPop.dismiss();
                } else {
                    // 显示窗口
                    mAutoPop.showAsDropDown(v);
                    mTvPopAll.setTextColor(ResUtils.getColor(R.color.text_gray_deep));
                    mIvPopAll.setImageResource(R.drawable.ic_drop_down1_2x);

                    mIvPopFaeture.setImageResource(R.drawable.ic_drop_down_2x);
                    mTvFeature.setTextColor(ResUtils.getColor(R.color.text_green));
                }
            }
        });
    }


    /**
     * init 搜索框的内容
     *
     * @param title
     */
    private void initToolBarTitle(String title) {
        textTitle.setText(title);
    }


    @Bind(R.id.act_good_list_contrast)
    TextView textContrast;

    /**
     * init 对比按钮
     *
     * @param num
     */
    private void initContrast(long num) {
        String numStr = ResUtils.getString(R.string.act_good_list_text_contrast_span, num + "");
        String str = ResUtils.getString(R.string.act_good_list_text_contrast) + numStr;
        textContrast.setText(SpanStringUtils.getHighLightText(ResUtils.getColor(R.color.colorPrimary), str, numStr));
        textContrast.setClickable(true);
        textContrast.setFocusable(true);
    }

    @OnClick(R.id.act_good_list_contrast)
    public void toContrastAct() {
        CompareGoodAct.startActivity(this);
    }


    @Bind(R.id.act_good_list_layout_feature)
    LinearLayout layoutFeature;


    /**
     * click 搜索框
     *
     * @param view
     */
    @OnClick(R.id.act_good_list_search_rl)
    public void showSearch(View view) {
        Intent in = new Intent();
        in.setClass(this, HomeSearchAct.class);
        this.startActivityForResult(in, 1);
    }

    @Override
    public void onLoadMore() {

        switch (from) {

            case FROM_CLASSFICATION:
                new GoodListByClassficationAPI(this, classficationId, featureId, sort, limit);
                break;

            case FROM_SEACH:
                new GoodListBySearchAPI(this, keyWork, sort, limit);
                break;
        }

    }

    @Override
    public void onRefresh() {
        limit = 0;
        mListView.setPullLoadEnable(false);
        switch (from) {

            case FROM_CLASSFICATION:
                new GoodListByClassficationAPI(this, classficationId, featureId, sort, limit);
                break;

            case FROM_SEACH:
                new GoodListBySearchAPI(this, keyWork, sort, limit);
                break;
        }
    }

    private void onLoad() {

        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(TimeUtils.getTime());
    }

    /**
     * 通过分类获取商品列表-begin---------------------------------------------------------------------------
     */
    @Override
    public void apiGoodListByClassficationFailure(long code, String msg) {
        tipView.showNoData();
        showTip(msg);
        onLoad();
    }

    private boolean flag = true;//是否第一次进来

    @Override
    public void apiGoodListByClassficationSuccess(List<Good> goodList) {

        if (goodList.size() == 0 && limit == 0) {
            if (flag) {
                tipView.showNoData();
            } else {
                tipView.hide();
            }
            onLoad();
            return;
        }
        tipView.hide();

        goodAdapter.getData().addAll((ArrayList) goodList);
        goodAdapter.setData(goodList);
        goodAdapter.notifyDataSetChanged();


        mListView.setPullLoadEnable(true);
        onLoad();
        flag = false;
    }

    /**
     * 通过分类获取商品列表-end---------------------------------------------------------------------------
     */

    @Override
    public void apiGoodListBySearchFailure(long code, String msg) {
        showTip(msg);
        onLoad();
    }

    @Override
    public void apiGoodListBySearchSuccess(List<Good> goodList) {

        if (goodList == null) {
            onLoad();
            return;
        }

        if (goodList.size() == 0 && limit == 0) {
            tipView.showNoData();
            onLoad();
            return;
        }

        tipView.hide();


        goodAdapter.setData(goodList);
        goodAdapter.notifyDataSetChanged();

        mListView.setPullLoadEnable(true);
        onLoad();
    }


    private String featureId = null;

    @Override
    public void apiFeatureFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiFeatureSuccess(List<Feature> featureList) {
        List<Feature> flsit = new ArrayList<>();
        Feature feature = new Feature();
        feature.setName("无");
        feature.setId(null);
        flsit.add(feature);
        flsit.addAll(1, featureList);
        initPopupWindowTrait(flsit);
    }


    @Bind(R.id.tip_view)
    TipView tipView;


}
