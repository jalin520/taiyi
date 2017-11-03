package com.t1_network.taiyi.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by David on 15/11/17.
 */
public class Classfication implements Parcelable {

    private String icon;
    private String name;
    private List<ClassItem> classItemList;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClassItem> getClassItemList() {
        return classItemList;
    }

    public void setClassItemList(List<ClassItem> classItemList) {
        this.classItemList = classItemList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon);
        dest.writeString(this.name);
        dest.writeTypedList(classItemList);
    }

    public Classfication() {
    }

    protected Classfication(Parcel in) {
        this.icon = in.readString();
        this.name = in.readString();
        this.classItemList = in.createTypedArrayList(ClassItem.CREATOR);
    }

    public static final Creator<Classfication> CREATOR = new Creator<Classfication>() {
        public Classfication createFromParcel(Parcel source) {
            return new Classfication(source);
        }

        public Classfication[] newArray(int size) {
            return new Classfication[size];
        }
    };
}
