package com.t1_network.taiyi.net.api.address;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 15/12/8.
 */
public class DelAddressAPI extends TYAPI {

    private DelAddressAPIListener listener;

    public DelAddressAPI(DelAddressAPIListener listener, String id) {
        this.listener = listener;

        addUserAuthorization();
        addParams("id", id);
        new TYHttp(this).request();

    }

    @Override
    public String getURL() {
        return Build.HOST + "recvadd/delete";
    }

    @Override
    public void apiRequestSuccess(String data) {
        listener.apiDelAddressSuccess();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiDelAddressFailure(code, msg);
    }

    public interface DelAddressAPIListener {
        public void apiDelAddressSuccess();

        public void apiDelAddressFailure(long code, String msg);
    }

}
