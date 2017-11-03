package com.t1_network.taiyi.net.api.address;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.Address;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by David on 15/12/18.
 */
public class DefaultAddressAPI extends TYAPI {

    private DefaultAddressAPIListener listener;

    public DefaultAddressAPI(DefaultAddressAPIListener listener) {
        this.listener = listener;
        addUserAuthorization();
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestSuccess(String data) {

        try {
            JSONObject json = new JSONObject(data);
            Address address = gson.fromJson(json.get("recvadd").toString(), Address.class);
            listener.apiDefaultAddressSuccess(address);

        } catch (JSONException e) {
            e.printStackTrace();
            //不应该是报解析错,应该调用默认的地址
            listener.apiDefaultAddressSuccess(null);
        }
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiDefaultAddressFailure(code, msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "recvadd/defaults";
    }


    public interface DefaultAddressAPIListener {

        public void apiDefaultAddressSuccess(Address address);

        public void apiDefaultAddressFailure(long code, String msg);

    }


}
