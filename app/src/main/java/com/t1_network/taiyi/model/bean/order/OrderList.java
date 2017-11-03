package com.t1_network.taiyi.model.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by David on 15/12/21.
 */
public class OrderList implements Parcelable {

    @SerializedName("result")
    private List<Order> orderList;

    @SerializedName("total")
    private long count;

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(orderList);
        dest.writeLong(this.count);
    }

    public OrderList() {
    }

    protected OrderList(Parcel in) {
        this.orderList = in.createTypedArrayList(Order.CREATOR);
        this.count = in.readLong();
    }

    public static final Parcelable.Creator<OrderList> CREATOR = new Parcelable.Creator<OrderList>() {
        public OrderList createFromParcel(Parcel source) {
            return new OrderList(source);
        }

        public OrderList[] newArray(int size) {
            return new OrderList[size];
        }
    };
}
