package com.t1_network.taiyi.model.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/25 14:47
 * 修改记录:
 */
public class SystemMsgList implements Parcelable {

    @SerializedName("result")
    private List<SystemMsg> msgList;

    public List<SystemMsg> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<SystemMsg> msgList) {
        this.msgList = msgList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(msgList);
    }

    public SystemMsgList() {
    }

    protected SystemMsgList(Parcel in) {
        this.msgList = in.createTypedArrayList(SystemMsg.CREATOR);
    }

    public static final Parcelable.Creator<SystemMsgList> CREATOR = new Parcelable.Creator<SystemMsgList>() {
        public SystemMsgList createFromParcel(Parcel source) {
            return new SystemMsgList(source);
        }

        public SystemMsgList[] newArray(int size) {
            return new SystemMsgList[size];
        }
    };
}
