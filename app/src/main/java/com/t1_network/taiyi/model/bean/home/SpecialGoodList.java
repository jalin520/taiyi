package com.t1_network.taiyi.model.bean.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/27 17:29
 * 修改记录:
 */
public class SpecialGoodList implements Parcelable {


    @SerializedName("result")
    private List<SpecialGood> goodList;

    public List<SpecialGood> getGoodList() {
        return goodList;
    }

    public void setGoodList(List<SpecialGood> goodList) {
        this.goodList = goodList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(goodList);
    }

    public SpecialGoodList() {
    }

    protected SpecialGoodList(Parcel in) {
        this.goodList = in.createTypedArrayList(SpecialGood.CREATOR);
    }

    public static final Parcelable.Creator<SpecialGoodList> CREATOR = new Parcelable.Creator<SpecialGoodList>() {
        public SpecialGoodList createFromParcel(Parcel source) {
            return new SpecialGoodList(source);
        }

        public SpecialGoodList[] newArray(int size) {
            return new SpecialGoodList[size];
        }
    };
}
