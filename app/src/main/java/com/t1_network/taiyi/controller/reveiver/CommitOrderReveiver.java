package com.t1_network.taiyi.controller.reveiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by David on 15/12/22.
 */
public class CommitOrderReveiver extends BroadcastReceiver {

    private CommitOrderListener listener;

    public static final String ACTION_COMMIT_ORDER = "ACTION_COMMIT_ORDER";

    public interface CommitOrderListener {
        public void receiveCommitOrder();
    }

    public CommitOrderReveiver(CommitOrderListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(ACTION_COMMIT_ORDER)) {
            listener.receiveCommitOrder();
        }
    }


    public static void send(Context context) {
        Intent intent = new Intent(ACTION_COMMIT_ORDER);
        context.sendBroadcast(intent);
    }

    public static void register(Context context, CommitOrderReveiver receiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CommitOrderReveiver.ACTION_COMMIT_ORDER);
        context.registerReceiver(receiver, intentFilter);
    }

    public static void unregister(Context context, CommitOrderReveiver receiver) {
        if (receiver != null) {
            context.unregisterReceiver(receiver);
        }
    }
}
