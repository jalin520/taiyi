package com.t1_network.taiyi.model.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by David on 2016/1/21.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class OrderDetailBean implements Parcelable {

    @SerializedName("order")
    private OrderEntity order;

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public static class OrderEntity implements Parcelable {
        /**
         * address : 港湾大道
         * city : 珠海市
         * defaults : 1
         * district : 香洲区
         * id : 151208165524101
         * name : 陈佳润
         * phone : 15680030824
         * province : 广东省
         * sysupdate : 2016-01-15 14:09:51
         * tag : 家
         * userid : 15000429
         */
        @SerializedName("address")
        private AddressEntity address;
        @SerializedName("delivertime")
        private String delivertime;

        @SerializedName("id")
        private String id;

        @SerializedName("paytime")
        private String paytime;
        @SerializedName("quantity")
        private String quantity;
        @SerializedName("subtotal")
        private String subtotal;
        @SerializedName("total")
        private String total;
        @SerializedName("voucher")
        private String voucher;


        /**
         * comment : 0
         * orderid : 1601180561
         * price : 121.00
         * productid : 10174
         * productname : [太一自营]九安(andon)电子血压计 家用全自动上臂式 KD-59101
         * productpic : [{"belongid":"10174","heperlink":"","id":"160104165412105","keyword":"","name":"product_pic","productid":"","seq":"3","type":"7","url":"http://image.t1-network.com/admin/thumbnailPic/20160104/160104165342104.png"},{"belongid":"10174","heperlink":"","id":"160104165412106","keyword":"","name":"product_pic","productid":"","seq":"4","type":"7","url":"http://image.t1-network.com/admin/thumbnailPic/20160104/160104165339103.png"}]
         * quantity : 1
         * seq : 1
         * subtotal : 121.00
         * sysupdate : 2016-01-18 16:37:35
         * total : 121.00
         * voucher : 0
         */

        @SerializedName("orderdetail")
        private List<OrderdetailEntity> orderdetail;

        /**
         * content : 随便写咯
         * email :
         * phone :
         * title : 哈哈公司
         * type : 纸质发票
         */
//        @SerializedName("receipt")
//        private ReceiptEntity receipt;


        public void setAddress(AddressEntity address) {
            this.address = address;
        }


        public void setDelivertime(String delivertime) {
            this.delivertime = delivertime;
        }


        public void setId(String id) {
            this.id = id;
        }


        public void setPaytime(String paytime) {
            this.paytime = paytime;
        }


        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }


        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }


        public void setTotal(String total) {
            this.total = total;
        }


        public void setVoucher(String voucher) {
            this.voucher = voucher;
        }

        public void setOrderdetail(List<OrderdetailEntity> orderdetail) {
            this.orderdetail = orderdetail;
        }


        public AddressEntity getAddress() {
            return address;
        }

        public String getDelivertime() {
            return delivertime;
        }


        public String getId() {
            return id;
        }


        public String getPaytime() {
            return paytime;
        }


        public String getQuantity() {
            return quantity;
        }


        public String getSubtotal() {
            return subtotal;
        }


        public String getTotal() {
            return total;
        }


        public String getVoucher() {
            return voucher;
        }


        public List<OrderdetailEntity> getOrderdetail() {
            return orderdetail;
        }


