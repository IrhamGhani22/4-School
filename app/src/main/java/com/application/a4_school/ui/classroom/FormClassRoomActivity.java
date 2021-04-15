package com.application.a4_school.ui.classroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.RestAPI.APIService;
import com.application.a4_school.adapter.SpinnerAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormClassRoomActivity extends AppCompatActivity {
    private Spinner spinType;
    private String type;
    private Button btnUpload;
    private String id_class;
    private TextInputEditText inputTitlte, inputDescription, inputDays, inputMonth, inputYear;
    private ImageButton btnCalendar;
    DatePickerDialog.OnDateSetListener setListener;
    private TextView deadlineTitle, uploadTitle;
    private LinearLayout containerDeadline;
    private CardView containerUpload;
    //data dummy dropdown, hapus aja nanti
    String[] selectOption = {"Task", "Theory"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_class_room);
        findView();
        id_class = getIntent().getStringExtra("EXTRA_ID_CLASS");
        datePicker(inputYear, inputMonth, inputDays);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_type_style, selectOption);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinType.setAdapter(spinnerAdapter);
        spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = spinType.getSelectedItem().toString();
                Log.d("spinValue", "value: "+type);
                if (type.equals("Theory")){
                    deadlineTitle.animate().alpha(0f).setDuration(250);
                    containerDeadline.animate().alpha(0f).setDuration(300);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            deadlineTitle.setVisibility(View.GONE);
                            containerDeadline.setVisibility(View.GONE);
                        }
                    }, 300);

                }else{
                    deadlineTitle.animate().alpha(1.0f).setDuration(250);
                    containerDeadline.animate().alpha(1.0f).setDuration(300);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            deadlineTitle.setVisibility(View.VISIBLE);
                            containerDeadline.setVisibility(View.VISIBLE);
                        }
                    }, 300);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_matpel = getSharedPreferences("userInfo", 0).getString("profession", "");
                String title = inputTitlte.getText().toString().trim();
                String desc = inputDescription.getText().toString().trim();
            }
        });
    }

    private void findView(){
        spinType = findViewById(R.id.list_task);
        btnUpload = findViewById(R.id.btn_upload_tasktheory);
        inputTitlte = findViewById(R.id.edt_title_formclass);
        inputDescription = findViewById(R.id.edt_descrip_formclass);
        inputDays = findViewById(R.id.edt_days_deadline);
        inputMonth = findViewById(R.id.edt_month_deadline);
        inputYear = findViewById(R.id.edt_year_deadline);
        btnCalendar = findViewById(R.id.btn_calendar_deadline);
        deadlineTitle = findViewById(R.id.deadlineText);
        containerDeadline = findViewById(R.id.container_deadline);
    }

    private void datePicker(final TextInputEditText dateYear, final TextInputEditText dateMonth, final TextInputEditText dateDay) {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        FormClassRoomActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month + 1;
                String tahun = "" + year;
                String bulan = "" + month;
                String hari = "" + dayOfMonth;
                dateYear.setText(tahun);
                dateMonth.setText(bulan);
                dateDay.setText(hari);
            }
        };

    }

    private void uploadTaskTheory(String id_class, String id_matpel, String title, String description, String type, String deadline){
        String token = getSharedPreferences("session", 0).getString("token", "");
        APIService api = APIClient.getClient().create(APIService.class);
        Call<ResponseBody> upload = api.uploadTaskTheory(id_class, id_matpel, title, description, type, deadline, "Bearer "+token);
        upload.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){

                }else{

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}