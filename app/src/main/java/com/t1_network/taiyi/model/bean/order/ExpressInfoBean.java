package com.t1_network.taiyi.model.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 2016/1/22.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class ExpressInfoBean implements Parcelable {


    @SerializedName("company")
    private String company;
    @SerializedName("delivery")

    private String delivery;

    @SerializedName("info")
    private List<InfoEntity> info;

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public void setInfo(List<InfoEntity> info) {
        this.info = info;
    }

    public String getCompany() {
        return company;
    }

    public String getDelivery() {
        return delivery;
    }

    public List<InfoEntity> getInfo() {
        return info;
    }

    public static class InfoEntity {
        @SerializedName("content")
        private String content;
        @SerializedName("time")
        private String time;

        public void setContent(String content) {
            this.content = content;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public String getTime() {
            return time;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.company);
        dest.writeString(this.delivery);
        dest.writeList(this.info);
    }

    public ExpressInfoBean() {
    }

    protected ExpressInfoBean(Parcel in) {
        this.company = in.readString();
        this.delivery = in.readString();
        this.info = new ArrayList<InfoEntity>();
        in.readList(this.info, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<ExpressInfoBean> CREATOR = new Parcelable.Creator<ExpressInfoBean>() {
        public ExpressInfoBean createFromParcel(Parcel source) {
            return new ExpressInfoBean(source);
        }

        public ExpressInfoBean[] newArray(int size) {
            return new ExpressInfoBean[size];
        }
    };
}
