package com.christopherhield.broadcastandreceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SampleReceiver extends BroadcastReceiver {

    private static final String TAG = "SampleReceiver";
    private MainActivity mainActivity;

    public SampleReceiver(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent == null || intent.getAction() == null)
            return;

        switch (intent.getAction()) {
            case MainActivity.SAMPLE_BROADCAST_TYPE_A:
                String value = "";
                if (intent.hasExtra(MainActivity.DATA_EXTRA1))
                    value = intent.getStringExtra(MainActivity.DATA_EXTRA1);
                Toast.makeText(mainActivity, "Broadcast Message A Received: " + value, Toast.LENGTH_LONG).show();
                break;
            case MainActivity.SAMPLE_BROADCAST_TYPE_B:
                String v1 = "";
                String v2 = "";
                if (intent.hasExtra(MainActivity.DATA_EXTRA2))
                    v1 = intent.getStringExtra(MainActivity.DATA_EXTRA2);
                if (intent.hasExtra(MainActivity.DATA_EXTRA3))
                    v2 = intent.getStringExtra(MainActivity.DATA_EXTRA3);
                Toast.makeText(mainActivity, "Broadcast Message  B Received: " + v1 + " / " + v2, Toast.LENGTH_LONG).show();
                break;
            default:
                Log.d(TAG, "onReceive: Unexpected broadcast: " + intent.getAction());
        }
    }

}
