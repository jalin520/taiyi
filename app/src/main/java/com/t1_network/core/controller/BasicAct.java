package com.t1_network.core.controller;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oneapm.agent.android.module.analysis.OneApmAnalysis;
import com.t1_network.core.utils.KeyBoardUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.SystemStatusManager;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.widget.TYProgressDialog;

import butterknife.ButterKnife;


/**
 * 功能：Activity的父类
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-10-15
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 * 1     2015-10-19         樊辉达         添加日期控制
 * 2    2015-11-26          樊辉达         封装了Toolbar
 */
public abstract class BasicAct extends AppCompatActivity {

    public Bundle savedInstanceState;

    public int contentResId;//内容的资源id
    public int titleResId;//标题的资源id

    public Toolbar toolbar;
    public TextView textTitle;
    public RelativeLayout imageBackBtn;

    public ImageLoader imageLoader;

    public TYProgressDialog dialog;

    public BasicAct(int contentResId, int titleResId) {
        this(contentResId, titleResId, true, TOOLBAR_TYPE_DEFAULT);
    }

    public BasicAct(int contentResId, int titleResId, boolean hasBackButton) {
        this(contentResId, titleResId, hasBackButton, TOOLBAR_TYPE_DEFAULT);
    }

    public BasicAct(int contentResId, int titleResId, boolean hasBackButton, int toolbarType) {
        this.contentResId = contentResId;
        this.titleResId = titleResId;
        this.toolbarType = toolbarType;
        this.hasBackButton = hasBackButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageLoader = ImageLoader.getInstance();
        dialog = TYProgressDialog.createDialog(this);

        initActionBar(); //设置Toolbar
//        setTranslucentStatus();

        this.savedInstanceState = savedInstanceState;//记录savedInstanceState

        initButterKnife();//声明注解式框架

        initUmeng(); //初始化友盟统计

        initView();//初始化子类的view


    }


    public final static int NO_CONTENT = 0;

    private void initContent() {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (NO_CONTENT != contentResId) {
            initWindow();
            setContentView(contentResId);
        }
    }

    /**
     * 如果需要进行Window操作,则重写此方法
     */
    public void initWindow() {

    }

    /**
     * ToolBar的初始化,适用于有ActionBar、无ActionBar、全屏等情况
     */
    public int toolbarType = TOOLBAR_TYPE_DEFAULT;

    public final static int TOOLBAR_TYPE_DEFAULT = 0;
    public final static int TOOLBAR_TYPE_NO_TOOLBAR = -1;
    public final static int TOOLBAR_TYPE_FULL_SCREEN = -2;

    public boolean hasBackButton = true;
    public final static int NO_TITLE = 0;

    private void initActionBar() {

        switch (toolbarType) {
            case TOOLBAR_TYPE_FULL_SCREEN://全屏
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            case TOOLBAR_TYPE_NO_TOOLBAR://无ActionBar
                initContent();//设置布局文件
                break;
            default://默认情况，保留Toolbar
                initContent();//设置布局文件
                //设置toolbar
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                //设置toolbar显示的title文字
                textTitle = (TextView) toolbar.findViewById(R.id.tool_bar_title);
                //设置Toolbar的返回图片
                imageBackBtn = (RelativeLayout) toolbar.findViewById(R.id.tool_bar_btn_back);
                //如果需要返回
                if (hasBackButton) {
                    //设置返回显示
                    imageBackBtn.setVisibility(View.VISIBLE);
                    imageBackBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });

                } else {//如果不需要返回

                    if (imageBackBtn != null) {
                        //设置返回隐藏
                        imageBackBtn.setVisibility(View.GONE);
                    }
                }

                //
                if (titleResId != NO_TITLE && textTitle != null) {

                    if (titleResId == NO_TITLE) {
                        //不显示文字
                        textTitle.setText(ResUtils.getString(R.string.default_toolbar_title));
                    } else {
                        //在actionbar中显示文字
                        textTitle.setText(ResUtils.getString(titleResId));
                    }
                }

//                setSupportActionBar(toolbar);

                break;
        }
    }

    /**
     * 设置状态栏背景状态
     */
    SystemStatusManager tintManager;

    private void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        tintManager = new SystemStatusManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(0);//状态栏无背景
        tintManager.setTintColor(ResUtils.getColor(R.color.colorPrimary));
//        tintManager.setStatusBarAlpha(0.5f);

    }

    public void setStatusBarColor(int colorResId) {
        tintManager.setStatusBarTintColor(ResUtils.getColor(colorResId));//状态栏无背景
        tintManager.setTintColor(ResUtils.getColor(colorResId));

    }

    private void initButterKnife() {
        ButterKnife.bind(this);
    }

    public void initUmeng() {
        //友盟统计
//        MobclickAgent.updateOnlineConfig(this);//发送策略
//        AnalyticsConfig.enableEncrypt(false);//加密模式.false不加密
//        AnalyticsConfig.setAppkey(this, "5600b05f67e58e8bcd00147b");//友盟后台申请的应用Appkey
//        AnalyticsConfig.setChannel("Wandoujia");//推广渠道名称
    }



    public abstract void initView();

    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart("SplashScreen"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写)
//        MobclickAgent.onResume(this);          //统计时长

        //OneApm
        OneApmAnalysis.onResume();

    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("SplashScreen"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息
//        MobclickAgent.onPause(this);
        //OneApm
        OneApmAnalysis.onPause();
    }

    public void showTip(String msg) {

        if (toolbar != null) {
            KeyBoardUtils.hideKeyboard(this, toolbar);
            TipUtils.snackBar(toolbar, msg);
        } else {
            TipUtils.toast(msg);
        }
    }

    public String getTag() {
        String className = this.getClass().getName();
        return className;
    }


    public void setTitle(String title) {
        if (textTitle != null) {
            textTitle.setText(title);
        }
    }

    public void clearAdapter(BasicListAdapter adapter) {
        if (adapter != null) {
            adapter.getData().clear();
        }
    }

}
