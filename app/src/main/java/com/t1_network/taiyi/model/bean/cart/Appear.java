package com.t1_network.taiyi.model.bean.cart;

import com.google.gson.annotations.SerializedName;

/**
 * 商品外观图片
 */
public class Appear {

    /**
     * 图片id
     */
    @SerializedName("id")
    private String id;

    /**
     * 图片url
     */
    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
