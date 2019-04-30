package com.christopherhield.alarmserviceexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Christopher on 3/29/2017.
 */


// New ==> Other ==> BroadcastReceiver
public class OnAlarmReceive extends BroadcastReceiver {

    private static final String TAG = "OnAlarmReceive";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "OnAlarmReceive onReceive called!");

        String msg = "";

        if (intent.hasExtra("DATA"))
            msg = intent.getStringExtra("DATA");

        // Start MainActivity
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra("DATA", msg + "\nWhat did I miss?");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
