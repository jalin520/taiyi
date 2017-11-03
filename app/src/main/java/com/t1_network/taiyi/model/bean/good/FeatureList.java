package com.t1_network.taiyi.model.bean.good;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/20 15:19
 * 修改记录:
 */
public class FeatureList implements Parcelable {

    @SerializedName("result")
    private List<Feature> featureList;

    public List<Feature> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(List<Feature> featureList) {
        this.featureList = featureList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(featureList);
    }

    public FeatureList() {
    }

    protected FeatureList(Parcel in) {
        this.featureList = in.createTypedArrayList(Feature.CREATOR);
    }

    public static final Parcelable.Creator<FeatureList> CREATOR = new Parcelable.Creator<FeatureList>() {
        public FeatureList createFromParcel(Parcel source) {
            return new FeatureList(source);
        }

        public FeatureList[] newArray(int size) {
            return new FeatureList[size];
        }
    };
}
