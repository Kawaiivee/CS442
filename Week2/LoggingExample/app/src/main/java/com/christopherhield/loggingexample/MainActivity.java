package com.christopherhield.loggingexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    // Type "logt<TAB>" to have this added automatically
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Type in "logd<TAB>" to have this added automatically
        Log.d(TAG, "onCreate: This is in onCreate!");
    }
}
