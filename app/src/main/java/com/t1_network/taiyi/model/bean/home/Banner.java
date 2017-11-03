package com.t1_network.taiyi.model.bean.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.t1_network.core.widget.BannerEntity;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/26 15:53
 * 修改记录:
 */
public class Banner extends BannerEntity implements Parcelable {

    @SerializedName("hyperlink")
    private String url;

    @SerializedName("url")
    private String image;

    @SerializedName("keyword")
    private String keyword;


    @Override
    public String getBannerImage() {
        return image;
    }

    @Override
    public String getBannerTitle() {
        return keyword;
    }

    @Override
    public String getBannerURL() {
        return url;
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
        dest.writeString(this.keyword);
    }

    public Banner() {
    }

    protected Banner(Parcel in) {
        this.url = in.readString();
        this.image = in.readString();
        this.keyword = in.readString();
    }

    public static final Parcelable.Creator<Banner> CREATOR = new Parcelable.Creator<Banner>() {
        public Banner createFromParcel(Parcel source) {
            return new Banner(source);
        }

        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };
}
