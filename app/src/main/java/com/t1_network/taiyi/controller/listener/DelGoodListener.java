package com.t1_network.taiyi.controller.listener;

import android.view.View;
import android.widget.TextView;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/29 15:18
 * 修改记录:
 */

/**
 * 点击减少商品按钮
 */
public class DelGoodListener implements View.OnClickListener {

    private TextView textNum;

    public DelGoodListener(TextView textNum) {
        this.textNum = textNum;
    }

    @Override
    public void onClick(View v) {

        String strNum = textNum.getText().toString();

        int num = Integer.parseInt(strNum);

        if (num >= 2) {
            num--;
            textNum.setText(num + "");
        }
    }

}
