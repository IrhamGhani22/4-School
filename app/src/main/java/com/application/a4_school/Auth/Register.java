package com.application.a4_school.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.application.a4_school.Auth.sessionResp.UserInfo;
import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.RestAPI.APIService;
import com.application.a4_school.RestAPI.ResponseData;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    EditText edtName;
    EditText edtEmail;
    EditText edtPw;
    Button btnregister;
    TextView shClosedRegist;
    ConstraintLayout containerRegist;
    Spinner spinProf, spinClasslevel, spinMajor;
    String[] selectOptionClass = {"X", "XI", "XII", "XIII"};
    private List<UserInfo> listmajors = new ArrayList<>();
    LinearLayout containerNip, containerNis, containerProf, containerClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();

        ArrayAdapter<String> spinnerAdapterClass = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, selectOptionClass);
        spinnerAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinClasslevel.setAdapter(spinnerAdapterClass);
        spinClasslevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadMajors(spinClasslevel.getSelectedItem().toString());
                Log.d("Classleveldropdown", "value: " + spinClasslevel.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cekOpenRegist();
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nipornis;
                String name = edtName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String pw = edtPw.getText().toString().trim();
            }
        });
    }

    private void initialize(){
        edtName = findViewById(R.id.inputname);
        edtEmail = findViewById(R.id.inputemail);
        edtPw = findViewById(R.id.inputpasswordregis);
        btnregister = findViewById(R.id.btn_register);
        containerNip = findViewById(R.id.container_nip);
        containerNis = findViewById(R.id.container_nis);
        containerClass = findViewById(R.id.container_class);
        containerProf = findViewById(R.id.container_profession_regist);
        shClosedRegist = findViewById(R.id.shClosedRegist);
        containerRegist = findViewById(R.id.container_regist);
        spinClasslevel = findViewById(R.id.spinclasslevel);
        spinMajor = findViewById(R.id.spinmajors);
        spinProf = findViewById(R.id.spinProfesi);
    }

    private void cekOpenRegist(){
        APIService api = APIClient.getClient().create(APIService.class);
        Call<JsonObject> cekOpen = api.cekRegist();
        cekOpen.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    JsonObject object = response.body().getAsJsonObject();
                    Log.d("RegistCek", "value: "+object);
                    String status = object.get("status").toString().replaceAll("\"", "");
                    Log.d("RegistCek", "value: "+status);
                    String role = object.get("role").toString().replaceAll("\"", "");
                    Log.d("RegistCek", "value: "+role);
                    if (status.equals("open")){
                        switch (role){
                            case "siswa":
                                containerNip.setVisibility(View.GONE);
                                containerProf.setVisibility(View.GONE);
                                break;

                            case "guru":
                                containerNis.setVisibility(View.GONE);
                                containerClass.setVisibility(View.GONE);
                                break;
                        }
                    }else{
                        containerRegist.setVisibility(View.GONE);
                        shClosedRegist.setVisibility(View.VISIBLE);
                    }
                }else{

                    containerRegist.setVisibility(View.GONE);
                    shClosedRegist.setVisibility(View.VISIBLE);
                    shClosedRegist.setText("Server is on maintenance, please try again later");
                    Log.d("RegistCek", "not success "+response.body().getAsJsonObject());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("RegistCek", "failure "+t.getMessage());
                containerRegist.setVisibility(View.GONE);
                shClosedRegist.setVisibility(View.VISIBLE);
                shClosedRegist.setText("We can't connect to server, please check your internet connection");
            }
        });
    }

    private void register(String nipornis, String name, String email,  String proforclass, String password){
        APIService api = APIClient.getClient().create(APIService.class);
        Call<ResponseBody> register = api.register(email, password, name);
        Log.d("authemail","email : " + email);
        Log.d("authemail","name : " + name);
        Log.d("authemail","password : " + password);
        register.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String responseJSON = response.body().string();
                        Log.d("register", "response : " + responseJSON);
                        Toast.makeText(Register.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                        Intent toLogin = new Intent(Register.this, Login.class);
                        startActivity(toLogin);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    //system error
                    Toast.makeText(Register.this, "System Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //internet connection
                Toast.makeText(Register.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                Log.d("failure", "Message : "+t.getMessage());
            }
        });
    }

    private void loadMajors(String classlevel) {
        APIService api = APIClient.getClient().create(APIService.class);
        Call<ResponseData> loadListMajor = api.getMajors(classlevel);
        loadListMajor.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful()) {
                    listmajors.clear();
                    listmajors.addAll(response.body().getListMajors());
                    spinMajor.setEnabled(true);
                    List<String> list = new ArrayList<>();
                    list.clear();
                    for (int i = 0; i < response.body().getListMajors().size(); i++) {
                        list.add(response.body().getListMajors().get(i).getMajors());
                    }
                    ArrayAdapter<UserInfo> spinmajorAdapter = new ArrayAdapter<UserInfo>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listmajors);
                    spinmajorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinMajor.setAdapter(spinmajorAdapter);
                    spinMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Log.d("majorvalue", "" + listmajors.get(i).getId_class());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } else {
                    Log.d("loadmajors", "not success");
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d("loadmajors", "failure " + t.getMessage());
            }
        });
    }
}