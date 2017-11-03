package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.good.Good;
import com.t1_network.taiyi.model.bean.good.GoodList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * Created by David on 2016/1/4.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class CollectionAPI extends TYAPI {


    private CollectionByTYListener mListener;

    public CollectionAPI(CollectionByTYListener listener, long limit) {
        this.mListener = listener;
        addUserAuthorization();

        addParams("pagenum", limit + "");
        addParams("pagesize", "10");

        new TYHttp(this).request();

    }

    @Override
    public void apiRequestSuccess(String data) {
        GoodList goodList = gson.fromJson(data, GoodList.class);
        mListener.apiCollectionBySuccess(goodList.getGoodList());
    }

    @Override
    public void apiRequestError(long code, String msg) {
        mListener.apiCollectionByFailure(code, msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "collection/listcollect";
    }

    public interface CollectionByTYListener {
        void apiCollectionBySuccess(List<Good> goodList);

        void apiCollectionByFailure(long code, String msg);
    }

}
