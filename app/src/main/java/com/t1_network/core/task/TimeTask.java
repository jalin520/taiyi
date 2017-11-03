package com.t1_network.core.task;

import android.os.AsyncTask;
import android.widget.TextView;

import com.t1_network.core.utils.ResUtils;

/**
 * 作用：用于获取验证码时，提示时间的Task，其中提示时间的组件及提示模板都进行了定义。
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-06-13
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 * 1        2015-10-20          樊辉达         添加提示模板
 * 2        2015-10-30          樊辉达          将T extends TextView 扩充为View
 */
public class TimeTask<T extends TextView> extends AsyncTask<Integer, Integer, Integer> {
    /**
     * 每次睡眠时间，默认为1s
     */
    private final long SLEEP_TIME = 1000;

    /**
     * 提示模板
     */
    private String tipTemple = "REPLACE秒";

    /**
     * 是否处于运行状态
     */
    private boolean isRuning;

    /**
     * 描述
     */
    private String desc;

    /**
     * 显示计时的组件
     */
    private T textTime;

    /**
     * 字体颜色值
     */
    private int color;


    public TimeTask(T textTime, int color) {
        this.isRuning = true;
        desc = "";
        this.textTime = textTime;
        this.color = color;
    }

    public TimeTask(T textTime, String tipTemple, int color) {
        this(textTime, color);
        tipTemple = tipTemple;
    }


    @Override
    protected Integer doInBackground(Integer... params) {
        int time = params[0].intValue();

        isRuning = true;
        try {
            //循环计时
            while (time > 0 && isRuning) {
                time--;
                publishProgress(time);
                Thread.sleep(SLEEP_TIME);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPreExecute() {
        desc = textTime.getText().toString();
        textTime.setEnabled(false);
        textTime.setClickable(false);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        textTime.setEnabled(true);
        textTime.setClickable(true);
        textTime.setText(desc);
        textTime.setTextColor(ResUtils.getColor(color));
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (textTime != null) {
            textTime.setText(tipTemple.replace("REPLACE", values[0].intValue() + ""));
            textTime.setTextColor(ResUtils.getColor(color));
        }
    }

    public void stop() {
        this.isRuning = false;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
