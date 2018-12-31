package com.example.new_demo;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SettingsActivity extends AppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartScreenCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null){
            // Create the fragment only when the activity is created for the first time.
            //仅在首次创建活动时创建片段。
            // ie. not after orientation changes
            //即。 方向改变后没有
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(SettingsPreferenceFragment.FRAGMENT_TAG);
            if (fragment == null){
                fragment = new SettingsPreferenceFragment();
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.framelayout_container,fragment,SettingsPreferenceFragment.FRAGMENT_TAG);
            ft.commit();
        }

        Toolbar toolbar = findViewById(R.id.weather_toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public boolean onPreferenceStartScreen(PreferenceFragmentCompat preferenceFragmentCompat,
                                           PreferenceScreen preferenceScreen) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SettingsPreferenceFragment fragment = new SettingsPreferenceFragment();
        Bundle args = new Bundle();
        args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT,preferenceScreen.getKey());
        fragment.setArguments(args);
        ft.add(R.id.framelayout_container,fragment,preferenceScreen.getKey());
        ft.addToBackStack(preferenceScreen.getKey());
        ft.commit();
        return true;
    }

}
