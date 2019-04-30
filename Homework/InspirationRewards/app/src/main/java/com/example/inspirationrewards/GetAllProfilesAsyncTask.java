package com.example.inspirationrewards;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

public class GetAllProfilesAsyncTask extends AsyncTask<String, Double, String> {

    //Related Activity
    @SuppressLint("StaticFieldLeak")
    private LeaderboardActivity leaderboardActivity;
    private String rawURL = "";
    private String requestMethod = "";

    private GetAllProfilesAsyncTask(LeaderboardActivity la){
        this.leaderboardActivity = la;
    }

    @Override
    protected String doInBackground(String... strArgs){
        //url stuff here
        return "";
    }

    @Override
    protected void onPostExecute(String str){
        super.onPostExecute(str);
    }
}
