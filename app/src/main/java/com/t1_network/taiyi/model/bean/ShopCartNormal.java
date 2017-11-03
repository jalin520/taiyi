package com.t1_network.taiyi.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by David on 15/12/7.
 */
public class ShopCartNormal implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    private String image = "";
    @SerializedName("count")
    private String num;
    @SerializedName("oldprice")
    private String marketPrice;
    @SerializedName("price")
    private String price;

    private boolean isSelect = true;


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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.num);
        dest.writeString(this.marketPrice);
        dest.writeString(this.price);
        dest.writeByte(isSelect ? (byte) 1 : (byte) 0);
    }

    public ShopCartNormal() {
    }

    protected ShopCartNormal(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.image = in.readString();
        this.num = in.readString();
        this.marketPrice = in.readString();
        this.price = in.readString();
        this.isSelect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ShopCartNormal> CREATOR = new Parcelable.Creator<ShopCartNormal>() {
        public ShopCartNormal createFromParcel(Parcel source) {
            return new ShopCartNormal(source);
        }

        public ShopCartNormal[] newArray(int size) {
            return new ShopCartNormal[size];
        }
    };
}
