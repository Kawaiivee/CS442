package com.christopherhield.linkifyexamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView url = findViewById(R.id.url);
        TextView phone = findViewById(R.id.phone);
        TextView phone2 = findViewById(R.id.phone2);
        TextView address = findViewById(R.id.address);
        TextView email = findViewById(R.id.email);

        Linkify.addLinks(url, Linkify.WEB_URLS);
        Linkify.addLinks(phone, Linkify.PHONE_NUMBERS);
        Linkify.addLinks(phone2, Linkify.PHONE_NUMBERS);
        Linkify.addLinks(address, Linkify.MAP_ADDRESSES);
        Linkify.addLinks(email, Linkify.EMAIL_ADDRESSES);

    }

    public void radioChecked(View v) {

        switch (v.getId()) {
            case R.id.exact:
                Linkify.addLinks(((TextView) findViewById(R.id.url)), Linkify.WEB_URLS);
                Linkify.addLinks(((TextView) findViewById(R.id.phone)), Linkify.PHONE_NUMBERS);
                Linkify.addLinks(((TextView) findViewById(R.id.phone2)), Linkify.PHONE_NUMBERS);
                Linkify.addLinks(((TextView) findViewById(R.id.address)), Linkify.MAP_ADDRESSES);
                Linkify.addLinks(((TextView) findViewById(R.id.email)), Linkify.EMAIL_ADDRESSES);
                break;
            case R.id.detect:
                Linkify.addLinks(((TextView) findViewById(R.id.url)), Linkify.ALL);
                Linkify.addLinks(((TextView) findViewById(R.id.phone)), Linkify.ALL);
                Linkify.addLinks(((TextView) findViewById(R.id.phone2)), Linkify.ALL);
                Linkify.addLinks(((TextView) findViewById(R.id.address)), Linkify.ALL);
                Linkify.addLinks(((TextView) findViewById(R.id.email)), Linkify.ALL);
                break;
        }

    }
}
