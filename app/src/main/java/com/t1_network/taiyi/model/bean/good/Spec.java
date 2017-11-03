package com.t1_network.taiyi.model.bean.good;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/6 15:29
 * 修改记录:
 */
public class Spec implements Parcelable {

    @SerializedName("type")
    private String key;

    @SerializedName("value")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.value);
    }

    public Spec() {
    }

    protected Spec(Parcel in) {
        this.key = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<Spec> CREATOR = new Parcelable.Creator<Spec>() {
        public Spec createFromParcel(Parcel source) {
            return new Spec(source);
        }

        public Spec[] newArray(int size) {
            return new Spec[size];
        }
    };
}
