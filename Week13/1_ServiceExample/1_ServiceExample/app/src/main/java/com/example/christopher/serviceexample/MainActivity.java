package com.example.christopher.serviceexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private TextView textView;
    private CheckBox checkBox;
    private boolean serviceRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView3);
        checkBox = findViewById(R.id.checkBox);

    }

    public void startButton(View v) {
        if (serviceRunning) {
            Toast.makeText(this, "Service Already Running", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean doRight;
        if (!checkBox.isChecked()) {
            doRight = true;
            textView.setText("The SampleService is properly implemented.\nAll will be fine.");
        } else {
            doRight = false;
            textView.setText("The SampleService is NOT properly implemented.\nSee what happens in about 20 seconds....");
        }

        Intent intent = new Intent(MainActivity.this, SampleService.class);
        intent.putExtra("DO_RIGHT", doRight);
        startService(intent);
        serviceRunning = true;
    }

    public void stopButton(View v) {
        Intent intent = new Intent(MainActivity.this, SampleService.class);
        stopService(intent);
        serviceRunning = false;
    }

}