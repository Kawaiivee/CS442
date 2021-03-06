package com.example.christopher.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class A_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_);

        TextView textView = (TextView) findViewById(R.id.activityLabel);

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            textView.setText("A_Activity\nOpened from " + text);
        }
    }

    public void clickC(View v) {
        Intent intent = new Intent(A_Activity.this, C_Activity.class);
        intent.putExtra(Intent.EXTRA_TEXT, A_Activity.class.getSimpleName());
        startActivity(intent);
    }

    public void clickD(View v) {
        Intent intent = new Intent(A_Activity.this, D_Activity.class);
        intent.putExtra(Intent.EXTRA_TEXT, A_Activity.class.getSimpleName());
        startActivity(intent);
    }
}
