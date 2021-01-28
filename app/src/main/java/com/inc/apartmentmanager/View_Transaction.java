package com.inc.apartmentmanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class View_Transaction extends AppCompatActivity {

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
        setContentView(R.layout.activity_view__transaction);


        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tabLayout =  findViewById(R.id.tabs_transaction);

        if(savedRadioIndex==0){
            tabLayout.setTabTextColors(Color.WHITE,getResources().getColor(R.color.colorPrimary));
        }

        viewPager =  findViewById(R.id.viewPager_transaction);
        viewPager.setAdapter(new MyAdapter_Transaction(getSupportFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
}
