package com.application.a4_school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AttedanceActivity extends AppCompatActivity {
    TextView shId_class;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attedance);
        initialize();
        String id_class = getIntent().getStringExtra("EXTRA_CLASS");
        shId_class.setText(id_class);
    }

    public void initialize(){
        shId_class = findViewById(R.id.result);
    }
}