package com.example.inspirationrewards;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

public class RewardsAsyncTask extends AsyncTask<String, Double, String> {

    private static final String baseURL = "http://inspirationrewardsapi-env.6mmagpm2pv.us-east-2.elasticbeanstalk.com";
    private static final String ID =  "A20349890";
    private static boolean statues = false;

    @SuppressLint("StaticFieldLeak")
    private AwardActivity awardActivity;
    private String receiverUsername;
    private String receiverName;
    private String date;
    private String comment;
    private int amount;

    //not yet implemented
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
