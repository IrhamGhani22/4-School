package com.application.a4_school.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.RestAPI.APIService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    EditText edtName;
    EditText edtEmail;
    EditText edtPw;
    Button btnregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(edtEmail.getText().toString(),edtName.getText().toString(), edtPw.getText().toString());
            }
        });
    }

    private void initialize(){
        edtName = findViewById(R.id.inputname);
        edtEmail = findViewById(R.id.inputemail);
        edtPw = findViewById(R.id.inputpasswordregis);
        btnregister = findViewById(R.id.btn_register);

    }

    private void register(String email, String name, String password){
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
                    try {
                        String responseJSON = response.body().string();
                        Log.d("register", "response : " + responseJSON);
                        Toast.makeText(Register.this, "System Error", Toast.LENGTH_SHORT).show();
                        Intent toLogin = new Intent(Register.this, Login.class);
                        startActivity(toLogin);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
}