package com.application.a4_school;

import android.os.Bundle;
import android.view.MenuItem;

import com.application.a4_school.ui.help.HelpFragment;
import com.application.a4_school.ui.home.HomeFragment;
import com.application.a4_school.ui.job.JobFragment;
import com.application.a4_school.ui.maps.MapsFragment;
import com.application.a4_school.ui.profile.ProfileFragment;
import com.application.a4_school.ui.schedule.ScheduleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //nampilin awal fragment pertama kali
        getFragmentPage(new HomeFragment());

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setSelectedItemId(R.id.nav_home);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_maps:
                        fragment = new MapsFragment();
                        break;
                    case R.id.nav_jobs:
                        fragment = new ScheduleFragment();
                        break;
                    case R.id.nav_help:
                        fragment = new HelpFragment();
                        break;
                    case R.id.nav_profile:
                        fragment = new ProfileFragment();
                        break;
                }

                return getFragmentPage(fragment);
            }
        });
    }

    private boolean getFragmentPage(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
        return  true;
        }
        return false;
    }
}