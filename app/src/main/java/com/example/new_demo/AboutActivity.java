package com.example.new_demo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.net.URL;

public class AboutActivity extends Activity {
    private String url = "https://github.com/ComeOnKissMe/New_demo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#24292e"));//改变通知栏颜色
        setContentView(R.layout.activity_meizi_detial);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.detial_swipe);//初始化下拉刷新控件
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#FF4081"),Color.parseColor("#0066FF"));//下拉刷新圈圈混合色
        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);//初始化悬浮按钮
        floatingActionButton.setVisibility(View.GONE);//GONE隐藏悬浮按钮，消除位置占用
        WebView webView = (WebView)findViewById(R.id.web_view);//web控件
        MeiziDetialActivity.setDefaultWebSettings(webView);//调用MeiziDetialActivity的setDefaultWebSettings方法
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}
