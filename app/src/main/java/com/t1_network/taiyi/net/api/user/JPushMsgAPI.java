package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.user.JPushMsg;
import com.t1_network.taiyi.model.bean.user.JPushMsgList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/25 13:28
 * 修改记录:
 */
public class JPushMsgAPI extends TYAPI {


    private JPushMsgAPIListener listener;

    public JPushMsgAPI(JPushMsgAPIListener listener, long limit) {
        this(listener, limit, 10);
    }

    public JPushMsgAPI(JPushMsgAPIListener listener, long limit, long size) {
        this.listener = listener;
        addUserAuthorization();

        addParams("pagenum", limit + "");
        addParams("pagesize", size + "");

        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiJPushMsgFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        JPushMsgList msgList = gson.fromJson(data, JPushMsgList.class);
        listener.apiJPushMsgSuccess(msgList.getMsgList());
    }

    @Override
    public String getURL() {
        return Build.HOST + "jpush/list";
    }

    public interface JPushMsgAPIListener {

        public void apiJPushMsgSuccess(List<JPushMsg> jPushMsgList);

        public void apiJPushMsgFailure(long code, String msg);

    }


}
