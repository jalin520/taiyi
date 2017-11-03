package com.t1_network.taiyi.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by David on 15/11/16.
 */
public class Login implements Parcelable {

    private String id;
    private String remoteip;
    private String ip;
    private String access;
    private String uuid;
    private String status;
    private String times;
    private String logintime;
    private String logouttime;
    private String sysupdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemoteip() {
        return remoteip;
    }

    public void setRemoteip(String remoteip) {
        this.remoteip = remoteip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getLogintime() {
        return logintime;
    }

    public void setLogintime(String logintime) {
        this.logintime = logintime;
    }

    public String getLogouttime() {
        return logouttime;
    }

    public void setLogouttime(String logouttime) {
        this.logouttime = logouttime;
    }

    public String getSysupdate() {
        return sysupdate;
    }

    public void setSysupdate(String sysupdate) {
        this.sysupdate = sysupdate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.remoteip);
        dest.writeString(this.ip);
        dest.writeString(this.access);
        dest.writeString(this.uuid);
        dest.writeString(this.status);
        dest.writeString(this.times);
        dest.writeString(this.logintime);
        dest.writeString(this.logouttime);
        dest.writeString(this.sysupdate);
    }

    public Login() {
    }

    protected Login(Parcel in) {
        this.id = in.readString();
        this.remoteip = in.readString();
        this.ip = in.readString();
        this.access = in.readString();
        this.uuid = in.readString();
        this.status = in.readString();
        this.times = in.readString();
        this.logintime = in.readString();
        this.logouttime = in.readString();
        this.sysupdate = in.readString();
    }

    public static final Creator<Login> CREATOR = new Creator<Login>() {
        public Login createFromParcel(Parcel source) {
            return new Login(source);
        }

        public Login[] newArray(int size) {
            return new Login[size];
        }
    };
}
