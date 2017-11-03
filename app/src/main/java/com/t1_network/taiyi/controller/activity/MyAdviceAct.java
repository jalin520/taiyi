package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.user.JPushMsg;

public class MyAdviceAct extends BasicAct  {


    private static final String JPUSHMSG = "JPUSHMSG";

    public MyAdviceAct() {
        super(R.layout.activity_my_advice, R.string.title_activity_my_advice, true);
    }

    public static void startActivity(Context context, JPushMsg jPushMsg) {
        Intent intent = new Intent(context, MyAdviceAct.class);
        intent.putExtra(JPUSHMSG,jPushMsg);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        Intent intent = this.getIntent();
        JPushMsg pushMsg =intent.getParcelableExtra(JPUSHMSG);
        LogUtils.e(pushMsg.getId()+"");
    }
}
