package com.t1_network.taiyi.net.api.voucher;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 15/12/9.
 */
public class ExchangeVoucherAPI extends TYAPI {

    private ExchangeVoucherAPIListener listener;

    public ExchangeVoucherAPI(ExchangeVoucherAPIListener listener, String id) {
        this.listener = listener;

        addUserAuthorization();
        addParams("id", id);
        new TYHttp(this).request();

    }

    @Override
    public String getURL() {
        return Build.HOST + "voucher/exchange";
    }

    @Override
    public void apiRequestSuccess(String data) {
        listener.apiExchangeVoucherSuccess();
    }


    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiExchangeVoucherFailure(code, msg);
    }

    public interface ExchangeVoucherAPIListener {
        public void apiExchangeVoucherSuccess();

        public void apiExchangeVoucherFailure(long code, String msg);
    }
}
