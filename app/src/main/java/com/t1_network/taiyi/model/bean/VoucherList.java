package com.t1_network.taiyi.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 功能：代币数组对象，包含了一个Consumer（为了获得代币总数）
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-11-2
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class VoucherList implements Parcelable {

    @SerializedName("result")
    private List<Voucher> voucherList;

    public List<Voucher> getVoucherList() {
        return voucherList;
    }

    public void setVoucherList(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(voucherList);
    }

    public VoucherList() {
    }

    protected VoucherList(Parcel in) {
        this.voucherList = in.createTypedArrayList(Voucher.CREATOR);
    }

    public static final Parcelable.Creator<VoucherList> CREATOR = new Parcelable.Creator<VoucherList>() {
        public VoucherList createFromParcel(Parcel source) {
            return new VoucherList(source);
        }

        public VoucherList[] newArray(int size) {
            return new VoucherList[size];
        }
    };
}
