package com.t1_network.taiyi.model.bean.good;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/31 14:32
 * 修改记录:
 */
public class GoodList implements Parcelable {

    @SerializedName("result")
    private List<Good> goodList;

    @SerializedName("total")
    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<Good> getGoodList() {
        return goodList;
    }

    public void setGoodList(List<Good> goodList) {
        this.goodList = goodList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(goodList);
        dest.writeString(this.count);
    }

    public GoodList() {
    }

    protected GoodList(Parcel in) {
        this.goodList = in.createTypedArrayList(Good.CREATOR);
        this.count = in.readString();
    }

    public static final Creator<GoodList> CREATOR = new Creator<GoodList>() {
        public GoodList createFromParcel(Parcel source) {
            return new GoodList(source);
        }

        public GoodList[] newArray(int size) {
            return new GoodList[size];
        }
    };
}
