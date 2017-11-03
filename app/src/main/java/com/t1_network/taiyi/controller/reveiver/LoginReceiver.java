package com.t1_network.taiyi.controller.reveiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by David on 15/11/13.
 */
public class LoginReceiver extends BroadcastReceiver {


    private LoginListener listener;

    public static final String ACTION_LOGIN = "ACTION_LOGIN";

    public LoginReceiver(LoginListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (action.equals(ACTION_LOGIN)) {
            listener.reveiverLogin();
        }

    }

    public interface LoginListener {
        public void reveiverLogin();
    }

    public static void send(Context context) {
        Intent intent = new Intent(ACTION_LOGIN);
        context.sendBroadcast(intent);
    }

    public static void register(Context context, LoginReceiver loginReceiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LoginReceiver.ACTION_LOGIN);
        context.registerReceiver(loginReceiver, intentFilter);
    }

    public static void unregister(Context context, LoginReceiver loginReceiver) {
        if (loginReceiver != null) {
            context.unregisterReceiver(loginReceiver);
        }
    }

}
