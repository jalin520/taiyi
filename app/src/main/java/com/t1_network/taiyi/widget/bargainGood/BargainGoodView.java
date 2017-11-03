package com.t1_network.taiyi.widget.bargainGood;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.UIUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.activity.GoodDetailAct;
import com.t1_network.taiyi.model.bean.home.SpecialGood;
import com.t1_network.taiyi.model.factory.ImageOptionFactory;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/28 13:56
 * 修改记录:
 */
public class BargainGoodView extends RelativeLayout {

    private Context context;
    private ViewPager viewPager;

    private ImageLoader imageLoader;


    public BargainGoodView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BargainGoodView(Context context) {
        super(context);
        init(context);
    }


    private void init(Context context) {
        imageLoader = ImageLoader.getInstance();
        this.context = context;
        initView();


    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.c_bargain_good, this);
        viewPager = (ViewPager) view.findViewById(R.id.c_bargain_good_view_pager);
    }

    public void setData(List<SpecialGood> goodList) {
        BargainGoodApater adapter = new BargainGoodApater(goodList, this);
        viewPager.setAdapter(adapter);

        task.start();
    }

    public View getGoodView(SpecialGood good) {
        View view = LayoutInflater.from(context).inflate(R.layout.c_item_bargain_good, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.item_bargin_good_image);
        TextView textName = (TextView) view.findViewById(R.id.item_bargin_good_name);
        TextView textPrice = (TextView) view.findViewById(R.id.item_bargin_good_price);

        textName.setText(good.getName());
        textPrice.setText(ResUtils.getString(R.string.money, good.getPrice()));

        imageLoader.displayImage(good.getImage(), imageView, ImageOptionFactory.getGoodOptions());

        view.setOnClickListener(new OnGoodClickListener(good));

        return view;
    }


    public class OnGoodClickListener implements OnClickListener {

        private SpecialGood good;

        public OnGoodClickListener(SpecialGood good) {
            this.good = good;
        }

        @Override
        public void onClick(View v) {
            GoodDetailAct.startActivity(context, good.getId());
        }
    }


    public View getView() {
        View view = LayoutInflater.from(context).inflate(R.layout.c_bargin_good_view, null);
        return view;
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
            int currentItem = viewPager.getCurrentItem() + 1;

            int count = viewPager.getChildCount();

            if (currentItem >= count) {
                currentItem = 0;
            }

            viewPager.setCurrentItem(currentItem);
            start();
        }
    }

}
