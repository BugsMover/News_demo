package com.example.new_demo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String url_news = "https://www.apiopen.top/journalismApi";
    String json;
    DrawerLayout drawerlayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    private SwipeRefreshLayout swipeRefreshLayout;
    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    initadapter();
                    break;
                case 2:
                    swipeRefreshLayout.setRefreshing(false);//取消刷新的圈
                    Toast.makeText(MainActivity.this, "刷新成功！", Toast.LENGTH_SHORT).show();
                    initadapter();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initIntro();//第一次启动引导页
        initView();
        new Https(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Json_news.json(Https.https(url_news));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    mHandler.sendEmptyMessage(1);
                }
            }
        }).start();


        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.open,R.string.close);
        drawerlayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerlayout.closeDrawer(GravityCompat.START);
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.drawer_weather_detailed:
                        Intent intent_weather_tetailed = new Intent(MainActivity.this,WeatherTetailedScrollingActivity.class);
                        startActivity(intent_weather_tetailed);
                        return true;
                    case R.id.drawer_settings:
                        Intent intent_setting = new Intent(MainActivity.this,SettingsActivity.class);
                        startActivity(intent_setting);
                        return true;
                    case R.id.drawer_finish:
                        System.exit(0);
                        return true;
                    case R.id.drawer_about:
//                        Toast.makeText(getBaseContext(),"关于",Toast.LENGTH_LONG).show();
                        Intent intent_about = new Intent(MainActivity.this,AboutActivity.class);
                        startActivity(intent_about);
                        return true;
                }
                return false;
            }
        });

    }

    private void initIntro() {
        SharedPreferences sp_first = getSharedPreferences("first", Context.MODE_PRIVATE);
        if (!sp_first.getBoolean("first", false)) {
            SharedPreferences.Editor editor = sp_first.edit();
            editor.putBoolean("first", true);
            editor.apply();
            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
        } else {
            SharedPreferences sp_switched = PreferenceManager.getDefaultSharedPreferences(this);
            boolean switched = sp_switched.getBoolean("weather_switch", true);
            if (switched) {
                Intent intent = new Intent(this, WeatherActivity.class);
                startActivity(intent);
            }
        }
    }

    private void initadapter() {
        int num = MeiziFactory.imageUrls.length;
        List<Meizi> meizis = MeiziFactory.createMeizis(num);
        MeiziAdapter adapter = new MeiziAdapter(this, meizis);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MeiziItemDecoration(2));
    }

    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#FF4081"), Color.parseColor("#0066FF"));

        toolbar = findViewById(R.id.toolbar);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MeiziFactory.imageUrls = new String[0];
                        MeiziFactory.links = new String[0];
                        MeiziFactory.comments = new String[0];
                        MeiziFactory.favorites = new String[0];
                        MeiziFactory.titles = new String[0];
                        MeiziFactory.names = new String[0];

                        try {
                            json = Https.https(url_news);
                            Json_news.json(json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }finally {
                            mHandler.sendEmptyMessage(2);
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toobar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_search) {
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_finish) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}