package com.t1_network.core.widget;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/27 14:19
 * 修改记录:
 */
public class BannerTask extends AsyncTask<Void, Void, Void> {

    private ViewPager viewPager;

    private final static int SHOW_TIME = 2000;

    private boolean flag = true;

    public BannerTask(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    protected Void doInBackground(Void... params) {
        while (flag) {

            try {

                Thread.sleep(SHOW_TIME);
                publishProgress();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        int current = viewPager.getCurrentItem() + 1;
        viewPager.setCurrentItem(current);
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    public void stop() {
        flag = false;
    }

}
