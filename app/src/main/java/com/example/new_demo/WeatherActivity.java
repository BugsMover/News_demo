package com.example.new_demo;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherActivity extends AppCompatActivity {
    TextView weather;
    TextView city;
    TextView temp;
    Button button;
    String  json;
    String Weather;
    String Temp;
    String CurrentCity;
    private MyCountDownTimer myCountDownTimer;
    String url = "https://api.isoyu.com/index.php/api/Weather/get_weather?city=";

    // 初始化handle，绑定在主线程中的队列消息中
    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 接收消息
            switch (msg.what){
                case 1:
                    initview();
                    myCountDownTimer= new MyCountDownTimer(3500,1000);
                    myCountDownTimer.start();
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) { myCountDownTimer.cancel();
                    WeatherActivity.this.finish();
                        }
                    });
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        json_weather();
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_weather);

    }

    private void json_weather() {

        // 创建子线程，在子线程中处理耗时工作
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String city = sp.getString("weather_city","深圳");
                try {
              json = Https.https(url+city);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        JSONObject job= new JSONObject(json);
                        JSONObject msg = job.getJSONObject("data");
                        JSONArray results_ary = msg.getJSONArray("results");
                        JSONObject results  =results_ary.getJSONObject(0);
                        CurrentCity = results.getString("currentCity");
                        JSONArray weather_data= results.getJSONArray("weather_data");
                        JSONObject weather_data_job = weather_data.getJSONObject(0);
                     String  temp = weather_data_job.getString("date");
                       Temp = temp.substring(temp.lastIndexOf("实时：")+3,temp.length()-1);
                         Weather = weather_data_job.getString("weather");
                    }catch (JSONException e){
                    }finally {
                        mHandler.sendEmptyMessage(1);
                    }
                }
            }
        }).start();
    }

    private void initview() {
        weather = (TextView) findViewById(R.id.weather);
       city = (TextView) findViewById(R.id.city);
       temp = (TextView) findViewById(R.id.temp);
       button = (Button) findViewById(R.id.button);

        weather.setText(Weather);
        city.setText(CurrentCity);
        temp.setText(Temp);
    }



    private class MyCountDownTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         *                          表示以毫秒为单位倒计时的总数
         *                          例如 millisInFuture = 1000 表示1秒
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         *                          表示 间隔 多少微秒 调用一次 onTick()
         *      例如: countDownInterval = 1000 ; 表示每 1000 毫秒调用一次 onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
             button.setText(l/1000+"");
        }

        @Override
        public void onFinish() {
            WeatherActivity.this.finish();
        }
    }
    @Override
    protected void onDestroy(){
        if (myCountDownTimer !=null){
            myCountDownTimer.cancel();
        }
        super.onDestroy();
    }
}
