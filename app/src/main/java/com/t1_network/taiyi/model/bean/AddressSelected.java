package com.t1_network.taiyi.model.bean;

/**
 * Created by David on 2016/1/15.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class AddressSelected {

    /**
     * id : 9
     * name : backaddr
     * remark : 官方退换货地址
     * sysupdate : 2015-12-02 11:31:21
     * value : {"address":"广东省珠海市香洲区唐家湾镇哈工大路1号博士楼C206","city":"珠海市","district":"香洲区","name":"何健荣","phone":"15916210842","province":"广东省","zipcode":"519100"}
     */

    private BizEntity biz;

    public void setBiz(BizEntity biz) {
        this.biz = biz;
    }

    public BizEntity getBiz() {
        return biz;
    }

    public static class BizEntity {
        private String id;
        private String name;
        private String remark;
        private String sysupdate;
        /**
         * address : 广东省珠海市香洲区唐家湾镇哈工大路1号博士楼C206
         * city : 珠海市
         * district : 香洲区
         * name : 何健荣
         * phone : 15916210842
         * province : 广东省
         * zipcode : 519100
         */

        private ValueEntity value;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public void setSysupdate(String sysupdate) {
            this.sysupdate = sysupdate;
        }

        public void setValue(ValueEntity value) {
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getRemark() {
            return remark;
        }

        public String getSysupdate() {
            return sysupdate;
        }

        public ValueEntity getValue() {
            return value;
        }

        public static class ValueEntity {
            private String address;
            private String city;
            private String district;
            private String name;
            private String phone;
            private String province;
            private String zipcode;

            public void setAddress(String address) {
                this.address = address;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setDistrict(String district) {
                this.district = district;
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

            public void setZipcode(String zipcode) {
                this.zipcode = zipcode;
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

            public String getName() {
                return name;
            }

            public String getPhone() {
                return phone;
            }

            public String getProvince() {
                return province;
            }

            public String getZipcode() {
                return zipcode;
            }
        }
    }
}
