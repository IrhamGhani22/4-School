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
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;

import java.util.zip.Inflater;

import static com.application.a4_school.R.id.WebView1;

public class HomeFragment extends Fragment {
    WebView webviewku;
    WebSettings webSettingsku;
    ProgressBar loading;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        loading = root.findViewById(R.id.loading);
        webviewku = (WebView) root.findViewById(WebView1);

        webSettingsku = webviewku.getSettings();
        webSettingsku.setJavaScriptEnabled(true);

        webviewku.setWebViewClient(new WebViewClient());
        webviewku.loadUrl(APIClient.BASE_URL);

        return root;
    }
}