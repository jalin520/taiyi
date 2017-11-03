package com.t1_network.taiyi.model.bean.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/27 17:25
 * 修改记录:
 */
public class SpecialGood implements Parcelable {


    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private String price;

    @SerializedName("productpic")
    private String image;

    @SerializedName("id")
    private String id;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SpecialGood() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.image);
        dest.writeString(this.id);
    }

    protected SpecialGood(Parcel in) {
        this.name = in.readString();
        this.price = in.readString();
        this.image = in.readString();
        this.id = in.readString();
    }

    public static final Creator<SpecialGood> CREATOR = new Creator<SpecialGood>() {
        public SpecialGood createFromParcel(Parcel source) {
            return new SpecialGood(source);
        }

        public SpecialGood[] newArray(int size) {
            return new SpecialGood[size];
        }
    };
}
