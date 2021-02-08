package com.application.a4_school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.application.a4_school.Auth.Login;

public class SplashScreen extends AppCompatActivity {
    private int delayscreen = 1500;
    private TextView txt_splash;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_splash_screen);

        txt_splash = findViewById(R.id.splashschool);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt_splash.setVisibility(View.VISIBLE);
                txt_splash.animate().alpha(1.0f).setDuration(200);
            }
        },500);

        new Handler().postDelayed(new  Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();
            }
        },delayscreen);
    }
}