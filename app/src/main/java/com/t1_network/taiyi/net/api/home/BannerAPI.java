package com.t1_network.taiyi.net.api.home;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.home.Banner;
import com.t1_network.taiyi.model.bean.home.BannerList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/26 15:42
 * 修改记录:
 */
public class BannerAPI extends TYAPI {

    private BannerAPIListener listener;

    public BannerAPI(BannerAPIListener listener) {
        this.listener = listener;

        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiBannerFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {

        BannerList bannerList = gson.fromJson(data, BannerList.class);

        listener.apiBannerSuccess(bannerList.getBannerList());

    }

    @Override
    public String getURL() {
        return Build.HOST + "homepage/banner";
    }

    public interface BannerAPIListener {

        public void apiBannerSuccess(List<Banner> bannerList);

        public void apiBannerFailure(long code, String msg);

    }
}
