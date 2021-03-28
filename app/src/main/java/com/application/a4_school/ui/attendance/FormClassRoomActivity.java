package com.application.a4_school.ui.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import com.application.a4_school.R;
import com.application.a4_school.adapter.SpinnerAdapter;

public class FormClassRoomActivity extends AppCompatActivity {
    Spinner listtask;

    Button btnUpload, btnBack;

    //data dummy dropdown, hapus aja nanti
        String[] selectOption = {"Task", "Theory"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_class_room);
        findView();

        SpinnerAdapter a = new SpinnerAdapter(getApplicationContext(), selectOption);
        listtask.setAdapter(a);

    }

    private void findView(){
        listtask = findViewById(R.id.list_task);
        btnUpload = findViewById(R.id.btn_submit);
        btnBack = findViewById(R.id.btn_back);
    }
}