package com.t1_network.taiyi.controller.reveiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by David on 2016/1/19.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class UserComparReceiver extends BroadcastReceiver{

    private UserComparReceiverListener mListener;

    public static final String COMPARE_NUMBER = "COMPARE_NUMBER";

    public interface UserComparReceiverListener{
        void receiverCompar();
    }

    public UserComparReceiver(UserComparReceiverListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(COMPARE_NUMBER)){
            mListener.receiverCompar();
        }
    }

    //广播发送者
    public static void send(Context context){
        Intent intent = new Intent(COMPARE_NUMBER);
        context.sendBroadcast(intent);
    }

    //广播接受者.
    public static void register(Context context,UserComparReceiver receiver){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UserComparReceiver.COMPARE_NUMBER);
        context.registerReceiver(receiver, intentFilter);

    }

    public static void unregister(Context context , UserComparReceiver receiver){
        if (receiver != null)
            context.unregisterReceiver(receiver);
    }


}
