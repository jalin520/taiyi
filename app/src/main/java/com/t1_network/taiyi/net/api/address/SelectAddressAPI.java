package com.t1_network.taiyi.net.api.address;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.AddressSelected;
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
public class SelectAddressAPI extends TYAPI {

    private SelectAddressAPIListener mListener;

    public SelectAddressAPI(SelectAddressAPIListener mListener) {
        this.mListener = mListener;
        addUserAuthorization();
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestSuccess(String data) {
        AddressSelected addressSelected = gson.fromJson(data, AddressSelected.class);
        mListener.apiSelectAddressSuccess(addressSelected);
    }

    @Override
    public void apiRequestError(long code, String msg) {
        mListener.apiSelectAddressFailure(code,msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "orderback/backaddr";
    }

    public interface SelectAddressAPIListener{
        void apiSelectAddressSuccess(AddressSelected addressSelected);
        void apiSelectAddressFailure(long code, String msg);

    }


}
