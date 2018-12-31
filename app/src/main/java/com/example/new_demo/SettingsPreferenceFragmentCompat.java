package com.example.new_demo;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

/**
 * Default parent class that fixes the color of the background of the fragment.
 * 默认父类，用于修复片段背景的颜色。
 */
public abstract class SettingsPreferenceFragmentCompat extends PreferenceFragmentCompat {
   @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
       super.onViewCreated(view,savedInstanceState);

       //Set the default white background in the view so as to avoid transparency
       //在视图中设置默认的白色背景以避免透明度
       view.setBackgroundColor(
               ContextCompat.getColor(getContext(),R.color.background_material_light)
       );
   }
}
