package com.example.christopher.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class B_Activity extends AppCompatActivity {

    private static final String TAG = "B_Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_);

        TextView textView = (TextView) findViewById(R.id.activityLabel);

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            textView.setText("B_Activity\nOpened from " + text);
        }
    }

    public void clickE(View v) {
        Intent intent = new Intent(B_Activity.this, E_Activity.class);
        intent.putExtra(Intent.EXTRA_TEXT, B_Activity.class.getSimpleName());
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                String first = data.getStringExtra("X");
                String second = data.getStringExtra("Y");
                Log.d(TAG, "onActivityResult: X: " + first + ", Y: " + second);
            } else {
                Log.d(TAG, "onActivityResult: result Code: " + resultCode);
            }

        } else {
            Log.d(TAG, "onActivityResult: Request Code " + requestCode);
        }
    }

}
