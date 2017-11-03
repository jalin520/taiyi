package com.t1_network.taiyi.net.api.order;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 2016/1/13.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class AfterCancleApplyAPI extends TYAPI {

    private AfterCancleApplyAPIListener apiListener;

    public AfterCancleApplyAPI(AfterCancleApplyAPIListener apiListener, String afterSaleId) {
        this.apiListener = apiListener;
        addParams("id", afterSaleId);
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestSuccess(String data) {
        apiListener.apiAfterCancleApplySuccess();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        apiListener.apiAfterCancleApplyFailure(code, msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "orderback/cancle";
    }


    public interface AfterCancleApplyAPIListener {

        void apiAfterCancleApplySuccess();

        void apiAfterCancleApplyFailure(long code, String msg);
    }


}
