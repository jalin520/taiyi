package com.t1_network.taiyi.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.t1_network.taiyi.R;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/23 10:22
 * 修改记录:
 */
public class TYProgressDialog extends Dialog {

    private Context context;

    public TYProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public TYProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }


    /**
     * 获取一个 TYProgressDialog 对象
     *
     * @return
     */
    public static TYProgressDialog createDialog(Context context) {

        TYProgressDialog dialog = new TYProgressDialog(context, R.style.CustomProgressDialog);
        dialog.setContentView(R.layout.c_ty_progress_dialog);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        return dialog;
    }

}
