package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.core.utils.UIUtils;
import com.t1_network.taiyi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class DeliverGoodsAct extends BasicAct implements View.OnClickListener {

    @Bind(R.id.act_content_deliver_goods_gl_disease)
    GridLayout gl;

    @Bind(R.id.act_content_deliver_goods_rl)
    RelativeLayout rl;

    private  List<String> mDatas ;

    public DeliverGoodsAct() {
        super(R.layout.activity_deliver_goods, R.string.act_deliver_goods_tb_title);
    }

    public static void startDeliverAct(Context context){
        Intent intent = new Intent(context,DeliverGoodsAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        mDatas = getList();
        initGridLayout(mDatas);
        rl.setOnClickListener(this);


    }

    private void initGridLayout(List<String> mDatas) {
        for (int i = 0; i < 5; i++) {
            mDatas.add("title" + i);
            View view = LayoutInflater.from(this).inflate(R.layout.item_frg_class_fication_adapter_item_text, null);
            TextView tv = (TextView) view.findViewById(R.id.item_frg_rv_text);
            tv.setText(mDatas.get(i));

            GridLayout.LayoutParams glParams = new GridLayout.LayoutParams();
            glParams.width = (UIUtils.getWidthByPx() - UIUtils.dp2Px(16)) / 3;
            gl.addView(view, glParams);
            if (!view.hasOnClickListeners()) {
                final int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TipUtils.toast("我是item" + finalI);
                    }
                });
            }

        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.act_content_deliver_goods_rl:
                if (gl.getVisibility() == View.VISIBLE){
                    gl.setVisibility(View.GONE);
                }else{
                    gl.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    public List<String> getList() {
        mDatas = new ArrayList<>();
        mDatas.add("顺风快递");
        mDatas.add("顺风快递");
        mDatas.add("顺风快递");
        mDatas.add("顺风快递");
        mDatas.add("顺风快递");
        return mDatas;
    }
}
