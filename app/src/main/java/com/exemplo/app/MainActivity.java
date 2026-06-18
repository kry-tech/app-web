package com.exemplo.app;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

public class MainActivity extends ComponentActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout splash = new LinearLayout(this);
        splash.setOrientation(LinearLayout.VERTICAL);
        splash.setGravity(Gravity.CENTER);
        splash.setBackgroundColor(Color.parseColor("#121212"));

        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);

        TextView text = new TextView(this);
        text.setText("Carregando...");
        text.setTextColor(Color.WHITE);
        text.setTextSize(18);
        text.setPadding(0, 30, 0, 0);

        splash.addView(progressBar);
        splash.addView(text);

        setContentView(splash);

        WebView webView = new WebView(this);

        webView.setBackgroundColor(Color.parseColor("#121212"));

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        webView.setWebChromeClient(new WebChromeClient());

        webView.addJavascriptInterface(
            new AndroidBridge(),
            "Android"
        );

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                setContentView(webView);
            }
        });

        webView.loadUrl("file:///android_asset/index.html");
    }

    public class AndroidBridge {

        @JavascriptInterface
        public void toast(String text) {
            runOnUiThread(() ->
                Toast.makeText(
                    MainActivity.this,
                    text,
                    Toast.LENGTH_SHORT
                ).show()
            );
        }

        @JavascriptInterface
        public String getVersion() {
            return "1.0";
        }
    }
}
