package com.t1_network.taiyi.controller.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.taiyi.R;

import butterknife.Bind;

public class WebAct extends BasicAct {


    @Bind(R.id.act_web_view_web)
    WebView webView;

    public WebAct() {
        super(R.layout.act_web, R.string.title_activity_web);
    }

    public static final String P_URL = "P_URL";
    public static final String P_TITLE = "P_TITLE";

    public static void startActivity(Context context, String url, String title) {

        Intent intent = new Intent(context, WebAct.class);
        intent.putExtra(P_URL, url);
        intent.putExtra(P_TITLE, title);
        context.startActivity(intent);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(P_URL);
        String title = intent.getStringExtra(P_TITLE);
        setTitle(title);

        WebSettings webSettings = webView.getSettings();
        // 设置可以使用localStorage
        webSettings.setDomStorageEnabled(true);

        //可以进行js回调
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "good");


        // 应用可以有数据库
        webSettings.setDatabaseEnabled(true);
        String databasePath = this.getApplicationContext()
                .getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(databasePath);
        // 应用可以有缓存
        webSettings.setAppCacheEnabled(true);
        String appCaceDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCaceDir);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onExceededDatabaseQuota(String url,
                                                String databaseIdentifier, long quota,
                                                long estimatedDatabaseSize, long totalQuota,
                                                WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(5 * 1024 * 1024);
            }
        });

        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }


    /**
     * js 回调接口
     *
     * @param str
     */
    @JavascriptInterface
    public void startFunction(String str) {
        Toast.makeText(this, "js调用了java函数", Toast.LENGTH_SHORT).show();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {


            }
        });
    }


}
