package com.application.a4_school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.application.a4_school.Auth.Login;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.RestAPI.APIService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    EditText edtName;
    EditText edtEmail;
    EditText edtPw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
        String email = edtEmail.getText().toString();
        String name = edtName.getText().toString();
        String password = edtPw.getText().toString();
        register(email,name,password);
    }

    private void initialize(){
        edtName = findViewById(R.id.inputemail);
        edtEmail = findViewById(R.id.inputname);
        edtPw = findViewById(R.id.inputpassword);

    }

    private void register(String email, String name, String password){
        APIService api = APIClient.getClient().create(APIService.class);
        Call<ResponseBody> register = api.register(email, password, name);
        register.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Intent toLogin = new Intent(Register.this, Login.class);
                    startActivity(toLogin);
                }else{
                    //system error
                    Toast.makeText(Register.this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
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