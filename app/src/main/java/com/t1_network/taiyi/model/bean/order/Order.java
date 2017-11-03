package com.t1_network.taiyi.model.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by David on 15/12/18.
 */
public class Order implements Parcelable {

    @SerializedName("id")
    private String id; //订单编号
    @SerializedName("total")
    private String total; //订单总额
    @SerializedName("feight")
    private String feight;//运费

    @SerializedName("orderdetail")
    private List<GoodInOrder> goodList;


    //0-待支付 1-已完成 2-待发货 3-待收货 4-已取消
    @SerializedName("status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeight() {
        return feight;
    }

    public void setFeight(String feight) {
        this.feight = feight;
    }

    public List<GoodInOrder> getGoodList() {
        return goodList;
    }

    public void setGoodList(List<GoodInOrder> goodList) {
        this.goodList = goodList;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.total);
        dest.writeString(this.feight);
        dest.writeTypedList(goodList);
        dest.writeString(this.status);
    }

    protected Order(Parcel in) {
        this.id = in.readString();
        this.total = in.readString();
        this.feight = in.readString();
        this.goodList = in.createTypedArrayList(GoodInOrder.CREATOR);
        this.status = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
