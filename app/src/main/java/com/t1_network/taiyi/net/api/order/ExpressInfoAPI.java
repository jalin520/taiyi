package com.t1_network.taiyi.net.api.order;

import com.google.gson.JsonSyntaxException;
import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.order.ExpressInfoBean;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 2016/1/22.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class ExpressInfoAPI extends TYAPI {

    private ExpressInfoAPIListener mListener;


    public interface ExpressInfoAPIListener {
        void apiExpressInfoSuccess(ExpressInfoBean infoBean);

        void apiExpressInfoFailure(long code, String msg);
    }

    public ExpressInfoAPI(ExpressInfoAPIListener listener, String id) {
        mListener = listener;
        addParams("id", id);
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestSuccess(String data) {
        try {
            ExpressInfoBean expressInfoBean = gson.fromJson(data, ExpressInfoBean.class);
            mListener.apiExpressInfoSuccess(expressInfoBean);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void apiRequestError(long code, String msg) {
        mListener.apiExpressInfoFailure(code, msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "kuaidi/info";
    }
}
