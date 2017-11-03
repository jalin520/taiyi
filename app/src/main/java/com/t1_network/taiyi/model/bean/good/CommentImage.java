package com.t1_network.taiyi.model.bean.good;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/28 15:59
 * 修改记录:
 */
public class CommentImage implements Parcelable {

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

    public CommentImage() {
    }

    protected CommentImage(Parcel in) {
        this.url = in.readString();
    }

    public static final Parcelable.Creator<CommentImage> CREATOR = new Parcelable.Creator<CommentImage>() {
        public CommentImage createFromParcel(Parcel source) {
            return new CommentImage(source);
        }

        public CommentImage[] newArray(int size) {
            return new CommentImage[size];
        }
    };
}
