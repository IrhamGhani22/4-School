package com.application.a4_school;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.application.a4_school.Models.Attendance;
import com.application.a4_school.Models.AttendanceData;
import com.application.a4_school.Models.HomeData;
import com.application.a4_school.adapter.AttendanceListAdapter;

import java.util.ArrayList;

public class AttedanceActivity extends AppCompatActivity {
    private RecyclerView rvAttendance;
    private AttendanceListAdapter adapter;
    private ArrayList<Attendance> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attedance);
        initialize();
        list.addAll(AttendanceData.getlistAttendance());
        rvAttendance.setLayoutManager(new LinearLayoutManager(this));
        rvAttendance.setAdapter(adapter);
        String id_class = getIntent().getStringExtra("EXTRA_CLASS");
    }

    public void initialize(){
        rvAttendance = findViewById(R.id.rv_attendance);
        adapter = new AttendanceListAdapter(list, this);
    }
}