package com.t1_network.taiyi.model.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by David on 15/12/21.
 */
public class ProductPic implements Parcelable {

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

    public ProductPic() {
    }

    protected ProductPic(Parcel in) {
        this.url = in.readString();
    }

    public static final Parcelable.Creator<ProductPic> CREATOR = new Parcelable.Creator<ProductPic>() {
        public ProductPic createFromParcel(Parcel source) {
            return new ProductPic(source);
        }

        public ProductPic[] newArray(int size) {
            return new ProductPic[size];
        }
    };
}
