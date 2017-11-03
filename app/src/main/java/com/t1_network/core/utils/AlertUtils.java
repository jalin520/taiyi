package com.t1_network.core.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.t1_network.core.app.App;
import com.t1_network.taiyi.R;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/13 17:38
 * 修改记录:
 */
public class AlertUtils {


    /**
     * 调用此方法需要提供一个id为 c_alert_btn_ok
     *
     * @param context
     * @param resId
     */
    public void alertDialog(Context context, int resId) {

        View viewDialog = LayoutInflater.from(context).inflate(resId, null);


        AlertDialog.Builder
                builder = new AlertDialog.Builder(App.getContext()).setView(viewDialog)
                .setCancelable(true);
        final Dialog dialog = builder.show();
    }


    public static Dialog comfirmDialog(Context context, int titleResId, String content, View.OnClickListener okListener, boolean hasCancelListener) {
        return comfirmDialog(context, titleResId, content, R.string.c_alert_btn_ok, R.string.c_alert_btn_cancel, okListener, hasCancelListener);
    }

    public static Dialog comfirmDialog(Context context, int titleResId, int contentId, View.OnClickListener okListener, boolean hasCancelListener) {
        return comfirmDialog(context, titleResId, ResUtils.getString(contentId), R.string.c_alert_btn_ok, R.string.c_alert_btn_cancel, okListener, hasCancelListener);
    }

    public static Dialog comfirmDialog(Context context, int contentId, View.OnClickListener okListener, boolean hasCancelListener) {
        return comfirmDialog(context, R.string.c_alert_title, ResUtils.getString(contentId), R.string.c_alert_btn_ok, R.string.c_alert_btn_cancel, okListener, hasCancelListener);
    }


    public static Dialog comfirmDialog4Input(Context context, int titleResId, String content, DialogInputListener mlistener, boolean hasCancelListener) {
        return comfirmDialog4Input(context, titleResId, content, R.string.c_alert_btn_ok, R.string.c_alert_btn_cancel, mlistener, hasCancelListener);
    }

    public static Dialog comfirmDialog(Context context, int titleResId, String content, int okResId, int cancelResId, View.OnClickListener okListener, boolean hasCancelListener) {

        View viewDialog = LayoutInflater.from(context).inflate(R.layout.c_alert, null);

        Button ok = (Button) viewDialog.findViewById(R.id.c_alert_btn_ok);
        Button cancel = (Button) viewDialog.findViewById(R.id.c_alert_btn_cancel);
        TextView tvContent = (TextView) viewDialog.findViewById(R.id.c_alert_text);
        TextView tvTitle = (TextView) viewDialog.findViewById(R.id.c_alert_title);

        ok.setText(ResUtils.getString(okResId));
        tvTitle.setText(ResUtils.getString(titleResId));
        tvContent.setText(content);
        cancel.setText(ResUtils.getString(cancelResId));

        if (!hasCancelListener) {
            cancel.setVisibility(View.GONE);
        } else {
            cancel.setVisibility(View.VISIBLE);
        }

        AlertDialog.Builder
                builder = new AlertDialog.Builder(context).setView(viewDialog)
                .setCancelable(true);
        final Dialog dialog = builder.show();

        if (okListener == null) {
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        } else {
            ok.setOnClickListener(okListener);
        }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static Dialog comfirmDialog4Input(Context context, int titleResId, String content, int okResId, int cancelResId, DialogInputListener mlistener, boolean hasCancelListener) {

        View viewDialog = LayoutInflater.from(context).inflate(R.layout.c_alert_input, null);

        Button ok = (Button) viewDialog.findViewById(R.id.c_alert_btn_ok);
        Button cancel = (Button) viewDialog.findViewById(R.id.c_alert_btn_cancel);
        TextView tvContent = (TextView) viewDialog.findViewById(R.id.c_alert_text);
        TextView tvTitle = (TextView) viewDialog.findViewById(R.id.c_alert_title);
        EditText inputNum = (EditText) viewDialog.findViewById(R.id.c_alert_et_input);
        String num = inputNum.getText().toString().trim();

        ok.setText(ResUtils.getString(okResId));
        tvTitle.setText(ResUtils.getString(titleResId));
        tvContent.setText(content);
        cancel.setText(ResUtils.getString(cancelResId));


        if (!hasCancelListener) {
            cancel.setVisibility(View.GONE);
        } else {
            cancel.setVisibility(View.VISIBLE);
        }


        AlertDialog.Builder
                builder = new AlertDialog.Builder(context).setView(viewDialog)
                .setCancelable(true);
        final Dialog dialog = builder.show();


        ok.setOnClickListener(new DialogInputClick(mlistener, num));


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }


    public interface DialogInputListener {
        void dialogInputClickListener(String number);
    }

    public static class DialogInputClick implements View.OnClickListener {
        private DialogInputListener mListener;
        private String number;

        public DialogInputClick(DialogInputListener listener, String number) {
            this.mListener = listener;
            this.number = number;
        }

        @Override
        public void onClick(View v) {
            mListener.dialogInputClickListener(number);
        }
    }


}
