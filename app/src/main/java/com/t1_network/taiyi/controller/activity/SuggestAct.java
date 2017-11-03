package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.net.api.user.FeekbackAPI;

import butterknife.Bind;

public class SuggestAct extends BasicAct implements View.OnClickListener, FeekbackAPI.FeekbackAPIListener {

    @Bind(R.id.act_suggest_et_content)
    EditText etContent;

    @Bind(R.id.act_suggest_et_name)
    EditText etName;

    @Bind(R.id.act_suggest_et_contact_phone)
    EditText etPhone;

    @Bind(R.id.act_suggest_ll_input)
    LinearLayout llInput;

    public SuggestAct() {
        super(R.layout.act_suggest, R.string.title_activity_suggest);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SuggestAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {


        llInput.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String content = etContent.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String contact = etPhone.getText().toString().trim();


        switch (id){
            case R.id.act_suggest_ll_input:
                if (TextUtils.isEmpty(content)){
                    showTip(ResUtils.getString(R.string.title_activity_suggest_content));
                    return;
                }
                if (TextUtils.isEmpty(name)){
                    showTip(ResUtils.getString(R.string.title_activity_suggest_name));
                    return;
                }
                if (TextUtils.isEmpty(contact)){
                    showTip(ResUtils.getString(R.string.title_activity_suggest_contact));
                    return;
                }

                new FeekbackAPI(this,content,contact,name);


                break;
        }
    }

    @Override
    public void apiFeekbackSuccess() {
        showTip("信息已成功发送,我们会尽快解决问题,谢谢您的宝贵意见");
        finish();
    }

    @Override
    public void apiFeekbackFailure(long code, String msg) {
        showTip("信息发送失败"+msg);
    }
}
