package com.t1_network.taiyi.model.bean.good;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by David on 15/12/17.
 */
public class GoodDetail implements Parcelable {


    @SerializedName("product")
    private Product product;

    @SerializedName("spec")
    private List<Spec> specList;

    @SerializedName("performance")
    private List<Good> bubbleGoodList;

    @SerializedName("info")
    private List<GoodDetailInfoBean> mGoodDetailInfoBeanList;



    public List<Good> getBubbleGoodList() {
        return bubbleGoodList;
    }

    public void setBubbleGoodList(List<Good> bubbleGoodList) {
        this.bubbleGoodList = bubbleGoodList;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public List<Spec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<Spec> specList) {
        this.specList = specList;
    }

    public List<GoodDetailInfoBean> getGoodDetailInfoBeanList() {
        return mGoodDetailInfoBeanList;
    }

    public void setGoodDetailInfoBeanList(List<GoodDetailInfoBean> goodDetailInfoBeanList) {
        mGoodDetailInfoBeanList = goodDetailInfoBeanList;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoodDetail that = (GoodDetail) o;

        if (product != null ? !product.equals(that.product) : that.product != null) return false;
        if (specList != null ? !specList.equals(that.specList) : that.specList != null)
            return false;
        return !(bubbleGoodList != null ? !bubbleGoodList.equals(that.bubbleGoodList) : that.bubbleGoodList != null);

    }

    @Override
    public int hashCode() {
        int result = product != null ? product.hashCode() : 0;
        result = 31 * result + (specList != null ? specList.hashCode() : 0);
        result = 31 * result + (bubbleGoodList != null ? bubbleGoodList.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.product, 0);
        dest.writeTypedList(specList);
        dest.writeTypedList(bubbleGoodList);
        dest.writeTypedList(mGoodDetailInfoBeanList);
    }

    public GoodDetail() {
    }

    protected GoodDetail(Parcel in) {
        this.product = in.readParcelable(Product.class.getClassLoader());
        this.specList = in.createTypedArrayList(Spec.CREATOR);
        this.bubbleGoodList = in.createTypedArrayList(Good.CREATOR);
        this.mGoodDetailInfoBeanList = in.createTypedArrayList(GoodDetailInfoBean.CREATOR);
    }

    public static final Parcelable.Creator<GoodDetail> CREATOR = new Parcelable.Creator<GoodDetail>() {
        public GoodDetail createFromParcel(Parcel source) {
            return new GoodDetail(source);
        }

        public GoodDetail[] newArray(int size) {
            return new GoodDetail[size];
        }
    };
}
