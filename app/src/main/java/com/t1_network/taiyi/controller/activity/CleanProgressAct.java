package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.CacheUtils;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.core.utils.UIUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.widget.progress.WaterWaveProgress;

public class CleanProgressAct extends BasicAct {

    public CleanProgressAct() {
        super(R.layout.act_clean_progress, R.string.title_activity_clean_progress);
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        String extra = intent.getStringExtra(CACHE_SIZE);

        waveProgress = (WaterWaveProgress) findViewById(R.id.waterWaveProgress1);
        waveProgress.setShowProgress(true);
        waveProgress.animateWave();

        waveProgress.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mFolderSize = CacheUtils.getFolderSize(CleanProgressAct.this.getCacheDir());
                            if (mFolderSize < 24000) {
                                TipUtils.toast("已经清理干净");
                                return;
                            }
                        } catch (Exception e) {
                        }

                        if (waveProgress.isClickable()) {
                            waveProgress.setClickable(false);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    for (int i = 0; i <= 100; i++) {
                                        try {

                                            Message message = new Message();
                                            message.what = 1;
                                            message.obj = i;
                                            mHandler.sendMessage(message);
                                            Thread.sleep(20);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        if (i == 100) {
                                            try {
                                                Message message = new Message();
                                                message.what = 1;
                                                message.obj = i;
                                                mHandler.sendMessage(message);
                                                Thread.sleep(2000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            for (int j = i; j >= 1; j--) {
                                                try {
                                                    Message message = new Message();
                                                    message.what = 1;
                                                    message.obj = j;
                                                    mHandler.sendMessage(message);
                                                    Thread.sleep(20);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        }


                                    }


                                    if (!flag)
                                        startTime = System.currentTimeMillis();
                                    UIUtils.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Message message = new Message();
                                            message.what = 2;
                                            message.obj = true;
                                            mHandler.sendMessage(message);
                                        }
                                    }, 2000);

                                }

                            }).start();


                        } else {
                            TipUtils.toast("缓存已经很干净了");
                        }


                    }
                }


        );


        ((CheckBox) findViewById(R.id.checkBox1)).
                setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                               @Override
                                               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                   waveProgress.setShowProgress(isChecked);
                                               }
                                           }

                );


        ((CheckBox) findViewById(R.id.checkBox2)).
                setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                               @Override
                                               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                   waveProgress.setShowNumerical(isChecked);
                                               }
                                           }

                );
    }

    WaterWaveProgress waveProgress;

    private boolean flag = false;

    long startTime;
    long endTime;

    private long mCacheSize;

    public static final String CACHE_SIZE = "CACHESIZE";

    private long mFolderSize;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;

            switch (what) {
                case 1:

                    try {
                        Integer i = (Integer) msg.obj;

                        waveProgress.setProgress(i);


                        CacheUtils.cleanAllCache(CleanProgressAct.this);
                        String allCacheSize = CacheUtils.getAllCacheSize(CleanProgressAct.this);


                        // intent很重要，是两个Activity之间的纽带
                        Intent in = new Intent();

                        Bundle bundle = new Bundle();

                        bundle.putString("result", allCacheSize);
                        in.putExtras(bundle);

                        //上面代码和从a到b没有多大变化，关键不要再去startActivity了

                        CleanProgressAct.this.setResult(RESULT_OK, in);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    break;
                case 2:
                    waveProgress.setClickable((Boolean) msg.obj);
                    if (endTime <= 0) {
                        TipUtils.toast("清理完毕");
                        endTime = System.currentTimeMillis();


                        break;
                    }

                    endTime = System.currentTimeMillis();
                    if (endTime - startTime > 1 && endTime - startTime < 10000) {
                        TipUtils.toast("已经清理干净了,没什么要清理了.");
                        flag = true;
                    } else {
                        TipUtils.toast("清理完毕");
                        flag = false;


                    }

                    break;
            }
        }
    };

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CleanProgressAct.class);
        context.startActivity(intent);
    }

}
