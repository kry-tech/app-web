package com.exemplo.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

public class MainActivity extends ComponentActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webView = new WebView(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebChromeClient(new WebChromeClient());

        webView.addJavascriptInterface(
            new AndroidBridge(),
            "Android"
        );

        webView.loadUrl("file:///android_asset/index.html");

        setContentView(webView);
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
