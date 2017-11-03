package com.t1_network.taiyi.model.bean.good;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by David on 15/12/17.
 */
public class Product implements Parcelable {

    @SerializedName("sid")
    private String shopId; //商家ID

    @SerializedName("id")
    private String id; //商品id

    @SerializedName("name")
    private String name; //商品名称

    @SerializedName("oldprice")
    private String marketPrice; //市场价

    @SerializedName("price")
    private String price; //价格

    @SerializedName("goodsname")
    private String goodInfoName; //商品基本信息模块的  商品名称

    @SerializedName("businessname")
    private String shopName;

    @SerializedName("summary")
    private String summary;

    @SerializedName("classifyname")
    private String commonName;

    @SerializedName("register")
    private String goodInfoRegister; //商品基本信息模块的  批准文号

    @SerializedName("sales")
    private String saleCount; //销量

    //认证

    @SerializedName("s9001")
    private String registerS9001;

    public boolean hasRegisterIOS9001() {
        return "1".equals(registerS9001);
    }


    @SerializedName("s13485")
    private String registerS13485;

    public boolean hasRegisterIOS13485() {
        return "1".equals(registerS13485);
    }


    @SerializedName("ccc")
    private String register3C; //是否3c认证 0-否 1-是

    public boolean hasRegister3C() {
        return "1".equals(register3C);
    }

    @SerializedName("web")
    private String registerWeb; //是否互联网 0-否 1-是

    public boolean hasRegisterWeb() {
        return "1".equals(registerWeb);
    }

    @SerializedName("health")
    private String registerHealth;

    public boolean hasRegisterHealth() {
        return "1".equals(registerHealth);
    }

    @SerializedName("madeinchina")
    private String registerMadeinchina;

    public boolean hasRegisterMadeInChina() {
        return "1".equals(registerMadeinchina);
    }


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


    /**
     * 商品详情-详情-图片
     */
    @SerializedName("appear")
    private List<GoodDetailImage> goodDetailImageList;

    /**
     * 商品图片
     */
    @SerializedName("productpic")
    private List<GoodDetailImage> goodImageList;

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public List<GoodDetailImage> getGoodDetailImageList() {
        return goodDetailImageList;
    }

    public void setGoodDetailImageList(List<GoodDetailImage> goodDetailImageList) {
        this.goodDetailImageList = goodDetailImageList;
    }

    public List<GoodDetailImage> getGoodImageList() {
        return goodImageList;
    }

    public void setGoodImageList(List<GoodDetailImage> goodImageList) {
        this.goodImageList = goodImageList;
    }

    public String getGoodInfoName() {
        return goodInfoName;
    }

    public void setGoodInfoName(String goodInfoName) {
        this.goodInfoName = goodInfoName;
    }

    public String getGoodInfoRegister() {
        return goodInfoRegister;
    }

    public void setGoodInfoRegister(String goodInfoRegister) {
        this.goodInfoRegister = goodInfoRegister;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRegister3C() {
        return register3C;
    }

    public void setRegister3C(String register3C) {
        this.register3C = register3C;
    }

    public String getRegisterHealth() {
        return registerHealth;
    }

    public void setRegisterHealth(String registerHealth) {
        this.registerHealth = registerHealth;
    }

    public String getRegisterMadeinchina() {
        return registerMadeinchina;
    }

    public void setRegisterMadeinchina(String registerMadeinchina) {
        this.registerMadeinchina = registerMadeinchina;
    }

    public String getRegisterS13485() {
        return registerS13485;
    }

    public void setRegisterS13485(String registerS13485) {
        this.registerS13485 = registerS13485;
    }

    public String getRegisterS9001() {
        return registerS9001;
    }

    public void setRegisterS9001(String registerS9001) {
        this.registerS9001 = registerS9001;
    }

    public String getRegisterWeb() {
        return registerWeb;
    }

    public void setRegisterWeb(String registerWeb) {
        this.registerWeb = registerWeb;
    }

    public String getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(String saleCount) {
        this.saleCount = saleCount;
    }

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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shopId);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.marketPrice);
        dest.writeString(this.price);
        dest.writeString(this.goodInfoName);
        dest.writeString(this.shopName);
        dest.writeString(this.summary);
        dest.writeString(this.commonName);
        dest.writeString(this.goodInfoRegister);
        dest.writeString(this.saleCount);
        dest.writeString(this.registerS9001);
        dest.writeString(this.registerS13485);
        dest.writeString(this.register3C);
        dest.writeString(this.registerWeb);
        dest.writeString(this.registerHealth);
        dest.writeString(this.registerMadeinchina);
        dest.writeString(this.scorePerformance);
        dest.writeString(this.scoreOperate);
        dest.writeString(this.scoreBrand);
        dest.writeString(this.scoreCost);
        dest.writeString(this.scoreExpert);
        dest.writeTypedList(goodDetailImageList);
        dest.writeTypedList(goodImageList);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.shopId = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.marketPrice = in.readString();
        this.price = in.readString();
        this.goodInfoName = in.readString();
        this.shopName = in.readString();
        this.summary = in.readString();
        this.commonName = in.readString();
        this.goodInfoRegister = in.readString();
        this.saleCount = in.readString();
        this.registerS9001 = in.readString();
        this.registerS13485 = in.readString();
        this.register3C = in.readString();
        this.registerWeb = in.readString();
        this.registerHealth = in.readString();
        this.registerMadeinchina = in.readString();
        this.scorePerformance = in.readString();
        this.scoreOperate = in.readString();
        this.scoreBrand = in.readString();
        this.scoreCost = in.readString();
        this.scoreExpert = in.readString();
        this.goodDetailImageList = in.createTypedArrayList(GoodDetailImage.CREATOR);
        this.goodImageList = in.createTypedArrayList(GoodDetailImage.CREATOR);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
