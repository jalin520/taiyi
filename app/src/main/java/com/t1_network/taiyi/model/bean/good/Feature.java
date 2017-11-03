package com.t1_network.taiyi.model.bean.good;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/20 15:18
 * 修改记录:
 */
public class Feature implements Parcelable {

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    public Feature() {
    }

    protected Feature(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Feature> CREATOR = new Parcelable.Creator<Feature>() {
        public Feature createFromParcel(Parcel source) {
            return new Feature(source);
        }

        public Feature[] newArray(int size) {
            return new Feature[size];
        }
    };
}
