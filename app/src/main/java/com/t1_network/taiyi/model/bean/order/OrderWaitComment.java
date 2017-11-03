package com.t1_network.taiyi.model.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.t1_network.taiyi.model.bean.common.Image;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/30 14:28
 * 修改记录:
 */
public class OrderWaitComment implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("orderid")
    private String orderId;

    @SerializedName("productpic")
    private List<Image> imageList;

    @SerializedName("productname")
    private String goodName;

    public String getImage() {

        if (imageList != null && imageList.size() > 0) {
            return imageList.get(0).getUrl();
        }
        return "";
    }


    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.orderId);
        dest.writeTypedList(imageList);
        dest.writeString(this.goodName);
    }

    public OrderWaitComment() {
    }

    protected OrderWaitComment(Parcel in) {
        this.id = in.readString();
        this.orderId = in.readString();
        this.imageList = in.createTypedArrayList(Image.CREATOR);
        this.goodName = in.readString();
    }

    public static final Parcelable.Creator<OrderWaitComment> CREATOR = new Parcelable.Creator<OrderWaitComment>() {
        public OrderWaitComment createFromParcel(Parcel source) {
            return new OrderWaitComment(source);
        }

        public OrderWaitComment[] newArray(int size) {
            return new OrderWaitComment[size];
        }
    };
}
