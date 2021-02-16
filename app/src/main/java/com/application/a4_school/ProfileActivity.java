package com.application.a4_school;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class ProfileActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] TAB_ICONS = {
            R.drawable.icon_person,
            R.drawable.ic_baseline_settings_24
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfilePagerAdapter profilePagerAdapter = new ProfilePagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(profilePagerAdapter);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);

    }

    private void setupTabIcons(){
        tabLayout.getTabAt(0).setIcon(TAB_ICONS[0]);
        tabLayout.getTabAt(1).setIcon(TAB_ICONS[1]);
    }
}