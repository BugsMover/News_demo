package com.example.new_demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherTetailedScrollingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView temp,pm25;
    private TextView date_1,date_2,date_3,textview_day;
    private ImageView dayPicture_1,dayPicture_2,dayPicture_3,dayPicture_4;
    private ImageView nightPicture_1,nightPicture_2,nightPicture_3,nightPicture_4;
    private TextView weather_1,weather_2,weather_3,weather_4;
    private TextView temperature_1,temperature_2,temperature_3,temperature_4;
    private TextView tipt_1,tipt_2,tipt_3,tipt_4,tipt_5;
    private TextView des_1,des_2,des_3,des_4,des_5;
    private FloatingActionButton floatingActionButton;
    private String url="https://api.isoyu.com/index.php/api/Weather/get_weather?city=";
    private String Json;
    private JSONObject results;
    private String city;
    private SharedPreferences sp;
    // 初始化handle，绑定在主线程中的队列消息中
    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            // 接收消息
            switch (msg.what){
               case 1:
                   setAll();
                break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_tetailed_scrolling);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getJsonData();
        initview();
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitle("天气");
        //刷新天气
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJsonData();
                Toast.makeText(WeatherTetailedScrollingActivity.this,
                        "刷新完成！",Toast.LENGTH_LONG).show();
            }
        });
    }

    //获取json信息，并解析部分
    private void getJsonData(){
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        city = sp.getString("weather_city","深圳");
        // 创建子线程，在子线程中处理耗时工作
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Json =  Https.https(url+city);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (Json !=null){
                    try {
                        JSONObject job= new JSONObject(Json);
                        JSONObject msg = job.getJSONObject("data");
                        JSONArray results_ary = msg.getJSONArray("results");
                        results  =results_ary.getJSONObject(0);
                        mHandler.sendEmptyMessage(1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                   }else {
                        Toast.makeText(WeatherTetailedScrollingActivity.this,"数据获取失败，请检查网络，或者稍后再试！",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }).start();
    }

    //解析json信息，并到控件上
    private void setAll(){
        try {
            String currentCity = results.getString("currentCity");
            int ipm25 =Integer.parseInt(results.getString("pm25"));
            JSONArray weather_data= results.getJSONArray("weather_data");
            JSONObject weather_data_job = weather_data.getJSONObject(0);
            String tempStr = weather_data_job.getString("date");
            temp.setText(tempStr.substring(tempStr.lastIndexOf("实时：")+3,tempStr.length()-1));
            if (0<=ipm25 && ipm25<=50){
                pm25.setText("空气质量:优");
            }else if (51<=ipm25 && ipm25<=100){
                pm25.setText("空气质量:良");
            }else if (101<=ipm25 && ipm25<=150){
                pm25.setText("空气质量:轻度污染");
            }else if (151<=ipm25 && ipm25<=200){
                pm25.setText("空气质量:中度污染");
            }else if (201<=ipm25 && ipm25<=300){
                pm25.setText("空气质量:重度污染");
            }else if (300<ipm25){
                pm25.setText("空气质量:严重污染");
            }
            collapsingToolbarLayout.setTitle(currentCity);
            textview_day.setText("今天");
            setDate(date_1,1);
            setDate(date_2,2);
            setDate(date_3,3);

            setWeather(weather_1,temperature_1,dayPicture_1,nightPicture_1,0);
            setWeather(weather_2,temperature_2,dayPicture_2,nightPicture_2,1);
            setWeather(weather_3,temperature_3,dayPicture_3,nightPicture_3,2);
            setWeather(weather_4,temperature_4,dayPicture_4,nightPicture_4,3);
            setLife(tipt_1,des_1,0);
            setLife(tipt_2,des_2,1);
            setLife(tipt_3,des_3,2);
            setLife(tipt_4,des_4,3);
            setLife(tipt_5,des_5,4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toobar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_finish){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initview() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        temp = (TextView) findViewById(R.id.temp);
        pm25 = (TextView) findViewById(R.id.pm25);
        date_1 = (TextView) findViewById(R.id.date_1);
        date_2 = (TextView) findViewById(R.id.date_2);
        date_3 = (TextView) findViewById(R.id.date_3);
        textview_day = (TextView)findViewById(R.id.textview_today);
        dayPicture_1 = (ImageView) findViewById(R.id.dayPicture_1);
        dayPicture_2 = (ImageView) findViewById(R.id.dayPicture_2);
        dayPicture_3 = (ImageView) findViewById(R.id.dayPicture_3);
        dayPicture_4 = (ImageView) findViewById(R.id.dayPicture_4);
        nightPicture_1 = (ImageView) findViewById(R.id.nightPicture_1);
        nightPicture_2 = (ImageView) findViewById(R.id.nightPicture_2);
        nightPicture_3 = (ImageView) findViewById(R.id.nightPicture_3);
        nightPicture_4 = (ImageView) findViewById(R.id.nightPicture_4);
        weather_1 = (TextView) findViewById(R.id.weather_1);
        weather_2 = (TextView) findViewById(R.id.weather_2);
        weather_3 = (TextView) findViewById(R.id.weather_3);
        weather_4 = (TextView) findViewById(R.id.weather_4);
        temperature_1 = (TextView) findViewById(R.id.temperature_1);
        temperature_2 = (TextView) findViewById(R.id.temperature_2);
        temperature_3 = (TextView) findViewById(R.id.temperature_3);
        temperature_4 = (TextView) findViewById(R.id.temperature_4);
        tipt_1 = (TextView) findViewById(R.id.tipt_1);
        tipt_2 = (TextView) findViewById(R.id.tipt_2);
        tipt_3 = (TextView) findViewById(R.id.tipt_3);
        tipt_4 = (TextView) findViewById(R.id.tipt_4);
        tipt_5 = (TextView) findViewById(R.id.tipt_5);
        des_1 = (TextView) findViewById(R.id.des_1);
        des_2 = (TextView) findViewById(R.id.des_2);
        des_3 = (TextView) findViewById(R.id.des_3);
        des_4 = (TextView) findViewById(R.id.des_4);
        des_5 = (TextView) findViewById(R.id.des_5);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_Refresh);
    }

    //减少代码写的方法
    private void setDate(TextView dateView,int i) throws JSONException {
        JSONArray weather_data= results.getJSONArray("weather_data");
        JSONObject weather_data_job = weather_data.getJSONObject(i);
        String date = weather_data_job.getString("date").substring(0,2);
        dateView.setText(date);
    }
    //减少代码写的方法
    private void setWeather(TextView weahter,TextView temperature,ImageView dayPicture,
                            ImageView nightPicture,int i) throws JSONException {
        JSONArray weather_data= results.getJSONArray("weather_data");
        JSONObject weather_data_job = weather_data.getJSONObject(i);
        String weatherStr = weather_data_job.getString("weather");
        String temperatureStr = weather_data_job.getString("temperature");
        String dayUrl = weather_data_job.getString("dayPictureUrl");
        String nightUrl = weather_data_job.getString("nightPictureUrl");

        weahter.setText(weatherStr);
        temperature.setText(temperatureStr);
        //加载网络图片
        Glide
                .with(this)
                .load(dayUrl)
                .into(dayPicture);
        Glide
                .with(this)
                .load(nightUrl)
                .into(nightPicture);
    }

    //减少代码写的方法
    private void setLife(TextView tipt,TextView des, int i) throws JSONException {
            JSONArray  index = results.getJSONArray("index");
            Log.d("index", String.valueOf(index));
            JSONObject indexjob = index.getJSONObject(i);
            String desStr = indexjob.getString("des");
            String tiptStr = indexjob.getString("tipt");
            String zsStr = indexjob.getString("zs");
             Log.d("index", desStr + tiptStr + zsStr );
            tipt.setText(tiptStr+"："+zsStr);
            des.setText(desStr);
    }
}
