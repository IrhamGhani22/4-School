package com.application.a4_school;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {
        EditText userid, pw;
        Button btnlogin;
        TextView resetPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userid = findViewById(R.id.inputlogin);
        pw = findViewById(R.id.inputpassword);
        btnlogin = findViewById(R.id.btn_login);
        resetPw = findViewById(R.id.reset_pw);

        resetPw.setOnClickListener(this);

        btnlogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:

                break;

            case R.id.reset_pw:
                Toast.makeText(this, "Ini Reset Password", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}