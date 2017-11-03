package com.t1_network.taiyi.model.bean.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.t1_network.core.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/23 11:34
 * 修改记录:
 */
public class Classfication implements Parcelable {

    //id

    private String id;
    //分类名称
    private String name;
    //子数组
    private List<Classfication> childList;

    /**
     * 将分类接口返回的数据解析成分类列表
     *
     * @param data
     * @return
     */
    public static List<Classfication> resloveClassfication(String data) {

        try {
            JSONArray result = JsonUtils.getJSONArray(new JSONObject(data), "result");
            return resloveClassficationList(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<Classfication>();
    }

    /**
     * 将分类的json数组解析成对象数组,多层采用递归
     *
     * @param data
     * @return
     */
    public static List<Classfication> resloveClassficationList(JSONArray data) {

        List<Classfication> classficationList = new ArrayList<Classfication>();

        try {

            for (int i = 0; i < data.length(); i++) {

                JSONObject obj = data.getJSONObject(i);

                Classfication classfication = new Classfication();
                classfication.setId(JsonUtils.getString(obj, "id"));
                classfication.setName(JsonUtils.getString(obj, "name"));

                if (obj.has("category")) {
                    classfication.setChildList(resloveClassficationList(obj.getJSONArray("category")));
                }

                classficationList.add(classfication);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return classficationList;
    }


    public List<Classfication> getChildList() {
        return childList;
    }

    public void setChildList(List<Classfication> childList) {
        this.childList = childList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(childList);
    }

    public Classfication() {
    }

    protected Classfication(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.childList = in.createTypedArrayList(Classfication.CREATOR);
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
