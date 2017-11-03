package com.t1_network.taiyi.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chenyu on 2015/11/18.
 */
public class AfterSale implements Parcelable {

    @SerializedName("id")
    private String after_sale_id;//id
    @SerializedName("orderid")
    private String after_sale_orderid; //订单id
    @SerializedName("address")
    private String after_sale_address;//地址
    @SerializedName("audittime")
    private String after_sale_audittime;//审批时间
    @SerializedName("phone")
    private String after_sale_phone;//用户电话
    @SerializedName("productid")
    private String after_sale_productid;//商品id
    @SerializedName("productname")
    private String after_sale_productname;//商品名称
    @SerializedName("status")
    private String after_sale_status;//审批状态
    @SerializedName("quantity")
    private String after_sale_quantity;//商品数量
    @SerializedName("type")
    private String after_sale_type;//退换类型
    @SerializedName("deliverid")
    private String after_sale_deliverid;
    @SerializedName("remark")
    private String after_sale_remark;

    public String getAfter_sale_deliverid() {
        return after_sale_deliverid;
    }

    public void setAfter_sale_deliverid(String after_sale_deliverid) {
        this.after_sale_deliverid = after_sale_deliverid;
    }

    public String getAfter_sale_remark() {
        return after_sale_remark;
    }

    public void setAfter_sale_remark(String after_sale_remark) {
        this.after_sale_remark = after_sale_remark;
    }

    @SerializedName("productpic")
    private List<ProductpicEntity> productpic;


    /*
    *  "back": "0",
        "exchangeid": "",
        "committime": "2015-11-26 22:17:15.0",
        "audittime": "2000-01-01 00:00:00.0",
        "returntime": "2000-01-01 00:00:00.0",
        "completetime": "2000-01-01 00:00:00.0",
        "cancletime": "2000-01-01 00:00:00.0",
        "refundtime": "2000-01-01 00:00:00.0",
        "paytime": "2015-11-25 14:32:01.0",
        "sysupdate": "2015-11-26 22:17:14.0",
        "operateid": "",
        "operatename": ""
    * */
    @SerializedName("back")
    private String after_sale_back;//是否已退款
    @SerializedName("exchangeid")
    private String after_sale_exchangeid;//换货单号
    @SerializedName("committime")
    private String after_sale_committime;//申请时间
    @SerializedName("returntime")
    private String after_sale_returntime;//寄回时间
    @SerializedName("completetime")
    private String after_sale_completetime;//完成时间
    @SerializedName("cancletime")
    private String after_sale_cancletime;//撤销时间
    @SerializedName("refundtime")
    private String after_sale_refundtime;//退款时间
    @SerializedName("paytime")
    private String after_sale_paytime;//支付时间
    @SerializedName("sysupdate")
    private String after_sale_sysupdate;//更新时间
    @SerializedName("operateid")
    private String after_sale_operateid;//操作人员ID
    @SerializedName("operatename")
    private String after_sale_operatename;//操作人员名称


    public String getAfter_sale_id() {
        return after_sale_id;
    }

    public void setAfter_sale_id(String after_sale_id) {
        this.after_sale_id = after_sale_id;
    }

    public String getAfter_sale_orderid() {
        return after_sale_orderid;
    }

    public void setAfter_sale_orderid(String after_sale_orderid) {
        this.after_sale_orderid = after_sale_orderid;
    }

    public String getAfter_sale_address() {
        return after_sale_address;
    }

    public void setAfter_sale_address(String after_sale_address) {
        this.after_sale_address = after_sale_address;
    }

    public String getAfter_sale_audittime() {
        return after_sale_audittime;
    }

    public void setAfter_sale_audittime(String after_sale_audittime) {
        this.after_sale_audittime = after_sale_audittime;
    }

    public String getAfter_sale_phone() {
        return after_sale_phone;
    }

    public void setAfter_sale_phone(String after_sale_phone) {
        this.after_sale_phone = after_sale_phone;
    }

