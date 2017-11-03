package com.t1_network.taiyi.model.bean.order;

import org.json.JSONArray;

/**
 * Created by David on 2016/1/26.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class ReturnOrderBean {


    /**
     * orderid : 1511110301
     * productid : 10153
     * userid : 201511030001
     * type : 1
     * freight : 10
     * quantity : 1
     * refund : 0.01
     * name : 何健荣
     * phone : 15916210842
     * address : 广东省 珠海市 香洲区 唐家湾镇 哈工大路1号博士楼C206
     * reason : 不想要
     * explain : 坏了
     */

    private String orderid;
    private String productid;
    private String userid;
    private String type;
    private String freight;
    private String quantity;
    private String refund;
    private String name;
    private String phone;
    private String address;
    private String reason;
    private String explain;
    private JSONArray proof;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public void setProof(JSONArray proof) {
        this.proof = proof;
    }

    public JSONArray getProof() {
        return proof;
    }

    @Override
    public String toString() {
        return "ReturnOrderBean{" +
                "orderid='" + orderid + '\'' +
                ", productid='" + productid + '\'' +
                ", userid='" + userid + '\'' +
                ", type='" + type + '\'' +
                ", freight='" + freight + '\'' +
                ", quantity='" + quantity + '\'' +
                ", refund='" + refund + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", reason='" + reason + '\'' +
                ", explain='" + explain + '\'' +
                ", proof=" + proof +
                '}';
    }


}
