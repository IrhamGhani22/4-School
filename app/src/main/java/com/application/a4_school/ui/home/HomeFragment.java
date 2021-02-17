package com.application.a4_school.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.application.a4_school.R;

import java.util.zip.Inflater;

import static com.application.a4_school.R.id.WebView1;

public class HomeFragment extends Fragment {
    WebView webviewku;
    WebSettings webSettingsku;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        webviewku = (WebView) root.findViewById(WebView1);

        webSettingsku = webviewku.getSettings();
        webSettingsku.setJavaScriptEnabled(true);

        webviewku.setWebViewClient(new WebViewClient());
        webviewku.loadUrl("http://192.168.43.234:8000");

        return root;
    }
}