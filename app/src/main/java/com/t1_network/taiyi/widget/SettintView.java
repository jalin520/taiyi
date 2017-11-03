package com.t1_network.taiyi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.t1_network.taiyi.R;


/**
 * 功能：item 用户设置风格的界面的item，以条形展示，可设置icon
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015/10/26.
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 * 1      2015-11-02        樊辉达         1.规范了代码格式；2.修复了构造方法中存在的Bug
 * 2      2015-11-06        樊辉达         1.拓展为可设置左边icon
 */
public class SettintView extends LinearLayout {

    private TextView textName;
    private String pastName = "http://schemas.android.com/apk/res/com.t1_network.taiyi.wigite";

    private View viewTopLineFull;
    private View viewTopLineWrap;
    private View viewBottomLineFull;
    private ImageView imageIcon;
    private View mView;

    public SettintView(Context context) {
        super(context);
        init(context, null);
    }

    public SettintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SettintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        initView(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SettingView);

        boolean showTopLineFull = false;
        boolean showTopLineWrap = false;
        boolean showBottomLineFull = false;


        if (ta.hasValue(R.styleable.SettingView_show_top_line_wrap)) {
            showTopLineWrap = ta.getBoolean(R.styleable.SettingView_show_top_line_wrap, false);
        }
        if (ta.hasValue(R.styleable.SettingView_show_top_line_full)) {
            showTopLineFull = ta.getBoolean(R.styleable.SettingView_show_top_line_full, false);
        }
        if (ta.hasValue(R.styleable.SettingView_show_bottom_line_full)) {
            showBottomLineFull = ta.getBoolean(R.styleable.SettingView_show_bottom_line_full, false);
        }

        if (ta.hasValue(R.styleable.SettingView_src)) {
            int resid = ta.getResourceId(R.styleable.SettingView_src, 0);
            imageIcon.setImageResource(resid);
            imageIcon.setVisibility(View.VISIBLE);
        } else {
            imageIcon.setVisibility(View.GONE);
        }

        if (showBottomLineFull) {
            viewBottomLineFull.setVisibility(View.VISIBLE);
        } else {
            viewBottomLineFull.setVisibility(View.GONE);
        }

        if (showTopLineFull) {
            viewTopLineFull.setVisibility(View.VISIBLE);
        } else {
            viewTopLineFull.setVisibility(View.GONE);
        }

        if (showTopLineWrap) {
            viewTopLineWrap.setVisibility(View.VISIBLE);
        } else {
            viewTopLineWrap.setVisibility(View.GONE);
        }

        this.textName.setText(ta.getString(R.styleable.SettingView_text));
//        float height = ta.getDimension(R.styleable.SettingView_set_height, R.dimen.c_setting_view_item_height);
//        mView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, (int) height));
//        mView.setLayoutParams(new linearlayout );
    }

    /**
     * 绑定组件@
     *
     * @param context
     */
    private void initView(Context context) {
        mView = View.inflate(context, R.layout.c_setting_view, this);
        textName = (TextView) mView.findViewById(R.id.item_setting_text_title);
        viewTopLineFull = (View) mView.findViewById(R.id.c_setting_view_top_line_full);
        viewTopLineWrap = (View) mView.findViewById(R.id.c_setting_view_top_line_wrap);
        viewBottomLineFull = (View) mView.findViewById(R.id.c_setting_view_bottom_line_full);
        imageIcon = (ImageView) mView.findViewById(R.id.c_setting_view_image_icon);
    }

    /**
     * 设置文字
     *
     * @param id
     */
    public void setText(int id) {
        this.textName.setText(id);
    }

    public void setHeight(int height){
        mView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,height));
    }
}
