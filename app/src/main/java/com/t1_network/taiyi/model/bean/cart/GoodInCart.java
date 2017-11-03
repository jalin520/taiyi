package com.t1_network.taiyi.model.bean.cart;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.t1_network.taiyi.model.bean.common.Image;

import java.util.List;

/**
 * 购物车中的商品
 */
public class GoodInCart implements Parcelable {

    /**
     * 商品编号
     */
    @SerializedName("id")
    private String id;

    /**
     * 商品名称
     */
    @SerializedName("name")
    private String name;

    /**
     * 市场价
     */
    @SerializedName("oldprice")
    private String marketPrice;

    /**
     * 现价
     */
    @SerializedName("price")
    private String price;

    /**
     * 商品数量
     */
    @SerializedName("count")
    private String count;

    /**
     * 商品价格小计
     */
    @SerializedName("total")
    private String total;

    /**
     * 代币使用限制
     */
    @SerializedName("voucher")
    private String voucher;

    /**
     * 商品图片
     */
    @SerializedName("productpic")
    private List<Image> imageList;

    /**
     * 是否选中
     */
    @SerializedName("selected")
    private long selected;

    //Extra


    /**
     * 是否选中,方便界面判断
     *
     * @return
     */
    public boolean isSelect() {
        return selected == 1;
    }

    public void setIsSelect(boolean isSelect) {
        if (isSelect) {
            selected = 1;
        } else {
            selected = 0;
        }
    }


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
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

    public long getSelected() {
        return selected;
    }

    public void setSelected(long selected) {
        this.selected = selected;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.marketPrice);
        dest.writeString(this.price);
        dest.writeString(this.count);
        dest.writeString(this.total);
        dest.writeString(this.voucher);
        dest.writeTypedList(imageList);
        dest.writeLong(this.selected);
    }

    public GoodInCart() {
    }

    protected GoodInCart(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.marketPrice = in.readString();
        this.price = in.readString();
        this.count = in.readString();
        this.total = in.readString();
        this.voucher = in.readString();
        this.imageList = in.createTypedArrayList(Image.CREATOR);
        this.selected = in.readLong();
    }

    public static final Parcelable.Creator<GoodInCart> CREATOR = new Parcelable.Creator<GoodInCart>() {
        public GoodInCart createFromParcel(Parcel source) {
            return new GoodInCart(source);
        }

        public GoodInCart[] newArray(int size) {
            return new GoodInCart[size];
        }
    };
}
