package com.example.new_demo;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.WindowManager;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class IntroActivity extends AppIntro2 {
    @Override
    protected void  onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

        SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle("天气简报");
        sliderPage1.setDescription("查看资讯"+"\r\n"+"从天气开始");
        sliderPage1.setImageDrawable(R.drawable.show_1);
        sliderPage1.setBgColor(Color.parseColor("#3498DB"));
        addSlide(AppIntroFragment.newInstance(sliderPage1));

        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle("新闻列表");
        sliderPage2.setDescription("发现最新资讯"+"\r\n"+"直击焦点");
        sliderPage2.setImageDrawable(R.drawable.show_2);
        sliderPage2.setBgColor(Color.parseColor("#28B463"));
        addSlide(AppIntroFragment.newInstance(sliderPage2));

        SliderPage sliderPage3 = new SliderPage();
        sliderPage3.setTitle("详细天气");
        sliderPage3.setDescription("关注天气"+"\r\n"+"安全出行");
        sliderPage3.setImageDrawable(R.drawable.show_3);
        sliderPage3.setBgColor(Color.parseColor("#884EA0"));
        addSlide(AppIntroFragment.newInstance(sliderPage3));

        showSkipButton(false);

//        setDoneText("完成");
    }

    @Override
    public void onDonePressed(Fragment currentFragment){
        super.onDonePressed(currentFragment);
          finish();
        //当用户点击完成按钮时执行某些操作。
    }

}
