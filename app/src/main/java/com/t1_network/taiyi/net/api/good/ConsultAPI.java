package com.t1_network.taiyi.net.api.good;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.good.Consult;
import com.t1_network.taiyi.model.bean.good.ConsultList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/29 10:39
 * 修改记录:
 */
public class ConsultAPI extends TYAPI {


    private ConsultAPIListener listener;

    public ConsultAPI(ConsultAPIListener listener, String id, long limit) {
        this.listener = listener;

        addParams("id", id);
        addParams("pagenum", limit);
        addParams("pagesize", 10);

        new TYHttp(this).request();

    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiCousultFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {

        ConsultList consultList = gson.fromJson(data, ConsultList.class);

        listener.apiCousultSuccess(consultList.getConsultList());
    }

    @Override
    public String getURL() {
        return Build.HOST + "home/consultationlist";
    }

    public interface ConsultAPIListener {

        public void apiCousultSuccess(List<Consult> consultList);

        public void apiCousultFailure(long code, String msg);

    }

}
