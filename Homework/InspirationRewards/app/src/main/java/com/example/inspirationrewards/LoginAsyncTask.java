package com.example.inspirationrewards;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

public class LoginAsyncTask extends AsyncTask<String, Double, String> {

    @SuppressLint("StaticFieldLeak")
    private LoginActivity loginActivity;
    private String rawURL = "";
    private String requestMethod = "";

    private LoginAsyncTask(LoginActivity la){
        this.loginActivity = la;
    }

    @Override
    protected String doInBackground(String... args){
        // url stuff here
        return "";
    }

    @Override
    protected void onPostExecute(String str){
        super.onPostExecute(str);
    }
}
