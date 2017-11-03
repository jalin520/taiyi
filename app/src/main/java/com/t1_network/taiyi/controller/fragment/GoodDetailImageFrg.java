package com.t1_network.taiyi.controller.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.t1_network.core.controller.BasicFrg;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.good.GoodDetailImage;
import com.t1_network.taiyi.model.factory.ImageOptionFactory;
import com.t1_network.taiyi.widget.TipView;

import java.util.List;

import butterknife.Bind;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/25 17:15
 * 修改记录:
 */
public class GoodDetailImageFrg extends BasicFrg {

    @Bind(R.id.frg_good_detail_image)
    LinearLayout layoutImage;

    @Bind(R.id.tip_view)
    TipView tipView;

    public GoodDetailImageFrg() {
        super(R.layout.frg_good_detail_image);
    }

    public static final String P_GOOD_DETAIL_IMAGE = "P_GOOD_DETAIL_IMAGE";

    @Override
    public void initView(View view) {

        Bundle bundle = getArguments();

        if (bundle == null) {
            return;
        }

        List<GoodDetailImage> goodDetailImageList = bundle.getParcelableArrayList(P_GOOD_DETAIL_IMAGE);

        if (goodDetailImageList == null) {
            return;
        }

        //展示图片
        for (int i = 0; i < goodDetailImageList.size(); i++) {

            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageLoader.displayImage(goodDetailImageList.get(i).getUrl(), imageView, ImageOptionFactory.getBannerOptions());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutImage.addView(imageView, lp);
        }
        if (goodDetailImageList.size() == 0) {
            tipView.showNoData();
        } else {
            tipView.hide();
        }

    }
}
