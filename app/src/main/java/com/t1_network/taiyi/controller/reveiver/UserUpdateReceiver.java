package com.t1_network.taiyi.controller.reveiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/14 13:34
 * 修改记录:
 */
public class UserUpdateReceiver extends BroadcastReceiver {


    private UserUpdateListener listener;

    public static final String ACTION_USER_UPDATE = "ACTION_USER_UPDATE";

    public interface UserUpdateListener {
        public void receiveUserUpdate();
    }

    public UserUpdateReceiver(UserUpdateListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(ACTION_USER_UPDATE)) {
            listener.receiveUserUpdate();
        }
    }

    public static void send(Context context) {
        Intent intent = new Intent(ACTION_USER_UPDATE);
        context.sendBroadcast(intent);
    }

    public static void register(Context context, UserUpdateReceiver receiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UserUpdateReceiver.ACTION_USER_UPDATE);
        context.registerReceiver(receiver, intentFilter);
    }

    public static void unregister(Context context, UserUpdateReceiver receiver) {
        if (receiver != null) {
            context.unregisterReceiver(receiver);
        }
    }


}
