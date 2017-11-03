package com.t1_network.taiyi.net.api.address;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.Address;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 15/12/8.
 */
public class UpdateAddressAPI extends TYAPI {

    private UpdateAddressAPIListener listener;

    public UpdateAddressAPI(UpdateAddressAPIListener listener, Address address) {
        this.listener = listener;

        addUserAuthorization();

        addParams("id", address.getId());
        addParams("name", address.getName());
        addParams("phone", address.getPhone());
        addParams("defaults", address.getDefaults());
        addParams("tag", address.getTag());
        addParams("province", address.getProvince());
        addParams("city", address.getCity());
        addParams("district", address.getDistrict());
        addParams("address", address.getAddress());

        new TYHttp(this).request();
    }

    @Override
    public String getURL() {
        return Build.HOST + "recvadd/edit";
    }

    @Override
    public void apiRequestSuccess(String data) {
        listener.apiUpdateAddressSuccess();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiUpdateAddressFailure(code, msg);
    }

    public interface UpdateAddressAPIListener {
        public void apiUpdateAddressSuccess();

        public void apiUpdateAddressFailure(long code, String msg);
    }
}
