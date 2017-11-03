package com.t1_network.taiyi.model.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private Login login;

    private Consumer consumer;

    public void setLogin(Login login) {
        this.login = login;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Login getLogin() {
        return login;
    }

    public Consumer getConsumer() {
        return consumer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.login, 0);
        dest.writeParcelable(this.consumer, 0);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.login = in.readParcelable(Login.class.getClassLoader());
        this.consumer = in.readParcelable(Consumer.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
