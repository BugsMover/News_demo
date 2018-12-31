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
        window.setStatusBarColor(Color.parseColor("#24292e"));
        setContentView(R.layout.activity_meizi_detial);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.detial_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#FF4081"),Color.parseColor("#0066FF"));
        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setVisibility(View.GONE);
        WebView webView = (WebView)findViewById(R.id.web_view);
        MeiziDetialActivity.setDefaultWebSettings(webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}
