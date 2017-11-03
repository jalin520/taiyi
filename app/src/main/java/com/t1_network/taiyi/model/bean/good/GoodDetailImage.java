package com.t1_network.taiyi.model.bean.good;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 功能:商品详情页-详情图片
 * 创建者: David
 * 创建日期: 15/12/28 11:36
 * 修改记录:
 */
public class GoodDetailImage implements Parcelable {


    @SerializedName("url")
    private String url;

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
    }

    public GoodDetailImage() {
    }

    protected GoodDetailImage(Parcel in) {
        this.url = in.readString();
    }

    public static final Parcelable.Creator<GoodDetailImage> CREATOR = new Parcelable.Creator<GoodDetailImage>() {
        public GoodDetailImage createFromParcel(Parcel source) {
            return new GoodDetailImage(source);
        }

        public GoodDetailImage[] newArray(int size) {
            return new GoodDetailImage[size];
        }
    };
}
