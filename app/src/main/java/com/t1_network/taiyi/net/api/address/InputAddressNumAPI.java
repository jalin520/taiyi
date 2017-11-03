package com.t1_network.taiyi.net.api.address;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 2016/1/15.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class InputAddressNumAPI extends TYAPI {

    private OnInputAddressNumAPIListener mListener;

    public InputAddressNumAPI(OnInputAddressNumAPIListener mListener, String number, String deliverid, String remark) {
        this.mListener = mListener;
        addUserAuthorization();

        addParams("id", number);
        addParams("deliverid", deliverid);

        new TYHttp(this).request();
    }

    @Override
    public void apiRequestSuccess(String data) {
        mListener.apiInputAddressNumAPISuccess();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        mListener.apiInputAddressNumAPIFailure(code, msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "orderback/back";
    }


    public interface OnInputAddressNumAPIListener {
        void apiInputAddressNumAPISuccess();

        void apiInputAddressNumAPIFailure(long code, String msg);
    }

}
