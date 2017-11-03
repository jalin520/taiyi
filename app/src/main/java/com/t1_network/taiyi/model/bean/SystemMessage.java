package com.t1_network.taiyi.model.bean;

/**
 * Created by chenyu on 2015/11/10.
 */
public class SystemMessage {
    private String goodName;
    private String systemMessageState;
    private String systemMessageDetail;
    private String systemMessageTime;

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getSystemMessageState() {
        return systemMessageState;
    }

    public void setSystemMessageState(String systemMessageState) {
        this.systemMessageState = systemMessageState;
    }

    public String getSystemMessageDetail() {
        return systemMessageDetail;
    }

    public void setSystemMessageDetail(String systemMessageDetail) {
        this.systemMessageDetail = systemMessageDetail;
    }

    public String getSystemMessageTime() {
        return systemMessageTime;
    }

    public void setSystemMessageTime(String systemMessageTime) {
        this.systemMessageTime = systemMessageTime;
    }
}
