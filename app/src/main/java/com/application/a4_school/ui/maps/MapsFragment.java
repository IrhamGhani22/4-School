package com.application.a4_school.ui.maps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;

import static com.application.a4_school.R.id.WebView1;

public class MapsFragment extends Fragment {
    WebView webviewku;
    WebSettings webSettingsku;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_maps, container, false);

        webviewku = (WebView) root.findViewById(WebView1);

        webSettingsku = webviewku.getSettings();
        webSettingsku.setJavaScriptEnabled(true);

        webviewku.setWebViewClient(new WebViewClient());
        webviewku.loadUrl(APIClient.BASE_URL);

        return root;
    }
}
