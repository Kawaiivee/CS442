package com.example.christopher.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class E_Activity extends AppCompatActivity {

    private static final String TAG = "E_Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_);

        TextView textView = (TextView) findViewById(R.id.activityLabel);

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            textView.setText("E_Activity\nOpened from " + text);
        }
    }

    public void clickReturn(View v) {
        Intent data = new Intent();
        data.putExtra("X", "123");
        data.putExtra("Y", "456");
        setResult(RESULT_OK, data);
        finish();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");

    }
}
