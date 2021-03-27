package com.application.a4_school.ui.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import com.application.a4_school.R;
import com.application.a4_school.adapter.SpinnerAdapter;

public class AddAttendanceActivity extends AppCompatActivity {
    Spinner listtask;

    Button btnUpload, btnBack;

    //data dummy dropdown, hapus aja nanti
    String[] task = {"AAA", "AA", "A"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendance);
        findView();

        SpinnerAdapter a = new SpinnerAdapter(getApplicationContext(), task);
        listtask.setAdapter(a);

    }

    private void findView(){
        listtask = findViewById(R.id.list_task);
        btnUpload = findViewById(R.id.btn_submit);
        btnBack = findViewById(R.id.btn_back);
    }
}