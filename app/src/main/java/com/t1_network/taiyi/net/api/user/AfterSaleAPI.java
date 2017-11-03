package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.AfterSale;
import com.t1_network.taiyi.model.bean.AfterSaleList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * Created by David on 2016/1/11.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class AfterSaleAPI extends TYAPI {

    private AfterSaleListener mListener;

    public AfterSaleAPI(AfterSaleListener listener,int pagenum,int pagesize) {
        this.mListener = listener;

        addParams("pagenum",pagenum);
        addParams("pagesize",pagesize);

        addUserAuthorization();
        new TYHttp(this).request();

    }

    @Override
    public void apiRequestSuccess(String data) {
        AfterSaleList afterSaleList = gson.fromJson(data, AfterSaleList.class);
        mListener.apiAfterSaleSuccess(afterSaleList.getSaleList());
    }

    @Override
    public void apiRequestError(long code, String msg) {
        mListener.apiAfterSaleFailure(code, msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "orderback/listall";
    }

    public interface AfterSaleListener {
        void apiAfterSaleSuccess(List<AfterSale> afterSaleList);

        void apiAfterSaleFailure(long code, String msg);
    }

}
