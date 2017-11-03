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
public class UserCartReceiver extends BroadcastReceiver{

    public static String size;
    private UserComparReceiverListener mListener;

    public static final String CART_NUMBER = "CART_NUMBER";


    public interface UserComparReceiverListener{
        void receiverCompar();
    }

    public UserCartReceiver(UserComparReceiverListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(CART_NUMBER)){
            mListener.receiverCompar();
        }
    }


    //广播发送者
    public static void send(Context context,String size){
        UserCartReceiver.size = size;
        Intent intent = new Intent(CART_NUMBER);
        context.sendBroadcast(intent);
    }

    //广播接受者.
    public static void register(Context context,UserCartReceiver receiver){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UserCartReceiver.CART_NUMBER);
        context.registerReceiver(receiver, intentFilter);

    }

    public static void unregister(Context context , UserCartReceiver receiver){
        if (receiver != null)
            context.unregisterReceiver(receiver);
    }


}
