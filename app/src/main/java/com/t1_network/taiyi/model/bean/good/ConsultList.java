package com.t1_network.taiyi.model.bean.good;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/29 10:47
 * 修改记录:
 */
public class ConsultList implements Parcelable {

    @SerializedName("result")
    private List<Consult> consultList;

    public List<Consult> getConsultList() {
        return consultList;
    }

    public void setConsultList(List<Consult> consultList) {
        this.consultList = consultList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(consultList);
    }

    public ConsultList() {
    }

    protected ConsultList(Parcel in) {
        this.consultList = in.createTypedArrayList(Consult.CREATOR);
    }

    public static final Creator<ConsultList> CREATOR = new Creator<ConsultList>() {
        public ConsultList createFromParcel(Parcel source) {
            return new ConsultList(source);
        }

        public ConsultList[] newArray(int size) {
            return new ConsultList[size];
        }
    };
}
