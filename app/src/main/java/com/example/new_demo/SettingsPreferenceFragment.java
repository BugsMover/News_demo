package com.example.new_demo;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.widget.Toast;

/**
 * A simple Preference Fragment that shows the xml on Demand.
 * 一个简单的偏好片段显示了需求的XML。
 */
public class SettingsPreferenceFragment extends SettingsPreferenceFragmentCompat {

    public static final String FRAGMENT_TAG = "my_preference_fragment";

    public SettingsPreferenceFragment(){

    }
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.pref_settings,s);

        final Preference weather_switch = (Preference) findPreference("weather_switch");

            weather_switch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                boolean switched = (boolean) o;
                         if (switched){
                             Toast.makeText(getContext(),"天气简报，开启！",Toast.LENGTH_LONG).show();
                         }else {
                             Toast.makeText(getContext(),"天气简报，关闭！",Toast.LENGTH_LONG).show();
                         }
                    return true;
                }
            });


    }
}
