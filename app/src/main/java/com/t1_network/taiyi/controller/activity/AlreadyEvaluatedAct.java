package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.user.JPushMsg;

public class AlreadyEvaluatedAct extends BasicAct {

    private static final String JPUSHMSG = "JPUSHMSG";

    public AlreadyEvaluatedAct() {
        super(R.layout.activity_already_evaluated, R.string.title_activity_already_evaluated, true);
    }

    public static void startActivity(Context context, JPushMsg jPushMsg) {
        Intent intent = new Intent(context, AlreadyEvaluatedAct.class);
        intent.putExtra(JPUSHMSG,jPushMsg);
        context.startActivity(intent);
    }

    @Override
    public void initView() {


    }
}
