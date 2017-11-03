package com.t1_network.taiyi.net.api.good;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 2016/1/8.
 *
 * @author David
 */
public class AddConsultAPI extends TYAPI {

    private AddConsultListener mAddConsultListener;


    public AddConsultAPI(AddConsultListener addConsultListener, String id, String content) {
        this.mAddConsultListener = addConsultListener;

        addUserAuthorization();

        addParams("productid", id);
        addParams("content", content);

        new TYHttp(this).request();
    }






    @Override
    public String getURL() {
        return Build.HOST + "consultation/consult";
    }

    @Override
    public void apiRequestSuccess(String data) {
        mAddConsultListener.addConsultSuccess();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        mAddConsultListener.addConsultError(code, msg);
    }


    public interface AddConsultListener {
        void addConsultSuccess();

        void addConsultError(long code, String msg);

    }


}
