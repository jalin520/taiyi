package com.t1_network.taiyi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.t1_network.taiyi.R;

/**
 * Created by David on 2015/11/25.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class ComparInfoView extends LinearLayout {


    private TextView mTitle;
    private TextView mLeftName;
    private TextView mRightName;

    public ComparInfoView(Context context) {
        this(context, null);

    }


    public ComparInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ComparInfoView);
        this.mTitle.setText(ta.getString(R.styleable.ComparInfoView_mTitle));
        this.mLeftName.setText(ta.getString(R.styleable.ComparInfoView_mLeftName));
        this.mRightName.setText(ta.getString(R.styleable.ComparInfoView_mRightName));
    }


    /**
     * 绑定组件@
     *
     * @param context
     */
    private void initView(Context context) {
        View view = View.inflate(context, R.layout.item_compare_info_widget, this);
        mTitle = (TextView) view.findViewById(R.id.item_compar_good_title);
        mLeftName = (TextView) view.findViewById(R.id.item_compar_good_info_left);
        mRightName = (TextView) view.findViewById(R.id.item_compar_good_info_right);
    }

    public void setTitle(String title) {
        mTitle .setText(title);
    }

    public void setLeftName(String leftName) {
        mLeftName .setText(leftName);
    }

    public void setRightName(String rightName) {
        mRightName.setText(rightName);
    }
}
