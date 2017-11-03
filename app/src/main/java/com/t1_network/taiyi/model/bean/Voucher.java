package com.t1_network.taiyi.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 功能：代币对象
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-11-16
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class Voucher implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("orderid")
    private String orderId;
    @SerializedName("inout")
    private String inOut;
    @SerializedName("count")
    private String count;
    @SerializedName("content")
    private String content;
    @SerializedName("sysupdate")
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static Creator<Voucher> getCREATOR() {
        return CREATOR;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getInOut() {
        return inOut;
    }

    public void setInOut(String inOut) {
        this.inOut = inOut;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Voucher() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.orderId);
        dest.writeString(this.inOut);
        dest.writeString(this.count);
        dest.writeString(this.content);
        dest.writeString(this.time);
    }

    protected Voucher(Parcel in) {
        this.id = in.readString();
        this.orderId = in.readString();
        this.inOut = in.readString();
        this.count = in.readString();
        this.content = in.readString();
        this.time = in.readString();
    }

    public static final Creator<Voucher> CREATOR = new Creator<Voucher>() {
        public Voucher createFromParcel(Parcel source) {
            return new Voucher(source);
        }

        public Voucher[] newArray(int size) {
            return new Voucher[size];
        }
    };
}
