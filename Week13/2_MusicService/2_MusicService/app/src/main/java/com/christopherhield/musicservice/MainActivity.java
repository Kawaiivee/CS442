package com.christopherhield.musicservice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean serviceRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startButton(View v) {
        if (serviceRunning) {
            Toast.makeText(this, "Service already running", Toast.LENGTH_SHORT).show();
            return;
        }

        startService(new Intent(MainActivity.this, MusicService.class));
        findViewById(R.id.layout).setBackgroundColor(Color.GREEN);
        serviceRunning = true;
    }

    public void stopButton(View v) {
        stopService(new Intent(MainActivity.this, MusicService.class));
        findViewById(R.id.layout).setBackgroundColor(Color.WHITE);
        serviceRunning = false;
    }
}
