package com.example.new_demo;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class MeiziDetialActivity extends AppCompatActivity {

    public static final String LINKS="links";
    public static final String Titles="title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏状态栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi_detial);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.detial_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#FF4081"),Color.parseColor("#0066FF"));

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);

        final WebView webView = (WebView) findViewById(R.id.web_view);
        setDefaultWebSettings(webView);
        webView.setWebViewClient(new WebViewClient());
        final String link = getIntent().getStringExtra(LINKS);
        final String title = getIntent().getStringExtra(Titles);
        webView.loadUrl(link);

        /**
         * mWebView.goBack();   后退
         * mWebView.goForward();前进
         * mWebView.reload();  刷新
         */
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction()==KeyEvent.ACTION_DOWN){
                    if (i == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
                        //表示按返回键时的操作
                        webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       swipeRefreshLayout.setRefreshing(false);//关闭刷新的圈
                        Toast.makeText(MeiziDetialActivity.this,"刷新成功！",Toast.LENGTH_SHORT).show();
                    }
                },2000);

            }
        });



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MeiziDetialActivity.this,"分享",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);//调用系统的分享功能
                intent.putExtra(Intent.EXTRA_TEXT,title+"\r\n"+link);
                intent.setType("text/plain");//文本类型
                startActivity(Intent.createChooser(intent,"分享到"));
            }
        });

    }

    public static void setDefaultWebSettings(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        //5.0以上开启混合模式加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        //允许js代码
        webSettings.setJavaScriptEnabled(true);
        //允许SessionStorage/LocalStorage存储
        webSettings.setDomStorageEnabled(true);
        //禁用放缩
        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(false);
        //禁用文字缩放
        webSettings.setTextZoom(100);
        //10M缓存，api 18后，系统自动管理。
        webSettings.setAppCacheMaxSize(10 * 1024 * 1024);
        //允许缓存，设置缓存位置
        webSettings.setAppCacheEnabled(true);
//        webSettings.setAppCachePath(context.getDir("appcache", 0).getPath());
        //允许WebView使用File协议
        webSettings.setAllowFileAccess(true);
        //不保存密码
        webSettings.setSavePassword(false);
        //设置UA
//        webSettings.setUserAgentString(webSettings.getUserAgentString() + " kaolaApp/" + AppUtils.getVersionName());
        //移除部分系统JavaScript接口
//        KaolaWebViewSecurity.removeJavascriptInterfaces(webView);
        //自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
    }

}