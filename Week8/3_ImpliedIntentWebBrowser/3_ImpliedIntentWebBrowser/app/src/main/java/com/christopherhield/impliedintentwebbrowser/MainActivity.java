package com.christopherhield.impliedintentwebbrowser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String cnnURL = "http://www.cnn.com";
    private static final String androidURL = "https://developer.android.com";
    private static final String googleURL = "http://www.google.com";
    private static final String iitURL = "http://www.iit.edu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickCNN(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(cnnURL));
        startActivity(i);
    }

    public void clickAndroid(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(androidURL));
        startActivity(i);
    }

    public void clickGoogle(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(googleURL));
        startActivity(i);
    }

    public void clickIIT(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(iitURL));
        startActivity(i);
    }

    public void goTo(View v) {
        String address = ((EditText) findViewById(R.id.goToUrl)).getText().toString();
        if (!address.startsWith("http")) {
            address = "http://" + address;
        }

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(Uri.parse(address).toString());
    }
}
