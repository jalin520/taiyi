package com.t1_network.taiyi.model.bean.good;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/29 10:41
 * 修改记录:
 */
public class Consult implements Parcelable {

    @SerializedName("id")
    private String id;//id

    private String username = "接口没返回用户名"; //用户名

    @SerializedName("content")
    private String question; //问题

    @SerializedName("replycontent")
    private String answer; //答案

    @SerializedName("conmsulttime")
    private String time; //咨询时间

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.question);
        dest.writeString(this.answer);
        dest.writeString(this.time);
    }

    public Consult() {
    }

    protected Consult(Parcel in) {
        this.id = in.readString();
        this.username = in.readString();
        this.question = in.readString();
        this.answer = in.readString();
        this.time = in.readString();
    }

    public static final Parcelable.Creator<Consult> CREATOR = new Parcelable.Creator<Consult>() {
        public Consult createFromParcel(Parcel source) {
            return new Consult(source);
        }

        public Consult[] newArray(int size) {
            return new Consult[size];
        }
    };
}
