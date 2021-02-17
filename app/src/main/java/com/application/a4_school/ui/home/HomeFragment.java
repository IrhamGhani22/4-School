package com.application.a4_school.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.application.a4_school.R;

import static com.application.a4_school.R.id.WebView1;

public class HomeFragment extends AppCompatActivity {
    WebView webviewku;
    WebSettings webSettingsku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        webviewku = (WebView) findViewById(WebView1);

        webSettingsku = webviewku.getSettings();
        webSettingsku.setJavaScriptEnabled(true);

        webviewku.setWebViewClient(new WebViewClient());
        webviewku.loadUrl("http://172.16.100.245:8000");


    }
}