package com.t1_network.taiyi.model.bean.good;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/28 15:44
 * 修改记录:
 */
public class Comment implements Parcelable {

    @SerializedName("id")
    private String id; //评论id

    @SerializedName("username")
    private String username; //用户名

    @SerializedName("content")
    private String content;//评价内容

    @SerializedName("score")
    private String score; //评分

    @SerializedName("commenttime")
    private String time; //评价时间

    @SerializedName("pic")
    private List<CommentImage> imageList; //评价图片

    @SerializedName("replycontent")
    private String replyContent; //回复内容


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

    public List<CommentImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<CommentImage> imageList) {
        this.imageList = imageList;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
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
        dest.writeString(this.content);
        dest.writeString(this.score);
        dest.writeString(this.time);
        dest.writeTypedList(imageList);
        dest.writeString(this.replyContent);
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        this.id = in.readString();
        this.username = in.readString();
        this.content = in.readString();
        this.score = in.readString();
        this.time = in.readString();
        this.imageList = in.createTypedArrayList(CommentImage.CREATOR);
        this.replyContent = in.readString();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
