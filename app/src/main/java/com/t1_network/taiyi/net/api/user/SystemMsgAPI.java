package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.user.SystemMsg;
import com.t1_network.taiyi.model.bean.user.SystemMsgList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/25 14:45
 * 修改记录:
 */
public class SystemMsgAPI extends TYAPI {


    SystemMsgAPIListener listener;

    public SystemMsgAPI(SystemMsgAPIListener listener, long limit) {
        this(listener, limit, 10);
    }

    public SystemMsgAPI(SystemMsgAPIListener listener, long limit, long size) {
        this.listener = listener;
        addUserAuthorization();
        addParams("pagenum", limit + "");
        addParams("pagesize", size + "");
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiSystemMsgFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        SystemMsgList msgList = gson.fromJson(data, SystemMsgList.class);
        listener.apiSystemMsgSuccess(msgList.getMsgList());
    }

    @Override
    public String getURL() {
        return Build.HOST + "consumer/readsysmsg";
    }

    public interface SystemMsgAPIListener {

        public void apiSystemMsgSuccess(List<SystemMsg> systemMsgLists);

        public void apiSystemMsgFailure(long code, String msg);


    }

}
