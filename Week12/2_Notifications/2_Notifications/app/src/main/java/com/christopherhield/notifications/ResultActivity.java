package com.christopherhield.notifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int level = getIntent().getIntExtra("LEVEL", -1);
        ((TextView) findViewById(R.id.textView2)).setText("Battery at " + level + "%");

    }
}
