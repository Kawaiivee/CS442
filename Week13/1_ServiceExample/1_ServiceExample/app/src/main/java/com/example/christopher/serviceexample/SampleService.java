package com.example.christopher.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


// New ==> Service ==> Service
public class SampleService extends Service {

    private static final String TAG = "SampleService";
    private boolean isRunning = true;
    private boolean doRight;

    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");
        return null;  //  Don't want clients to bind to the service
    }

    // This one is like onCreate for an Activity
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();

        if (intent == null) {
            doRight = true;
        }
        else if (intent.hasExtra("DO_RIGHT")) {
            doRight = intent.getBooleanExtra("DO_RIGHT", true);
        }

        final long interval = 1000;

        if (doRight) { // Run the service properly

            //Creating new thread for my service
            //Always perform your long running tasks in a separate thread, to avoid ANR

            new Thread(new Runnable() {
                @Override
                public void run() {

                    // In this example we display a log message every 1000ms
                    // In reality, this can be getting data from the internet, playing music,
                    // or any other long-running "background" task
                    while (isRunning) {
                        long start = System.currentTimeMillis();

                        if (isRunning) {
                            Log.d(TAG, "Hello - I'm running properly in my own thread!");
                        }

                        long time = interval - (System.currentTimeMillis() - start);
                        Log.d(TAG, "run: Sleep time: " + time + " ms");

                        try {
                            Thread.sleep(interval - (System.currentTimeMillis() - start));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    Log.i(TAG, "SampleService was properly stopped");
                }
            }).start();


        } else { // Run the service incorrectly - ANR results

            // In this example we display a log message every 1000ms
            // In reality, this can be getting data from the internet, playing music,
            // or any other long-running "background" task
            while (isRunning) {
                long start = System.currentTimeMillis();

                if (isRunning) {
                    Log.d(TAG, "Hello - I'm running IMPROPERLY!");
                }

                long time = interval - (System.currentTimeMillis() - start);
                Log.d(TAG, "run: Sleep time: " + time);

                try {
                    Thread.sleep(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            Log.i(TAG, "SampleService was properly stopped");
        }

        Log.d(TAG, "onStartCommand: EXITED onStartCommand");

        // Return code here tells the system what it should do with the service i
        // if its process is killed while it is running
        // START_STICKY tells the OS to recreate the service after it has enough
        // memory and call onStartCommand() again with a null intent.
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        isRunning = false;
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }
}