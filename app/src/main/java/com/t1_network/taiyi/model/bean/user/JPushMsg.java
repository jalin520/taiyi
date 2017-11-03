package com.t1_network.taiyi.model.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/25 13:26
 * 修改记录:
 */
public class JPushMsg implements Parcelable {
    @SerializedName("msg_id")
    private long id;
    @SerializedName("alert")
    private String content;
    @SerializedName("time")
    private String time;
    @SerializedName("sendno")
    private long sendNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSendNum() {
        return sendNum;
    }

    public void setSendNum(long sendNum) {
        this.sendNum = sendNum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.content);
        dest.writeString(this.time);
        dest.writeLong(this.sendNum);
    }

    public JPushMsg() {
    }

    protected JPushMsg(Parcel in) {
        this.id = in.readLong();
        this.content = in.readString();
        this.time = in.readString();
        this.sendNum = in.readLong();
    }

    public static final Parcelable.Creator<JPushMsg> CREATOR = new Parcelable.Creator<JPushMsg>() {
        public JPushMsg createFromParcel(Parcel source) {
            return new JPushMsg(source);
        }

        public JPushMsg[] newArray(int size) {
            return new JPushMsg[size];
        }
    };
}
