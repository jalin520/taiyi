package com.t1_network.taiyi.controller.reveiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.t1_network.core.utils.LogUtils;
import com.t1_network.taiyi.controller.activity.SystemMessageAct;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by David on 15/11/5.
 */
public class JPushReceiver extends BroadcastReceiver {

    public static final String ACTION_JPUSH = JPushInterface.ACTION_NOTIFICATION_OPENED;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (action.equals(ACTION_JPUSH)) {
            LogUtils.e("打开了通知");

            Intent i = new Intent(context, SystemMessageAct.class);  //自定义打开的界面
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }

    }

}
