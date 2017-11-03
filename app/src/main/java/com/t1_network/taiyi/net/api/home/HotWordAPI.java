package com.t1_network.taiyi.net.api.home;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.home.HotWord;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/25 10:20
 * 修改记录:
 */
public class HotWordAPI extends TYAPI {

    private HotWordAPIListener listener;

    public HotWordAPI(HotWordAPIListener listener) {
        this.listener = listener;

        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiHotWordFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {

        HotWord.HotWordList hotWordList = gson.fromJson(data, HotWord.HotWordList.class);
        listener.apiHotWordSuccess(hotWordList.getHotWordList());

    }

    @Override
    public String getURL() {
        return Build.HOST + "home/hotword";
    }

    public interface HotWordAPIListener {
        public void apiHotWordSuccess(List<HotWord> hotWordList);

        public void apiHotWordFailure(long code, String msg);

    }

}
