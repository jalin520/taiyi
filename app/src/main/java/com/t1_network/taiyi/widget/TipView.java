package com.t1_network.taiyi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.t1_network.taiyi.R;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/12 13:57
 * 修改记录:
 */
public class TipView extends LinearLayout {


    private ImageView imageTip;
    private TextView textTip;

    public TipView(Context context) {
        super(context);
        init(context);
    }

    public TipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.c_tip, this);
        imageTip = (ImageView) view.findViewById(R.id.c_tip_image);
        textTip = (TextView) view.findViewById(R.id.c_tip_text);
    }

    public void showNoData() {
        this.setVisibility(View.VISIBLE);
        imageTip.setImageResource(R.drawable.default_no_data);
        textTip.setText("这里什么都没有哦!");
    }

    public void showError() {
        this.setVisibility(View.VISIBLE);
        imageTip.setImageResource(R.drawable.default_error);
        textTip.setText("网络异常,请重新加载");
    }

    public void hide() {
        this.setVisibility(View.GONE);
    }

}
