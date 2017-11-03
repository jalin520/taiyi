package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.net.api.good.AddConsultAPI;

import butterknife.Bind;

public class InputConsultAct extends BasicAct implements View.OnClickListener, AddConsultAPI.AddConsultListener {

    @Bind(R.id.act_input_consult_et_content)
    EditText etContent;

    @Bind(R.id.act_input_consult_ll_input)
    LinearLayout llInput;

    private String goodid;

    public InputConsultAct() {
        super(R.layout.act_input_consult, R.string.title_activity_input_consult);
    }

    public static void startActivity(Context context, String id) {
        Intent intent = new Intent(context, InputConsultAct.class);
        intent.putExtra("GOODID", id);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        Intent intent = this.getIntent();
        String goodid = intent.getStringExtra("GOODID");
        this.goodid = goodid;


        llInput.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String contentValue = etContent.getText().toString();
        if (contentValue == null || contentValue.length() == 0) {
            showTip("请输入咨询内容");
        } else {

            new AddConsultAPI(this, goodid, contentValue);

        }

    }

    @Override
    public void addConsultSuccess() {
        showTip("发表成功");
        finish();
    }

    @Override
    public void addConsultError(long code, String msg) {
        showTip(msg);
    }
}
