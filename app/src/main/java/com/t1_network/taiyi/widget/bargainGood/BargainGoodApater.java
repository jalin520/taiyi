package com.t1_network.taiyi.widget.bargainGood;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.home.SpecialGood;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/28 14:12
 * 修改记录:
 */
public class BargainGoodApater extends PagerAdapter {

    private List<SpecialGood> goodList;

    private BargainGoodView bargainGoodView;

    private List<View> viewList;

    public BargainGoodApater(List<SpecialGood> goodList, BargainGoodView bargainGoodView) {
        this.goodList = goodList;
        this.bargainGoodView = bargainGoodView;
        initView(goodList);
    }

    private void initView(List<SpecialGood> goodList) {

        viewList = new ArrayList<View>();

        int goodListSize = goodList.size();
        int pageNum = getPageNum(goodListSize);

        for (int i = 0; i < pageNum; i++) {

            View view = bargainGoodView.getView();
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.c_item_bargain_good_layout);

            for (int j = 0; j < 3; j++) {
                int temp = i * 3 + j;

                if (temp >= goodListSize) {
                    continue;
                }
                layout.addView(bargainGoodView.getGoodView(goodList.get(temp)));
            }

            viewList.add(view);
        }
    }


    /**
     * 获取页数
     *
     * @return
     */
    private int getPageNum(int size) {
        int pageNum = goodList.size() / 3;

        if (goodList.size() % 3 != 0) {
            pageNum++;
        }
        return pageNum;
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

}
