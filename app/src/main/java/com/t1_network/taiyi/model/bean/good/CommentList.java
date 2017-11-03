package com.t1_network.taiyi.model.bean.good;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/28 16:06
 * 修改记录:
 */
public class CommentList implements Parcelable {

    @SerializedName("result")
    private List<Comment> commentList;

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(commentList);
    }

    public CommentList() {
    }

    protected CommentList(Parcel in) {
        this.commentList = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Parcelable.Creator<CommentList> CREATOR = new Parcelable.Creator<CommentList>() {
        public CommentList createFromParcel(Parcel source) {
            return new CommentList(source);
        }

        public CommentList[] newArray(int size) {
            return new CommentList[size];
        }
    };
}
