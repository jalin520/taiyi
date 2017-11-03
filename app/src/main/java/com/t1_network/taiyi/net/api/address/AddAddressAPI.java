package com.t1_network.taiyi.net.api.address;

import android.text.TextUtils;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.Address;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 15/12/8.
 */
public class AddAddressAPI extends TYAPI {

    private AddAddressAPIListener listener;

    public AddAddressAPI(AddAddressAPIListener listener, Address address) {
        this.listener = listener;

        addUserAuthorization();


        addParams("name", address.getName());
        addParams("phone", address.getPhone());
        addParams("defaults", address.getDefaults());

        if (!TextUtils.isEmpty(address.getTag())) {
            addParams("tag", address.getTag());
        }

        addParams("province", address.getProvince());
        addParams("city", address.getCity());
        addParams("district", address.getDistrict());
        addParams("address", address.getAddress());

        new TYHttp(this).request();

    }

    @Override
    public String getURL() {
        return Build.HOST + "recvadd/add";
    }


    @Override
    public void apiRequestSuccess(String data) {
        listener.apiAddAddressSuccess();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiAddAddressFailure(code, msg);
    }

    public interface AddAddressAPIListener {

        public void apiAddAddressSuccess();

        public void apiAddAddressFailure(long code, String msg);

    }
}
