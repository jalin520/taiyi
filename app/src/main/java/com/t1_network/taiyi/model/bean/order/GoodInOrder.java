package com.t1_network.taiyi.model.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by David on 15/12/18.
 */
public class GoodInOrder implements Parcelable {

    @SerializedName("orderid")
    private String id;

    @SerializedName("productid")
    private String productId;

    @SerializedName("productname")
    private String name;

    @SerializedName("price")
    private String price;

    @SerializedName("quantity")
    private String count;

    @SerializedName("voucher")
    private String voucher;

    @SerializedName("total")
    private String total;

    @SerializedName("productpic")
    private List<ProductPic> imageList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ProductPic> getImageList() {
        return imageList;
    }

    public void setImageList(List<ProductPic> imageList) {
        this.imageList = imageList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.productId);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.count);
        dest.writeString(this.voucher);
        dest.writeString(this.total);
        dest.writeTypedList(imageList);
    }

    public GoodInOrder() {
    }

    protected GoodInOrder(Parcel in) {
        this.id = in.readString();
        this.productId = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.count = in.readString();
        this.voucher = in.readString();
        this.total = in.readString();
        this.imageList = in.createTypedArrayList(ProductPic.CREATOR);
    }

    public static final Parcelable.Creator<GoodInOrder> CREATOR = new Parcelable.Creator<GoodInOrder>() {
        public GoodInOrder createFromParcel(Parcel source) {
            return new GoodInOrder(source);
        }

        public GoodInOrder[] newArray(int size) {
            return new GoodInOrder[size];
        }
    };
}
