package com.t1_network.taiyi.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Test
 */
public class ClassItem implements Parcelable {

    private String image;

    private long id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ClassItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeLong(this.id);
        dest.writeString(this.name);
    }

    protected ClassItem(Parcel in) {
        this.image = in.readString();
        this.id = in.readLong();
        this.name = in.readString();
    }

    public static final Creator<ClassItem> CREATOR = new Creator<ClassItem>() {
        public ClassItem createFromParcel(Parcel source) {
            return new ClassItem(source);
        }

        public ClassItem[] newArray(int size) {
            return new ClassItem[size];
        }
    };
}
