package com.t1_network.taiyi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.t1_network.taiyi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by David on 15/11/6.
 */
public class MessageView extends LinearLayout {

    @Bind(R.id.item_message_center_image_avatar)
    ImageView imageIcon;
    @Bind(R.id.item_message_center_text_title)
    TextView textTitle;
    @Bind(R.id.item_message_center_text_msg)
    TextView textContent;
    @Bind(R.id.item_message_center_text_time)
    TextView textTime;

    private String pastName = "http://schemas.android.com/apk/res/com.t1_network.taiyi.wigite";


    public MessageView(Context context) {
        super(context);
        init(context, null);
    }

    public MessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initView(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MessageView);

        if (ta.hasValue(R.styleable.MessageView_messageIcon)) {
            int resid = ta.getResourceId(R.styleable.MessageView_messageIcon, 0);
            imageIcon.setImageResource(resid);
            imageIcon.setVisibility(View.VISIBLE);
        } else {
            imageIcon.setVisibility(View.GONE);
        }

        if (ta.hasValue(R.styleable.MessageView_messageTitle)) {
            String string = ta.getString(R.styleable.MessageView_messageTitle);
            textTitle.setText(string);
        } else {
            textTitle.setText("");
        }
        if (ta.hasValue(R.styleable.MessageView_content)) {
            String string = ta.getString(R.styleable.MessageView_content);
            textContent.setText(string);
        } else {
            textContent.setText("");
        }
        if (ta.hasValue(R.styleable.MessageView_time)) {
            String string = ta.getString(R.styleable.MessageView_time);
            textTime.setText(string);
        } else {
            textTime.setText("");
        }
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.item_message_center, this);
        ButterKnife.bind(view);
    }


    public void setContent(String content) {
        this.textContent.setText(content);
    }

    public void setTime(String time) {
        this.textTime.setText(time);

    }
}