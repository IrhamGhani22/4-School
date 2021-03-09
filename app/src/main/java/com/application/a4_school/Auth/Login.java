package com.application.a4_school.Auth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.application.a4_school.ForgotPassword;
import com.application.a4_school.MainActivity;
import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.RestAPI.APIService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends Activity implements View.OnClickListener {
    EditText etUsername, etPw;
    Button btnlogin;
    TextView resetPw;
    TextView txtRegister;
    SessionManager sessionManager;
    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());
        etUsername = findViewById(R.id.inputlogin);
        etPw = findViewById(R.id.inputpassword);
        btnlogin = findViewById(R.id.btn_login);
        resetPw = findViewById(R.id.reset_pw);
        txtRegister = findViewById(R.id.register);

        resetPw.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        btnlogin.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            this.finish();
            super.onBackPressed();
            return;
        }
        exit = true;
        Toast.makeText(this, "Tap back again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                exit = false;
            }
        }, 2000);
    }

    @Override
    public void onClick(View view) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Wait a second...");
        pd.show();
        switch (view.getId()){
            case R.id.btn_login:
                final String username = etUsername.getText().toString();
                final String pw = etPw.getText().toString();
                APIService api = APIClient.getClient().create(APIService.class);
                Call<ResponseBody> login = api.login(username, pw);
                login.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        pd.dismiss();
                        if (response.isSuccessful()){
                            try {
                                String responseJSON = response.body().string();
                                Log.d("auth", "response : "+responseJSON);
                                Gson objGson = new Gson();
                                SessionResponse objResp = objGson.fromJson(responseJSON, SessionResponse.class);
                                if (objResp.getToken() != null){
                                    String role = objResp.getUserInfo().getRole();
                                    Log.d("role", "role : " + role);
                                    sessionManager.createSession(objResp.getToken(), role);
                                    if (role.equals("guru")){
                                        Intent toDasboard = new Intent(Login.this, MainActivity.class);
                                        startActivity(toDasboard);
                                        finish();
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
                        }
                        else if (username.isEmpty()){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);
                            alertDialogBuilder.setTitle("Field the Blank Form Input");
                            alertDialogBuilder
                                    .setMessage("Please Enter Username or Password")
                                    .setCancelable(false)
                                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);
                            alertDialogBuilder.setTitle("Incorrect Form Input");
                            alertDialogBuilder
                                    .setMessage("Please enter your Email or password correctly")
                                    .setCancelable(false)
                                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }else if (pw.isEmpty()){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);
                            alertDialogBuilder.setTitle("Field the Blank Form Input");
                            alertDialogBuilder
                                    .setMessage("Please Enter Password")
                                    .setCancelable(false)
                                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }else{
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);
                            alertDialogBuilder.setTitle("Incorrect");
                            alertDialogBuilder
                                    .setMessage("Email or password not recornized")
                                    .setCancelable(false)
                                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        pd.dismiss();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);
                        alertDialogBuilder.setTitle("Internet Connection Error");
                        alertDialogBuilder
                                .setMessage("Please check your internet connection")
                                .setCancelable(false)
                                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        Toast.makeText(Login.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        Log.d("failure", "Message : "+t.getMessage());
                    }
                });
                break;

            case R.id.reset_pw:
                Intent toForgotPassword = new Intent(Login.this, ForgotPassword.class);
                startActivity(toForgotPassword);
                break;

            case R.id.register:
                Intent toRegister = new Intent(Login.this, Register.class);
                startActivity(toRegister);
                break;
        }

    }
}