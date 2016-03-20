package com.sprungsolutions.sitorstart;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dmax.dialog.SpotsDialog;

/**
 * Created by arisprung on 3/20/16.
 */
public class PlayerWebView extends AppCompatActivity {

    private WebView mWebView;

   // private SpotsDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_web_view_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_web);
        setSupportActionBar(toolbar);
        String url = getIntent().getStringExtra("player_url");
        mWebView=(WebView)findViewById(R.id.player_webview);
        mWebView.setWebViewClient(new WebViewClient());
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.loadUrl(url);


    }

    @Override
    protected void onResume() {
        super.onResume();

        String name = getIntent().getStringExtra("player_name");
//        mDialog = new SpotsDialog(PlayerWebView.this);
//        mDialog.setMessage("Downloading "+name+" data...");
//        mDialog.show();


    }

}
