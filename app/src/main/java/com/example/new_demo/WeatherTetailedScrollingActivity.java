package com.example.new_demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherTetailedScrollingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView temp,pm25;
    private TextView date_1,date_2,date_3;
    private ImageView dayPicture_1,dayPicture_2,dayPicture_3,dayPicture_4;
    private ImageView nightPicture_1,nightPicture_2,nightPicture_3,nightPicture_4;
    private TextView weather_1,weather_2,weather_3,weather_4;
    private TextView temperature_1,temperature_2,temperature_3,temperature_4;
    private TextView tipt_1,tipt_2,tipt_3,tipt_4,tipt_5;
    private TextView des_1,des_2,des_3,des_4,des_5;
    private String url="https://api.isoyu.com/index.php/api/Weather/get_weather?city=";
    private String Json;
    private JSONObject results;
    private String city;
    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
               case 1:
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
                    setDate(date_1,1);
                    setDate(date_2,2);
                    setDate(date_3,3);
                    setWeather(weather_1,temperature_1,dayPicture_1,nightPicture_1,1);
                    setWeather(weather_2,temperature_2,dayPicture_2,nightPicture_2,2);
                    setWeather(weather_3,temperature_3,dayPicture_3,nightPicture_3,3);
                    setWeather(weather_4,temperature_4,dayPicture_4,nightPicture_4,4);
                    setLife(tipt_1,des_1,1);
                    setLife(tipt_2,des_2,2);
                    setLife(tipt_3,des_3,3);
                    setLife(tipt_4,des_4,4);
                    setLife(tipt_5,des_5,5);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_tetailed_scrolling);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        city = sp.getString("weather_city",null);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Json =  Https.https(url+city);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            JSONObject job= new JSONObject(Json);
                            JSONObject msg = job.getJSONObject("data");
                            JSONArray results_ary = msg.getJSONArray("results");
                            results  =results_ary.getJSONObject(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }finally {
                         mHandler.sendEmptyMessage(1);
                        }
                    }
                }
            }).start();

        initview();
        setSupportActionBar(toolbar);

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
            System.exit(0);
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
    }

    private void setDate(TextView dateView,int i) throws JSONException {
        JSONArray weather_data= results.getJSONArray("weather_data");
        JSONObject weather_data_job = weather_data.getJSONObject(i);
        String date = weather_data_job.getString("date").substring(0,2);
        dateView.setText(date);
    }
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
        Glide
                .with(this)
                .load(dayUrl)
                .into(dayPicture);
        Glide
                .with(this)
                .load(nightUrl)
                .into(nightPicture);
    }

    private void setLife(TextView tipt,TextView des, int i) throws JSONException {
            JSONArray  index = results.getJSONArray("index");
            JSONObject indexjob = index.getJSONObject(i);
            String desStr = indexjob.getString("des");
            String tiptStr = indexjob.getString("tipt");
            String zsStr = indexjob.getString("zs");
            tipt.setText(tiptStr+"："+zsStr);
            des.setText(desStr);
    }
}
