package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.good.Good;
import com.t1_network.taiyi.model.bean.good.GoodList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * Created by David on 2016/1/17.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class FootPrintsAPI extends TYAPI {

    private FootPrintsAPIListener mListener;

    public FootPrintsAPI(FootPrintsAPIListener mListener, long limit) {
        this.mListener = mListener;
        addUserAuthorization();
        addParams("pagenum", limit + "");
        addParams("pagesize", "10");
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestSuccess(String data) {
        GoodList goodList = gson.fromJson(data, GoodList.class);
        mListener.apiFootPrintsAPISuccess(goodList.getGoodList());
    }

    @Override
    public void apiRequestError(long code, String msg) {
        mListener.apiFootPrintsAPIFailure(code,msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "collection/listbrowse";
    }

    public interface FootPrintsAPIListener {
        void apiFootPrintsAPISuccess(List<Good> list);

        void apiFootPrintsAPIFailure(long code, String msg);
    }

}
