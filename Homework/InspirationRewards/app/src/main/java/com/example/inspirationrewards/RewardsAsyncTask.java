package com.example.inspirationrewards;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

public class RewardsAsyncTask extends AsyncTask<String, Double, String> {

    @SuppressLint("StaticFieldLeak")
    private AwardActivity awardActivity;
    private String rawURL = "";
    private String requestMethod = "";

    private RewardsAsyncTask(AwardActivity aa){
        this.awardActivity = aa;
    }

    @Override
    protected String doInBackground(String... strArgs){
        return "";
    }

    @Override
    protected void onPostExecute(String str){
        super.onPostExecute(str);
    }
}
