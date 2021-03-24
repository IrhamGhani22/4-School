package com.application.a4_school.ui.maps;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;

import static com.application.a4_school.R.id.WebView1;

public class MapsFragment extends Fragment {
    WebView webviewku;
    WebSettings webSettingsku;
    LottieAnimationView pd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_maps, container, false);

        webviewku = (WebView) root.findViewById(WebView1);
        pd = root.findViewById(R.id.loading_maps);
        webSettingsku = webviewku.getSettings();
        webSettingsku.setJavaScriptEnabled(true);
        webSettingsku.setLoadWithOverviewMode(true);
        webSettingsku.setUseWideViewPort(true);
        webSettingsku.setBuiltInZoomControls(true);
        webviewku.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pd.animate().alpha(0f).setDuration(800);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.setVisibility(View.GONE);
                    }
                },700);
                String webUrl = webviewku.getUrl();
            }
        });
        webviewku.loadUrl(APIClient.BASE_URL);

        return root;
    }
}
