package com.t1_network.taiyi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.activity.GoodDetailAct;
import com.t1_network.taiyi.model.bean.good.Good;
import com.t1_network.taiyi.model.factory.ImageOptionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 15/12/7.
 */
public class SupposeYouWantView extends LinearLayout {


    ViewPager viewPager;
    TextView textTitle;

    private List<Good> goodList = new ArrayList<Good>();
    private List<View> viewList = new ArrayList<View>();

    private Context context;

    private ImageLoader imageLoader;

    public SupposeYouWantView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;

        imageLoader = ImageLoader.getInstance();

        initView(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SupposeYouWantView);
        String title = ta.getString(R.styleable.SupposeYouWantView_SupposeYouWantViewTitle);
        textTitle.setText(title);

    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.c_suppose_you_want_view, this);
        viewPager = (ViewPager) view.findViewById(R.id.c_suppose_you_want_view_view_pager);
        textTitle = (TextView) view.findViewById(R.id.c_suppose_you_want_view_text_title);

        setVisibility(View.GONE);
    }


    public void setData(List<Good> goodList) {

        viewList.clear();

        int count = goodList.size();
        int index = count % 3;
        int pageNum = count / 3 + index == 0 ? 0 : 1;

        for (int i = 0; i < pageNum; i++) {
            View view = View.inflate(context, R.layout.item_shop_cart_view_suppose_you_want, null);

            LinearLayout layoutGood = (LinearLayout) view.findViewById(R.id.layout_good);

            for (int j = 0; j < 3; j++) {

                int k = i * 3 + j; //商品下标

                if (k >= count) {
                    break;
                }


                LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                ll.weight = 1;

                layoutGood.addView(initGoodItem(goodList.get(k)), ll);
            }

            viewList.add(view);
        }

        viewPager.setAdapter(new GoodPagerAdapter(viewList));
        setVisibility(View.VISIBLE);
    }

    /**
     * 根据商品生成item
     *
     * @param good
     * @return
     */
    private View initGoodItem(Good good) {

        View view = View.inflate(context, R.layout.item_shop_cart_view_suppose_you_want_item, null);

        ImageView image = (ImageView) view.findViewById(R.id.item_good_shop_cart_view_suppose_you_want_image_good);
        TextView textName = (TextView) view.findViewById(R.id.item_good_shop_cart_view_suppose_you_want_text_name);
        TextView textPrice = (TextView) view.findViewById(R.id.item_good_shop_cart_view_suppose_you_want_text_price);


        String url = "";

        if (good != null) {

            if (good.getImageList() != null && !good.getImageList().isEmpty()) {
                url = good.getImageList().get(0).getUrl();
            }

            imageLoader.displayImage(url, image, ImageOptionFactory.getGoodOptions());
            textName.setText(good.getName());
            textPrice.setText(ResUtils.getString(R.string.money, good.getPrice()));

        }

        view.setOnClickListener(new OnClickGoodListener(good));

        return view;
    }

    public class OnClickGoodListener implements OnClickListener {

        private Good good;

        public OnClickGoodListener(Good good) {
            this.good = good;
        }

        @Override
        public void onClick(View v) {
            GoodDetailAct.startActivity(context, good.getId());
        }
    }


    private class GoodPagerAdapter extends PagerAdapter {
        private List<View> viewList;

        public GoodPagerAdapter(List<View> viewList) {
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
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }
    }


}
