package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.Address;
import com.t1_network.taiyi.model.bean.AddressList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * Created by David on 15/11/25.
 */
public class AddressAPI extends TYAPI {

    private AddressAPIListener listener;

    public AddressAPI(AddressAPIListener listener) {
        this.listener = listener;

        addUserAuthorization();
        new TYHttp(this).request();

    }

    @Override
    public String getURL() {
        return Build.HOST + "recvadd/list";
    }


    @Override
    public void apiRequestSuccess(String data) {

        AddressList addressList = gson.fromJson(data, AddressList.class);
        listener.apiAddressSuccess(addressList.getAddressList());

    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiAddressFailure(code, msg);
    }

    public interface AddressAPIListener {

        public void apiAddressSuccess(List<Address> addressList);

        public void apiAddressFailure(long code, String msg);

    }

}
