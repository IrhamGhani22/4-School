package com.application.a4_school.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.application.a4_school.Auth.sessionResp.UserInfo;
import com.application.a4_school.MainActivity;
import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.RestAPI.APIService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends Activity implements View.OnClickListener {
    EditText etUsername, etPw;
    Button btnlogin;
    TextView resetPw;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());
        etUsername = findViewById(R.id.inputlogin);
        etPw = findViewById(R.id.inputpassword);
        btnlogin = findViewById(R.id.btn_login);
        resetPw = findViewById(R.id.reset_pw);

        resetPw.setOnClickListener(this);

        btnlogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                String username = etUsername.getText().toString();
                String pw = etPw.getText().toString();
                APIService api = APIClient.getClient().create(APIService.class);
                Call<ResponseBody> login = api.login(username, pw);
                login.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                String responseJSON = response.body().string();
                                Gson objGson = new Gson();
                                SessionResponse objResp = objGson.fromJson(responseJSON, SessionResponse.class);
                                if (objResp.getToken() != null){
                                    String role = objResp.getUserInfo().getRole();
                                    sessionManager.createSession(objResp.getToken(), role);
                                    if (role.equals("guru")){
                                        Intent toDasboard = new Intent(Login.this, MainActivity.class);
                                        startActivity(toDasboard);
                                    }else{
                                        Toast.makeText(Login.this, "Halaman siswa belum dibuat", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(Login.this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("Login", "" + e.getMessage());
                            }
                        }else{
                            Toast.makeText(Login.this, "System error, please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Login.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        Log.d("failure", "Message : "+t.getMessage());
                    }
                });
                break;

            case R.id.reset_pw:
                Toast.makeText(this, "Ini Reset Password", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}