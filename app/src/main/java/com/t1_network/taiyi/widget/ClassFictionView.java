package com.t1_network.taiyi.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.UIUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.activity.GoodListAct;
import com.t1_network.taiyi.model.bean.home.Classfication;

import java.util.List;

public class ClassFictionView extends LinearLayout {

    private TextView mTv;
    private ImageView mImageView;
    public GridLayout mGl;

    private Context context;
    public RelativeLayout mRl;

    private boolean isSelected;

    private boolean isVisible;


    public ClassFictionView(Context context) {
        this(context, null);
    }

    public ClassFictionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initView(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClassFictionView);
        if (ta.hasValue(R.styleable.ClassFictionView_mImageView)) {
            int resourceId = ta.getResourceId(R.styleable.ClassFictionView_mImageView, R.drawable.ic_pack_up_2x);
            mImageView.setImageResource(resourceId);
        }
        if (ta.hasValue(R.styleable.ClassFictionView_isVisible)) {
            isVisible = ta.getBoolean(R.styleable.ClassFictionView_isVisible, false);
            if (isVisible) {
                mTv.setSelected(isSelected);
                mTv.setTextColor(ResUtils.getColor(R.color.text_green));
                mImageView.setImageResource(R.drawable.ic_pack_up_2x);
                mGl.setVisibility(VISIBLE);

            } else {
                mGl.setVisibility(GONE);
            }
        }

        this.mTv.setText(ta.getString(R.styleable.ClassFictionView_mTv));


    }

    private List<Classfication> data;

    public void setData(List<Classfication> data) {
        this.data = data;
        initGridLayout(data);
    }

    private void initGridLayout(List<Classfication> data) {

        if (data == null) {
            return;
        }

        for (int i = 0; i < data.size(); i++) {

            Classfication classfication = data.get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.item_frg_class_fication_adapter_item_text, null);
            TextView tv = (TextView) view.findViewById(R.id.item_frg_rv_text);
            tv.setText(classfication.getName());

            GridLayout.LayoutParams gl = new GridLayout.LayoutParams();
            gl.width = (UIUtils.getWidthByPx() - UIUtils.dp2Px(16)) / 3;
            mGl.addView(view, gl);

            if (!view.hasOnClickListeners()) {
                final int finalI = i;
                view.setOnClickListener(new OnClickItemListener(classfication));
            }
        }


    }

    private void initView(Context context) {
        View inflate = View.inflate(context, R.layout.item_frg_class_fication, this);
        mRl = (RelativeLayout) inflate.findViewById(R.id.frg_classfication_rl_btn);
        mTv = (TextView) inflate.findViewById(R.id.item_classfication_text_type);
        mImageView = (ImageView) inflate.findViewById(R.id.item_classfication_image_icon);
        mGl = (GridLayout) inflate.findViewById(R.id.frg_classfication_gl_disease);
    }


    public void setTv(String tv) {
        mTv.setText(tv);
    }

    public void setImageView(Integer imageView) {
        mImageView.setImageResource(imageView);
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void visibleGridView() {
        mTv.setSelected(isSelected);
        mTv.setTextColor(ResUtils.getColor(R.color.text_gray_deep));
        mImageView.setImageResource(R.drawable.ic_pack_down_2x);
        mGl.setVisibility(VISIBLE);
    }

    public void setTextType() {
        if (mGl.getVisibility() == View.GONE) {
            mTv.setSelected(isSelected);
            mTv.setTextColor(ResUtils.getColor(R.color.text_gray_deep));
            mImageView.setImageResource(R.drawable.ic_pack_down_2x);

        } else {

            mTv.setSelected(isSelected);
            mTv.setTextColor(ResUtils.getColor(R.color.text_green));
            mImageView.setImageResource(R.drawable.ic_pack_up_2x);
        }
    }

    private class OnClickItemListener implements OnClickListener {

        private Classfication classfication;

        public OnClickItemListener(Classfication classfication) {
            this.classfication = classfication;
        }

        @Override
        public void onClick(View v) {
            GoodListAct.startActivityFromClassfication(context, classfication.getId(), classfication.getName());
        }
    }


}
