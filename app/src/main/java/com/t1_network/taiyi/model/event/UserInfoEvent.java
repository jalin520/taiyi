package com.t1_network.taiyi.model.event;

import com.t1_network.core.app.App;
import com.t1_network.taiyi.model.bean.User;

/**
 * 功能: 登出事件
 * 创建者: David
 * 创建日期: 16/1/21 17:10
 * 修改记录:
 */
public class UserInfoEvent {
    private User user;
    public UserInfoEvent(User user) {
        this.user = user;
        App.getApp().setUser(user);
    }

}
