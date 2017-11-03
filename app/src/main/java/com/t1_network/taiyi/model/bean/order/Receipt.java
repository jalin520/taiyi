package com.t1_network.taiyi.model.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by David on 15/12/18.
 */
public class Receipt implements Parcelable {

    private String title;

    private String phone;

    private String email;

    private String content;

    private String type;

    private boolean isPersonal = true;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public boolean isPersonal() {
        return isPersonal;
    }

    public void setIsPersonal(boolean isPersonal) {
        this.isPersonal = isPersonal;
    }

    public Receipt() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeString(this.content);
        dest.writeString(this.type);
        dest.writeByte(isPersonal ? (byte) 1 : (byte) 0);
    }

    protected Receipt(Parcel in) {
        this.title = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.content = in.readString();
        this.type = in.readString();
        this.isPersonal = in.readByte() != 0;
    }

    public static final Creator<Receipt> CREATOR = new Creator<Receipt>() {
        public Receipt createFromParcel(Parcel source) {
            return new Receipt(source);
        }

        public Receipt[] newArray(int size) {
            return new Receipt[size];
        }
    };
}
