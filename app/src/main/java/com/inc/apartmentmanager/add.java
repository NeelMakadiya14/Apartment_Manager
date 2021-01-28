package com.inc.apartmentmanager;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


public class add extends AppCompatActivity{

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        int savedRadioIndex = sharedPreferences.getInt("SAVED_RADIO_BUTTON_INDEX",0);
        if(savedRadioIndex==0){
          setTheme(R.style.MyTheme_Dark);
        }
        else {
            setTheme(R.style.MyTheme_Light);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);



        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tabLayout =  findViewById(R.id.tabs);

        if(savedRadioIndex==0){
            tabLayout.setTabTextColors(Color.WHITE,getResources().getColor(R.color.colorPrimary));
        }

        viewPager =  findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
}