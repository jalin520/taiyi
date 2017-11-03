package com.t1_network.taiyi.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by David on 15/11/25.
 */
public class AddressList {

    @SerializedName("total")
    private long total;

    @SerializedName("result")
    private List<Address> addressList;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
