package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;

import com.meiqia.core.MQManager;
import com.meiqia.core.callback.OnClientInfoCallback;
import com.meiqia.meiqiasdk.activity.MQConversationActivity;
import com.t1_network.core.app.App;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.User;

import java.util.HashMap;
import java.util.Map;

public class CustomerAct extends BasicAct {

    public CustomerAct() {
        super(R.layout.act_customer, R.string.title_activity_customer);
    }

    public static void startActivity(Context context) {

        init(context);
        Intent intent = new Intent(context, MQConversationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {

    }

    private static void init(Context context) {
        MQManager mqManager = MQManager.getInstance(context);


        if (App.getApp().isLogin()) {

            User user = App.getApp().getUser();

            Map<String, String> info = new HashMap<>();
            info.put("用户编号", user.getConsumer().getId());
            info.put("name", user.getConsumer().getUsername());

            info.put("tel", user.getConsumer().getPhone());
            info.put("sex", user.getConsumer().getSex());

            MQManager.getInstance(context).setClientInfo(info, new OnClientInfoCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(int i, String s) {

                }
            });


        }


    }


}
