package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.core.utils.share.Share;
import com.t1_network.core.utils.share.ShareUtil;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.good.GoodDetail;

import butterknife.OnClick;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareAct extends BasicAct {


    public ShareAct() {
        super(R.layout.activity_share, NO_TITLE, false, TOOLBAR_TYPE_NO_TOOLBAR);
    }


    private GoodDetail goodDetail;

    @Override
    public void initView() {
        Intent intent = getIntent();

        goodDetail = intent.getParcelableExtra(P_GOOD_DETAIL);


    }


    public static final String P_GOOD_DETAIL = "P_GOOD_DETAIL";

    public static void startActivity(Context context, GoodDetail goodDetail) {
        Intent intent = new Intent(context, ShareAct.class);
        intent.putExtra(P_GOOD_DETAIL, goodDetail);
        context.startActivity(intent);
    }

    @OnClick(R.id.act_share_ll_friend_share)
    public void onClickFriendShare(View v) {


        Share share = new Share();
        share.setTitle(goodDetail.getProduct().getName());
        share.setImageUrl(goodDetail.getProduct().getGoodImageList().get(0).getUrl());
        share.setText(goodDetail.getProduct().getSummary());
        share.setUrl("http://tyijian.com/product?id=" + goodDetail.getProduct().getId());

        ShareUtil.share(WechatMoments.NAME, this, share);
    }

    @OnClick(R.id.act_share_ll_wechat_share)
    public void onClickWechatShare(View v) {


        Share share = new Share();
        share.setTitle(goodDetail.getProduct().getName());
        share.setImageUrl(goodDetail.getProduct().getGoodImageList().get(0).getUrl());
        share.setText(goodDetail.getProduct().getSummary());
        share.setUrl("http://tyijian.com/product?id=" + goodDetail.getProduct().getId());

        ShareUtil.share(Wechat.NAME, this, share);
    }

    @OnClick(R.id.act_share_ll_qzone_share)
    public void onClickQzoneShare(View v) {
//        TipUtils.toast("QQ空间");
//        Share share = new Share();
//        share.setTitle(goodDetail.getProduct().getName());
//        share.setImageUrl(goodDetail.getProduct().getGoodImageList().get(0).getUrl());
//        share.setText(goodDetail.getProduct().getSummary());
//        share.setUrl("http://tyijian.com/product?id=" + goodDetail.getProduct().getId());
//
//        ShareUtil.share(QZone.NAME, this, share);
    }

    @OnClick(R.id.act_share_ll_qq_share)
    public void onClickQQhare(View v) {
        TipUtils.toast("QQ");
        Share share = new Share();
        share.setTitle(goodDetail.getProduct().getName());
        share.setImageUrl(goodDetail.getProduct().getGoodImageList().get(0).getUrl());
        share.setText(goodDetail.getProduct().getSummary());
        share.setUrl("http://tyijian.com/product?id=" + goodDetail.getProduct().getId());

        ShareUtil.share(QQ.NAME, this, share);

    }

    @OnClick(R.id.act_share_ll_weibo_share)
    public void onClickWeibohare(View v) {
        TipUtils.toast("微博");
    }

    @OnClick(R.id.act_share_ll_photo_share)
    public void onClickPhotohare(View v) {
        TipUtils.toast("相册");
    }

    @OnClick(R.id.share_iv_finish)
    public void onClickFinish(View v) {
        finish();
    }

}
