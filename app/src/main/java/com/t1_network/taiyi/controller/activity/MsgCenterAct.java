package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.user.JPushMsg;
import com.t1_network.taiyi.model.bean.user.SystemMsg;
import com.t1_network.taiyi.net.api.user.JPushMsgAPI;
import com.t1_network.taiyi.net.api.user.SystemMsgAPI;
import com.t1_network.taiyi.widget.MessageView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MsgCenterAct extends BasicAct implements JPushMsgAPI.JPushMsgAPIListener, SystemMsgAPI.SystemMsgAPIListener {

    private int countDownLatch = 1;

    public MsgCenterAct() {
        super(R.layout.act_msg_center, R.string.title_activity_msg_center);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MsgCenterAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        dialog.show();

        new JPushMsgAPI(this, 0, 1);
//        new SystemMsgAPI(this, 0, 1);
    }

    @Bind(R.id.act_msg_center_view_inner_message)
    MessageView innerView;

    @Bind(R.id.act_msg_center_view_system_message)
    MessageView systemView;

    @OnClick(R.id.act_msg_center_view_inner_message)
    public void innerMessage() {
        InnerActMsg.startActivity(this);
    }

    @OnClick(R.id.act_msg_center_view_system_message)
    public void systemMessage() {
        SystemMessageAct.startActivity(this);
    }


    @Override
    public void apiJPushMsgFailure(long code, String msg) {
        countDownLatch--;
        systemView.setVisibility(View.GONE);
        if (countDownLatch == 0) {
            dialog.dismiss();
        }
    }

    @Override
    public void apiJPushMsgSuccess(List<JPushMsg> jPushMsgList) {
        countDownLatch--;

        if (jPushMsgList.size() == 0) {

            systemView.setVisibility(View.GONE);
            return;
        }

        JPushMsg msg = jPushMsgList.get(0);

        systemView.setTime(msg.getTime());
        systemView.setContent(msg.getContent());


        systemView.setVisibility(View.VISIBLE);
        if (countDownLatch == 0) {
            dialog.dismiss();
        }
    }

    @Override
    public void apiSystemMsgSuccess(List<SystemMsg> systemMsgLists) {
        countDownLatch--;
        if (systemMsgLists.size() == 0) {

            innerView.setVisibility(View.GONE);
            return;
        }

        SystemMsg msg = systemMsgLists.get(0);

        innerView.setTime(msg.getTime());
        innerView.setContent(msg.getContent());

        innerView.setVisibility(View.VISIBLE);
        if (countDownLatch == 0) {
            dialog.dismiss();
        }
    }

    @Override
    public void apiSystemMsgFailure(long code, String msg) {
        countDownLatch--;
        innerView.setVisibility(View.GONE);
        if (countDownLatch == 0) {
            dialog.dismiss();
        }
    }
}
