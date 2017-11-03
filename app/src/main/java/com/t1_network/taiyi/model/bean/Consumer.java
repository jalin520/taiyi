package com.t1_network.taiyi.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by David on 15/11/16.
 */
public class Consumer implements Parcelable {

    private String id;
    private String usercode;
    private String phone;
    private String username;
    private String password;
    private String wechat;
    private String qqid;
    private String weibo;
    private String status;
    private String token;
    private String birthday;
    private String email;
    private String emailverify;
    private String sex;
    private String voucher;
    private String adminid;
    private String registertype;
    private String registertime;
    private String sysupdate;
    private String nickname;
    private String photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQqid() {
        return qqid;
    }

    public void setQqid(String qqid) {
        this.qqid = qqid;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailverify() {
        return emailverify;
    }

    public void setEmailverify(String emailverify) {
        this.emailverify = emailverify;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    public String getRegistertype() {
        return registertype;
    }

    public void setRegistertype(String registertype) {
        this.registertype = registertype;
    }

    public String getRegistertime() {
        return registertime;
    }

    public void setRegistertime(String registertime) {
        this.registertime = registertime;
    }

    public String getSysupdate() {
        return sysupdate;
    }

    public void setSysupdate(String sysupdate) {
        this.sysupdate = sysupdate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.usercode);
        dest.writeString(this.phone);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.wechat);
        dest.writeString(this.qqid);
        dest.writeString(this.weibo);
        dest.writeString(this.status);
        dest.writeString(this.token);
        dest.writeString(this.birthday);
        dest.writeString(this.email);
        dest.writeString(this.emailverify);
        dest.writeString(this.sex);
        dest.writeString(this.voucher);
        dest.writeString(this.adminid);
        dest.writeString(this.registertype);
        dest.writeString(this.registertime);
        dest.writeString(this.sysupdate);
        dest.writeString(this.nickname);
        dest.writeString(this.photo);
    }

    public Consumer() {
    }

    protected Consumer(Parcel in) {
        this.id = in.readString();
        this.usercode = in.readString();
        this.phone = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.wechat = in.readString();
        this.qqid = in.readString();
        this.weibo = in.readString();
        this.status = in.readString();
        this.token = in.readString();
        this.birthday = in.readString();
        this.email = in.readString();
        this.emailverify = in.readString();
        this.sex = in.readString();
        this.voucher = in.readString();
        this.adminid = in.readString();
        this.registertype = in.readString();
        this.registertime = in.readString();
        this.sysupdate = in.readString();
        this.nickname = in.readString();
        this.photo = in.readString();
    }

    public static final Parcelable.Creator<Consumer> CREATOR = new Parcelable.Creator<Consumer>() {
        public Consumer createFromParcel(Parcel source) {
            return new Consumer(source);
        }

        public Consumer[] newArray(int size) {
            return new Consumer[size];
        }
    };
}
