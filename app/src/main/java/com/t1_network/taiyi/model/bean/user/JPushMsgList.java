package com.t1_network.taiyi.model.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/25 13:30
 * 修改记录:
 */
public class JPushMsgList implements Parcelable {

    @SerializedName("result")
    private List<JPushMsg> msgList;

    public List<JPushMsg> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<JPushMsg> msgList) {
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

    public JPushMsgList() {
    }

    protected JPushMsgList(Parcel in) {
        this.msgList = in.createTypedArrayList(JPushMsg.CREATOR);
    }

    public static final Parcelable.Creator<JPushMsgList> CREATOR = new Parcelable.Creator<JPushMsgList>() {
        public JPushMsgList createFromParcel(Parcel source) {
            return new JPushMsgList(source);
        }

        public JPushMsgList[] newArray(int size) {
            return new JPushMsgList[size];
        }
    };
}
