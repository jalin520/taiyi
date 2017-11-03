package com.t1_network.taiyi.model.bean.good;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/31 11:26
 * 修改记录:
 */
public class Good implements Parcelable {

    @SerializedName("id")
    private String id; //商品编号
    @SerializedName("name")
    private String name; //商品名称
    @SerializedName("price")
    private String price; //商品价格
    @SerializedName("productpic")
    private List<GoodDetailImage> imageList; //商品图片列表
    @SerializedName("oldprice")
    private String marketPrice;//商品市场价


    /**
     * 评分
     */

    @SerializedName("performance")
    private String scorePerformance;//性能得分

    @SerializedName("usability")
    private String scoreOperate;//易操作性

    @SerializedName("brandscore")
    private String scoreBrand;//厂家评分

    @SerializedName("check")
    private String scoreCost;//性价比

    @SerializedName("expert")
    private String scoreExpert;//专家评分



    public String getScoreBrand() {
        return scoreBrand;
    }

    public void setScoreBrand(String scoreBrand) {
        this.scoreBrand = scoreBrand;
    }

    public String getScoreCost() {
        return scoreCost;
    }

    public void setScoreCost(String scoreCost) {
        this.scoreCost = scoreCost;
    }

    public String getScoreExpert() {
        return scoreExpert;
    }

    public void setScoreExpert(String scoreExpert) {
        this.scoreExpert = scoreExpert;
    }

    public String getScoreOperate() {
        return scoreOperate;
    }

    public void setScoreOperate(String scoreOperate) {
        this.scoreOperate = scoreOperate;
    }

    public String getScorePerformance() {
        return scorePerformance;
    }

    public void setScorePerformance(String scorePerformance) {
        this.scorePerformance = scorePerformance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<GoodDetailImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<GoodDetailImage> imageList) {
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

    public Good() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeTypedList(imageList);
        dest.writeString(this.marketPrice);
        dest.writeString(this.scorePerformance);
        dest.writeString(this.scoreOperate);
        dest.writeString(this.scoreBrand);
        dest.writeString(this.scoreCost);
        dest.writeString(this.scoreExpert);
    }

    protected Good(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.imageList = in.createTypedArrayList(GoodDetailImage.CREATOR);
        this.marketPrice = in.readString();
        this.scorePerformance = in.readString();
        this.scoreOperate = in.readString();
        this.scoreBrand = in.readString();
        this.scoreCost = in.readString();
        this.scoreExpert = in.readString();
    }

    public static final Creator<Good> CREATOR = new Creator<Good>() {
        public Good createFromParcel(Parcel source) {
            return new Good(source);
        }

        public Good[] newArray(int size) {
            return new Good[size];
        }
    };
}
