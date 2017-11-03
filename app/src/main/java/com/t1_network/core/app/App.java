package com.t1_network.core.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.meiqia.core.MQManager;
import com.meiqia.core.callback.OnInitCallBackOn;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.oneapm.agent.android.OneApmAgent;
import com.t1_network.taiyi.model.bean.User;
import com.umeng.update.UpdateConfig;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;


/**
 * 作用：作为App的Application，并承当全局存储的作用，存放RequestQueue、ImageLoader等线程安全的单例对象
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-10-15
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 * 1     2015-10-22         樊辉达         整合代码，对RequestQueue进行线程安全的单例控制
 * 2    2015-10-23          樊辉达         在onCreate中添加Universal-Image-Loader的初始化
 */

public class App extends Application {
    public User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLogin() {
        return user != null;
    }

    public static App app;

    public static App getApp() {
        if (app == null) {
            synchronized (App.class) {
                if (app == null) {
                    app = new App();
                }
            }
        }
        return app;
    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    /**
     * 处理网络请求的消息队列
     */
    public static RequestQueue queue;

    public static RequestQueue getQueue() {
        if (queue == null) {
            synchronized (RequestQueue.class) {
                if (queue == null) {
                    //初始化Volley请求队列
                    queue = Volley.newRequestQueue(context);
                }
            }
        }
        return queue;
    }

    //上下文
    public static Context context;

    public static Context getContext() {
        return context;
    }

    // 主线程
    private static Thread mainThread;

    public static Thread getMainThread() {
        return mainThread;
    }

    private static long mainThreadId;

    public static long getMainThreadId() {
        return mainThreadId;
    }

    private static Looper mainThreadLooper;

    public static Looper getMainThreadLooper() {
        return mainThreadLooper;
    }

    // 创建主线程的handler
    private static Handler mainHandler;

    public static Handler getMainHandler() {
        return mainHandler;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //上下文
        context = getApplicationContext();
        // 主线程
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();
        mainThreadLooper = getMainLooper();
        // 创建主线程的handler
        mainHandler = new Handler();


        //Imagg-Loader初始化
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .memoryCacheExtraOptions(480, 800)
                        // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(
                        new BaseImageDownloader(getApplicationContext(),
                                5 * 1000, 30 * 1000)) // connectTimeout
                .writeDebugLogs() // Remove for release app
                .build();// 开始构建
        ImageLoader.getInstance().init(config);

        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);


        //美洽客服

        MQManager.init(context, "b823c19523c28ae6e2be44858431746b", new OnInitCallBackOn() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int code, String message) {

            }
        });

        //SharedSDK
        ShareSDK.initSDK(this);
        UpdateConfig.setDebug(true);

        //OneApm
        OneApmAgent.init(this).setToken("C1EE0839075016DAD0996786B003C00606").start();


    }


}
