package com.application.a4_school.ui.classroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.RestAPI.APIService;
import com.application.a4_school.adapter.SpinnerAdapter;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private void uploadTaskTheory(String id_class, String id_matpel, String title, String description, String type, String deadline, int nilai){
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