//        public void setReceipt(ReceiptEntity receipt) {
//            this.receipt = receipt;
//        }
//
//        public ReceiptEntity getReceipt() {
//            return receipt;
//        }


        public static class AddressEntity implements Parcelable {
            @SerializedName("address")
            private String address;
            @SerializedName("city")
            private String city;

            @SerializedName("district")
            private String district;
            @SerializedName("id")
            private String id;
            @SerializedName("name")
            private String name;
            @SerializedName("phone")
            private String phone;
            @SerializedName("province")
            private String province;
            @SerializedName("sysupdate")
            private String sysupdate;
            @SerializedName("tag")
            private String tag;
            @SerializedName("userid")
            private String userid;

            public void setAddress(String address) {
                this.address = address;
            }

            public void setCity(String city) {
                this.city = city;
            }


            public void setDistrict(String district) {
                this.district = district;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public void setSysupdate(String sysupdate) {
                this.sysupdate = sysupdate;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getAddress() {
                return address;
            }

            public String getCity() {
                return city;
            }


            public String getDistrict() {
                return district;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getPhone() {
                return phone;
            }

            public String getProvince() {
                return province;
            }

            public String getSysupdate() {
                return sysupdate;
            }

            public String getTag() {
                return tag;
            }

            public String getUserid() {
                return userid;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.address);
                dest.writeString(this.city);
                dest.writeString(this.district);
                dest.writeString(this.id);
                dest.writeString(this.name);
                dest.writeString(this.phone);
                dest.writeString(this.province);
                dest.writeString(this.sysupdate);
                dest.writeString(this.tag);
                dest.writeString(this.userid);
            }

            public AddressEntity() {
            }

            protected AddressEntity(Parcel in) {
                this.address = in.readString();
                this.city = in.readString();
                this.district = in.readString();
                this.id = in.readString();
                this.name = in.readString();
                this.phone = in.readString();
                this.province = in.readString();
                this.sysupdate = in.readString();
                this.tag = in.readString();
                this.userid = in.readString();
            }

            public static final Parcelable.Creator<AddressEntity> CREATOR = new Parcelable.Creator<AddressEntity>() {
                public AddressEntity createFromParcel(Parcel source) {
                    return new AddressEntity(source);
                }

                public AddressEntity[] newArray(int size) {
                    return new AddressEntity[size];
                }
            };
        }

        public static class OrderdetailEntity implements Parcelable {
            @SerializedName("comment")
            private String comment;
            @SerializedName("orderid")
            private String orderid;
            @SerializedName("price")
            private String price;
            @SerializedName("productid")
            private String productid;
            @SerializedName("productname")
            private String productname;
            @SerializedName("quantity")
            private String quantity;
            @SerializedName("seq")
            private String seq;
            @SerializedName("subtotal")
            private String subtotal;
            @SerializedName("sysupdate")
            private String sysupdate;
            @SerializedName("total")
            private String total;
            @SerializedName("voucher")
            private String voucher;
            /**
             * belongid : 10174
             * heperlink :
             * id : 160104165412105
             * keyword :
             * name : product_pic
             * productid :
             * seq : 3
             * type : 7
             * url : http://image.t1-network.com/admin/thumbnailPic/20160104/160104165342104.png
             */
            @SerializedName("productpic")
            private List<ProductpicEntity> productpic;

            public void setComment(String comment) {
                this.comment = comment;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setProductid(String productid) {
                this.productid = productid;
            }

            public void setProductname(String productname) {
                this.productname = productname;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public void setSeq(String seq) {
                this.seq = seq;
            }

            public void setSubtotal(String subtotal) {
                this.subtotal = subtotal;
            }

            public void setSysupdate(String sysupdate) {
                this.sysupdate = sysupdate;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public void setVoucher(String voucher) {
                this.voucher = voucher;
            }

            public void setProductpic(List<ProductpicEntity> productpic) {
                this.productpic = productpic;
            }

            public String getComment() {
                return comment;
            }

            public String getOrderid() {
                return orderid;
            }

            public String getPrice() {
                return price;
            }

            public String getProductid() {
                return productid;
            }

            public String getProductname() {
                return productname;
            }

            public String getQuantity() {
                return quantity;
            }

            public String getSeq() {
                return seq;
            }

            public String getSubtotal() {
                return subtotal;
            }

            public String getSysupdate() {
                return sysupdate;
            }

            public String getTotal() {
                return total;
            }

            public String getVoucher() {
                return voucher;
            }

            public List<ProductpicEntity> getProductpic() {
                return productpic;
            }

            public static class ProductpicEntity implements Parcelable {
                @SerializedName("belongid")
                private String belongid;
                @SerializedName("heperlink")
                private String heperlink;
                @SerializedName("id")
                private String id;
                @SerializedName("keyword")
                private String keyword;
                @SerializedName("name")
                private String name;
                @SerializedName("productid")
                private String productid;
                @SerializedName("seq")
                private String seq;
                @SerializedName("type")
                private String type;
                @SerializedName("url")
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

                public static final Parcelable.Creator<ProductpicEntity> CREATOR = new Parcelable.Creator<ProductpicEntity>() {
                    public ProductpicEntity createFromParcel(Parcel source) {
                        return new ProductpicEntity(source);
                    }

                    public ProductpicEntity[] newArray(int size) {
                        return new ProductpicEntity[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.comment);
                dest.writeString(this.orderid);
                dest.writeString(this.price);
                dest.writeString(this.productid);
                dest.writeString(this.productname);
                dest.writeString(this.quantity);
                dest.writeString(this.seq);
                dest.writeString(this.subtotal);
                dest.writeString(this.sysupdate);
                dest.writeString(this.total);
                dest.writeString(this.voucher);
                dest.writeTypedList(productpic);
            }

            public OrderdetailEntity() {
            }

            protected OrderdetailEntity(Parcel in) {
                this.comment = in.readString();
                this.orderid = in.readString();
                this.price = in.readString();
                this.productid = in.readString();
                this.productname = in.readString();
                this.quantity = in.readString();
                this.seq = in.readString();
                this.subtotal = in.readString();
                this.sysupdate = in.readString();
                this.total = in.readString();
                this.voucher = in.readString();
                this.productpic = in.createTypedArrayList(ProductpicEntity.CREATOR);
            }

            public static final Parcelable.Creator<OrderdetailEntity> CREATOR = new Parcelable.Creator<OrderdetailEntity>() {
                public OrderdetailEntity createFromParcel(Parcel source) {
                    return new OrderdetailEntity(source);
                }

                public OrderdetailEntity[] newArray(int size) {
                    return new OrderdetailEntity[size];
                }
            };
        }

//        public static class ReceiptEntity implements Parcelable {
//            @SerializedName("content")
//            private String content;
//            @SerializedName("email")
//            private String email;
//            @SerializedName("phone")
//            private String phone;
//            @SerializedName("title")
//            private String title;
//            @SerializedName("type")
//            private String type;
//
//            public void setContent(String content) {
//                this.content = content;
//            }
//
//            public void setEmail(String email) {
//                this.email = email;
//            }
//
//            public void setPhone(String phone) {
//                this.phone = phone;
//            }
//
//            public void setTitle(String title) {
//                this.title = title;
//            }
//
//            public void setType(String type) {
//                this.type = type;
//            }
//
//            public String getContent() {
//                return content;
//            }
//
//            public String getEmail() {
//                return email;
//            }
//
//            public String getPhone() {
//                return phone;
//            }
//
//            public String getTitle() {
//                return title;
//            }
//
//            public String getType() {
//                return type;
//            }
//
//            @Override
//            public int describeContents() {
//                return 0;
//            }
//
//            @Override
//            public void writeToParcel(Parcel dest, int flags) {
//                dest.writeString(this.content);
//                dest.writeString(this.email);
//                dest.writeString(this.phone);
//                dest.writeString(this.title);
//                dest.writeString(this.type);
//            }
//
//            public ReceiptEntity() {
//            }
//
//            protected ReceiptEntity(Parcel in) {
//                this.content = in.readString();
//                this.email = in.readString();
//                this.phone = in.readString();
//                this.title = in.readString();
//                this.type = in.readString();
//            }
//
//            public static final Creator<ReceiptEntity> CREATOR = new Creator<ReceiptEntity>() {
//                public ReceiptEntity createFromParcel(Parcel source) {
//                    return new ReceiptEntity(source);
//                }
//
//                public ReceiptEntity[] newArray(int size) {
//                    return new ReceiptEntity[size];
//                }
//            };
//        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.address, 0);
            dest.writeString(this.delivertime);
            dest.writeString(this.id);
            dest.writeString(this.paytime);
            dest.writeString(this.quantity);
            dest.writeString(this.subtotal);
            dest.writeString(this.total);
            dest.writeString(this.voucher);
            dest.writeTypedList(orderdetail);
//            dest.writeParcelable(this.receipt, 0);
        }

        public OrderEntity() {
        }

        protected OrderEntity(Parcel in) {
            this.address = in.readParcelable(AddressEntity.class.getClassLoader());
            this.delivertime = in.readString();
            this.id = in.readString();
            this.paytime = in.readString();
            this.quantity = in.readString();
            this.subtotal = in.readString();
            this.total = in.readString();
            this.voucher = in.readString();
            this.orderdetail = in.createTypedArrayList(OrderdetailEntity.CREATOR);
//            this.receipt = in.readParcelable(ReceiptEntity.class.getClassLoader());
        }

        public static final Creator<OrderEntity> CREATOR = new Creator<OrderEntity>() {
            public OrderEntity createFromParcel(Parcel source) {
                return new OrderEntity(source);
            }

            public OrderEntity[] newArray(int size) {
                return new OrderEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.order, 0);
    }

    public OrderDetailBean() {
    }

    protected OrderDetailBean(Parcel in) {
        this.order = in.readParcelable(OrderEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<OrderDetailBean> CREATOR = new Parcelable.Creator<OrderDetailBean>() {
        public OrderDetailBean createFromParcel(Parcel source) {
            return new OrderDetailBean(source);
        }

        public OrderDetailBean[] newArray(int size) {
            return new OrderDetailBean[size];
        }
    };
}
