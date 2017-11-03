package com.t1_network.taiyi.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.verify.ExchangeTokenVerify;
import com.t1_network.taiyi.model.verify.VerifyResult;
import com.t1_network.taiyi.net.api.voucher.ExchangeVoucherAPI;

import butterknife.Bind;
import butterknife.OnClick;

public class ExchangeTokenAct extends BasicAct implements ExchangeVoucherAPI.ExchangeVoucherAPIListener {

    public ExchangeTokenAct() {
        super(R.layout.act_exchange_token, R.string.title_activity_exchange_token);
    }

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, ExchangeTokenAct.class);
        context.startActivityForResult(intent, 1);
    }

    @Override
    public void initView() {

    }

    @Bind(R.id.act_exchange_token_hint_input_token)
    EditText editTokenId;

    @OnClick(R.id.act_exchange_token_btn_exchange)
    public void exchange() {

        String tokenId = editTokenId.getText().toString();

        VerifyResult result = ExchangeTokenVerify.verify(tokenId);

        if (!result.getResult()) {
            showTip(result.getErrorMsg());
            return;
        }

        new ExchangeVoucherAPI(this, tokenId);
    }

    @Override
    public void apiExchangeVoucherSuccess() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void apiExchangeVoucherFailure(long code, String msg) {
        showTip(msg);
    }
}
