package com.example.christopher.async;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Date;

public class MyAsyncTask extends AsyncTask<Long, Integer, String> { //  <Parameter, Progress, Result>

    private static final String TAG = "MyAsyncTask";
    private MainActivity mainActivity;

    public MyAsyncTask(MainActivity ma) {
        mainActivity = ma;
    }

    @Override
    protected String doInBackground(Long... params) {

        Log.d(TAG, "doInBackground: Starting background execution");
        try {
            if (params[0] != 0) {
                long seconds = params[0];

                for (int i = 0; i < seconds; i++) {

                    Thread.sleep(1000); // 1 second in millis

                    publishProgress(i+1); // An AsyncTask method

                    Log.d(TAG, "doInBackground: Second = " + (i+1));
                }
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        Log.d(TAG, "doInBackground: Done - returning timestamp");
        return MainActivity.formatter.format(new Date());
    }

    @Override
    protected void onPreExecute() {
        // This method is used infrequently - we do nothing in this example
        super.onPreExecute();
        Log.d(TAG, "onPreExecute: ");
    }

    @Override
    protected void onPostExecute(String string) {
        // This method is almost always used - handles the results of doInBackground
        super.onPostExecute(string);
        Log.d(TAG, "onPostExecute: " + string);

        mainActivity.whenAsyncIsDone(string);

        Log.d(TAG, "onPostExecute: AsyncTask terminating successfully");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // This method is used with long running tasks
        super.onProgressUpdate(values);

        mainActivity.updateProgressBar(values[0]);

        Log.d(TAG, "onProgressUpdate: " + values[0]);
    }
}
