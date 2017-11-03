package com.t1_network.taiyi.model.bean.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/26 15:55
 * 修改记录:
 */
public class BannerList implements Parcelable {

    @SerializedName("result")
    private List<Banner> bannerList;

    public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(bannerList);
    }

    public BannerList() {
    }

    protected BannerList(Parcel in) {
        this.bannerList = in.createTypedArrayList(Banner.CREATOR);
    }

    public static final Parcelable.Creator<BannerList> CREATOR = new Parcelable.Creator<BannerList>() {
        public BannerList createFromParcel(Parcel source) {
            return new BannerList(source);
        }

        public BannerList[] newArray(int size) {
            return new BannerList[size];
        }
    };
}
