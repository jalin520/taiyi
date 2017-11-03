package com.t1_network.core.utils;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by David on 2015/11/2.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class CheckUtils {

    /**
     * 初始化 TextInputLayout 加校验 返回EditText的正确数据
     * @param inputlayout 传入TextInputLayout
     * @param length 判断的长度
     * @param errorMessage 错误显示的message
     * @return 返回一个输入字符串
     */
    public static String initInputLayout(final TextInputLayout inputlayout, final Integer length, final String errorMessage){
        EditText mobileEdit = inputlayout.getEditText();


        mobileEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > length) {
                    inputlayout.setError(errorMessage);
                    inputlayout.setErrorEnabled(true);
                } else {
                    inputlayout.setErrorEnabled(false);
                }
            }
        });
        return mobileEdit.getText().toString().trim();
    }

    /**
     * 初始化 TextInputLayout 加校验 返回EditText的正确数据

     * @return 返回一个输入字符串
     */
    public static String initInputLayoutByPwd(final TextInputLayout inputlayout){
        EditText mobileEdit = inputlayout.getEditText();
        mobileEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 6) {
                    inputlayout.setError("密码不能少于6位数");
                    inputlayout.setErrorEnabled(true);
                } else {
                    inputlayout.setErrorEnabled(false);
                }
            }
        });


        return mobileEdit.getText().toString().trim();
    }

    /**
     * 初始化 TextInputLayout 加校验 返回EditText的正确数据

     * @return 返回一个输入字符串
     */
    public static String initInputLayoutByPhone(final TextInputLayout inputlayout){
        EditText mobileEdit = inputlayout.getEditText();
        mobileEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 11) {
                    inputlayout.setError("请输入正确的手机号码");
                    inputlayout.setErrorEnabled(true);
                } else {
                    inputlayout.setErrorEnabled(false);
                }
            }
        });


        return mobileEdit.getText().toString().trim();
    }

    /**
     * 初始化 TextInputLayout 返回EditText => String 数据

     * @return 返回一个输入字符串
     */
    public static String inputLayoutString(final TextInputLayout inputlayout){
        EditText mobileEdit = inputlayout.getEditText();

        return mobileEdit.getText().toString().trim();
    }
}