    public String getAfter_sale_productid() {
        return after_sale_productid;
    }

    public void setAfter_sale_productid(String after_sale_productid) {
        this.after_sale_productid = after_sale_productid;
    }

    public String getAfter_sale_productname() {
        return after_sale_productname;
    }

    public void setAfter_sale_productname(String after_sale_productname) {
        this.after_sale_productname = after_sale_productname;
    }

    public String getAfter_sale_status() {
        return after_sale_status;
    }

    public void setAfter_sale_status(String after_sale_status) {
        this.after_sale_status = after_sale_status;
    }

    public String getAfter_sale_quantity() {
        return after_sale_quantity;
    }

    public void setAfter_sale_quantity(String after_sale_quantity) {
        this.after_sale_quantity = after_sale_quantity;
    }

    public String getAfter_sale_back() {
        return after_sale_back;
    }

    public void setAfter_sale_back(String after_sale_back) {
        this.after_sale_back = after_sale_back;
    }

    public String getAfter_sale_exchangeid() {
        return after_sale_exchangeid;
    }

    public void setAfter_sale_exchangeid(String after_sale_exchangeid) {
        this.after_sale_exchangeid = after_sale_exchangeid;
    }

    public String getAfter_sale_committime() {
        return after_sale_committime;
    }

    public void setAfter_sale_committime(String after_sale_committime) {
        this.after_sale_committime = after_sale_committime;
    }

    public String getAfter_sale_returntime() {
        return after_sale_returntime;
    }

    public void setAfter_sale_returntime(String after_sale_returntime) {
        this.after_sale_returntime = after_sale_returntime;
    }

    public String getAfter_sale_completetime() {
        return after_sale_completetime;
    }

    public void setAfter_sale_completetime(String after_sale_completetime) {
        this.after_sale_completetime = after_sale_completetime;
    }

    public String getAfter_sale_cancletime() {
        return after_sale_cancletime;
    }

    public void setAfter_sale_cancletime(String after_sale_cancletime) {
        this.after_sale_cancletime = after_sale_cancletime;
    }

    public String getAfter_sale_refundtime() {
        return after_sale_refundtime;
    }

    public void setAfter_sale_refundtime(String after_sale_refundtime) {
        this.after_sale_refundtime = after_sale_refundtime;
    }

    public String getAfter_sale_paytime() {
        return after_sale_paytime;
    }

    public void setAfter_sale_paytime(String after_sale_paytime) {
        this.after_sale_paytime = after_sale_paytime;
    }

    public String getAfter_sale_sysupdate() {
        return after_sale_sysupdate;
    }

    public void setAfter_sale_sysupdate(String after_sale_sysupdate) {
        this.after_sale_sysupdate = after_sale_sysupdate;
    }

    public String getAfter_sale_operateid() {
        return after_sale_operateid;
    }

    public void setAfter_sale_operateid(String after_sale_operateid) {
        this.after_sale_operateid = after_sale_operateid;
    }

    public String getAfter_sale_operatename() {
        return after_sale_operatename;
    }

    public void setAfter_sale_operatename(String after_sale_operatename) {
        this.after_sale_operatename = after_sale_operatename;
    }

    public String getAfter_sale_type() {
        return after_sale_type;
    }

    public void setAfter_sale_type(String after_sale_type) {
        this.after_sale_type = after_sale_type;
    }


    public List<ProductpicEntity> getProductpic() {
        return productpic;
    }

    public void setProductpic(List<ProductpicEntity> productpic) {
        this.productpic = productpic;
    }

    public static class ProductpicEntity implements Parcelable {
        private String belongid;
        private String heperlink;
        private String id;
        private String keyword;
        private String name;
        private String productid;
        private String seq;
        private String type;
        private String url;

        public void setBelongid(String belongid) {
            this.belongid = belongid;
        }

