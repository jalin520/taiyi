package com.t1_network.taiyi.model.event;

import com.t1_network.core.app.App;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.taiyi.db.TYSP;
import com.t1_network.taiyi.model.bean.User;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 功能: 登录事件 更改购物车,个人中心
 * 创建者: David
 * 创建日期: 16/1/21 17:09
 * 修改记录:
 */
public class LoginEvent {

    private User user;

    public LoginEvent(User user) {
        this.user = user;
        App.getApp().setUser(user);
        TYSP.setToken(user.getConsumer().getToken());

        JPushInterface.setAliasAndTags(App.getContext(), user.getConsumer().getId(), null, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                LogUtils.e("JPUSH:" + s + " " + "status" + i);
            }
        });

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
