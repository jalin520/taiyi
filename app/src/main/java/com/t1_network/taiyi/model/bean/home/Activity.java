package com.t1_network.taiyi.model.bean.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/27 15:45
 * 修改记录:
 */
public class Activity implements Parcelable {

    @SerializedName("hyperlink")
    private String url;

    @SerializedName("url")
    private String image;

    @SerializedName("productid")
    private String goodId;

    @SerializedName("keyword")
    private String keyword;

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.image);
        dest.writeString(this.goodId);
        dest.writeString(this.keyword);
    }

    public Activity() {
    }

    protected Activity(Parcel in) {
        this.url = in.readString();
        this.image = in.readString();
        this.goodId = in.readString();
        this.keyword = in.readString();
    }

    public static final Parcelable.Creator<Activity> CREATOR = new Parcelable.Creator<Activity>() {
        public Activity createFromParcel(Parcel source) {
            return new Activity(source);
        }

        public Activity[] newArray(int size) {
            return new Activity[size];
        }
    };
}