        public void setHeperlink(String heperlink) {
            this.heperlink = heperlink;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public void setSeq(String seq) {
            this.seq = seq;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBelongid() {
            return belongid;
        }

        public String getHeperlink() {
            return heperlink;
        }

        public String getId() {
            return id;
        }

        public String getKeyword() {
            return keyword;
        }

        public String getName() {
            return name;
        }

        public String getProductid() {
            return productid;
        }

        public String getSeq() {
            return seq;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.belongid);
            dest.writeString(this.heperlink);
            dest.writeString(this.id);
            dest.writeString(this.keyword);
            dest.writeString(this.name);
            dest.writeString(this.productid);
            dest.writeString(this.seq);
            dest.writeString(this.type);
            dest.writeString(this.url);
        }

        public ProductpicEntity() {
        }

        protected ProductpicEntity(Parcel in) {
            this.belongid = in.readString();
            this.heperlink = in.readString();
            this.id = in.readString();
            this.keyword = in.readString();
            this.name = in.readString();
            this.productid = in.readString();
            this.seq = in.readString();
            this.type = in.readString();
            this.url = in.readString();
        }

        public static final Creator<ProductpicEntity> CREATOR = new Creator<ProductpicEntity>() {
            public ProductpicEntity createFromParcel(Parcel source) {
                return new ProductpicEntity(source);
            }

            public ProductpicEntity[] newArray(int size) {
                return new ProductpicEntity[size];
            }
        };
    }


    public AfterSale() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.after_sale_id);
        dest.writeString(this.after_sale_orderid);
        dest.writeString(this.after_sale_address);
        dest.writeString(this.after_sale_audittime);
        dest.writeString(this.after_sale_phone);
        dest.writeString(this.after_sale_productid);
        dest.writeString(this.after_sale_productname);
        dest.writeString(this.after_sale_status);
        dest.writeString(this.after_sale_quantity);
        dest.writeString(this.after_sale_type);
        dest.writeString(this.after_sale_deliverid);
        dest.writeString(this.after_sale_remark);
        dest.writeTypedList(productpic);
        dest.writeString(this.after_sale_back);
        dest.writeString(this.after_sale_exchangeid);
        dest.writeString(this.after_sale_committime);
        dest.writeString(this.after_sale_returntime);
        dest.writeString(this.after_sale_completetime);
        dest.writeString(this.after_sale_cancletime);
        dest.writeString(this.after_sale_refundtime);
        dest.writeString(this.after_sale_paytime);
        dest.writeString(this.after_sale_sysupdate);
        dest.writeString(this.after_sale_operateid);
        dest.writeString(this.after_sale_operatename);
    }

    protected AfterSale(Parcel in) {
        this.after_sale_id = in.readString();
        this.after_sale_orderid = in.readString();
        this.after_sale_address = in.readString();
        this.after_sale_audittime = in.readString();
        this.after_sale_phone = in.readString();
        this.after_sale_productid = in.readString();
        this.after_sale_productname = in.readString();
        this.after_sale_status = in.readString();
        this.after_sale_quantity = in.readString();
        this.after_sale_type = in.readString();
        this.after_sale_deliverid = in.readString();
        this.after_sale_remark = in.readString();
        this.productpic = in.createTypedArrayList(ProductpicEntity.CREATOR);
        this.after_sale_back = in.readString();
        this.after_sale_exchangeid = in.readString();
        this.after_sale_committime = in.readString();
        this.after_sale_returntime = in.readString();
        this.after_sale_completetime = in.readString();
        this.after_sale_cancletime = in.readString();
        this.after_sale_refundtime = in.readString();
        this.after_sale_paytime = in.readString();
        this.after_sale_sysupdate = in.readString();
        this.after_sale_operateid = in.readString();
        this.after_sale_operatename = in.readString();
    }

    public static final Creator<AfterSale> CREATOR = new Creator<AfterSale>() {
        public AfterSale createFromParcel(Parcel source) {
            return new AfterSale(source);
        }

        public AfterSale[] newArray(int size) {
            return new AfterSale[size];
        }
    };
}
