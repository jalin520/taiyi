package com.t1_network.taiyi.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.markmao.pulltorefresh.widget.XScrollView;
import com.t1_network.core.controller.BasicFrg;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.good.GoodDetail;

import butterknife.Bind;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/2/15 17:05
 * 修改记录:
 */
public class GoodDetailInfoFrg extends BasicFrg {

    private GoodDetail goodDetail;

    @Bind(R.id.frg_good_detail_good_info_scroll)
    XScrollView xScrollView;

    private View contentView;

    public GoodDetailInfoFrg() {
        super(R.layout.frg_good_detail_good_info);
    }

    @Override
    public void initView(View view) {
        Bundle bundle = getArguments();
        initScrollView();
    }

    private void initScrollView() {
        contentView = LayoutInflater.from(context).inflate(R.layout.content_good_detail_good_info, null);
        xScrollView.setView(contentView);
    }

}
