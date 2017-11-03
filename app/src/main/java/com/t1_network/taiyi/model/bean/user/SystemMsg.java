package com.t1_network.taiyi.model.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/25 14:43
 * 修改记录:
 */
public class SystemMsg implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("content")
    private String content;

    @SerializedName("sendtime")
    private String time;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.content);
        dest.writeString(this.time);
    }

    public SystemMsg() {
    }

    protected SystemMsg(Parcel in) {
        this.id = in.readString();
        this.content = in.readString();
        this.time = in.readString();
    }

    public static final Parcelable.Creator<SystemMsg> CREATOR = new Parcelable.Creator<SystemMsg>() {
        public SystemMsg createFromParcel(Parcel source) {
            return new SystemMsg(source);
        }

        public SystemMsg[] newArray(int size) {
            return new SystemMsg[size];
        }
    };
}
