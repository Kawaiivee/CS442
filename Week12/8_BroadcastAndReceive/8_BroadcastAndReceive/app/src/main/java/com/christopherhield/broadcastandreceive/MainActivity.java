package com.christopherhield.broadcastandreceive;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String SAMPLE_BROADCAST_TYPE_A = "A Type Message";
    public static final String SAMPLE_BROADCAST_TYPE_B = "B Type Message";
    public static final String DATA_EXTRA1 = "DATA_EXTRA1";
    public static final String DATA_EXTRA2 = "DATA_EXTRA2";
    public static final String DATA_EXTRA3 = "DATA_EXTRA3";

    private SampleReceiver sampleReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sampleReceiver = new SampleReceiver(this);

        IntentFilter filter1 = new IntentFilter(SAMPLE_BROADCAST_TYPE_A);
        registerReceiver(sampleReceiver, filter1);

        IntentFilter filter2 = new IntentFilter(SAMPLE_BROADCAST_TYPE_B);
        registerReceiver(sampleReceiver, filter2);
    }

    @Override
    protected void onDestroy() {
        // Don't forget to unregister!
        unregisterReceiver(sampleReceiver);
        super.onDestroy();
    }

    public void sendA(View v) {
        if (((EditText) findViewById(R.id.editText)).getText().length() ==0)
            return;

        Intent intent = new Intent();
        intent.setAction(SAMPLE_BROADCAST_TYPE_A);
        String data = ((EditText) findViewById(R.id.editText)).getText().toString();
        intent.putExtra(DATA_EXTRA1, data);
        sendBroadcast(intent);
    }

    public void sendB(View v) {
        if (((EditText) findViewById(R.id.editText2)).getText().length() ==0)
            return;

        Intent intent = new Intent();
        intent.setAction(SAMPLE_BROADCAST_TYPE_B);
        String data = ((EditText) findViewById(R.id.editText2)).getText().toString();
        String reversed = new StringBuilder(data).reverse().toString();
        intent.putExtra(DATA_EXTRA2, data);
        intent.putExtra(DATA_EXTRA3, reversed);
        sendBroadcast(intent);
    }

}
