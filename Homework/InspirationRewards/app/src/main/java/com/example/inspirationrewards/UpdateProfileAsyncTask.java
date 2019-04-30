package com.example.inspirationrewards;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

public class UpdateProfileAsyncTask extends AsyncTask<String, Double, String> {

    @SuppressLint("StaticFieldLeak")
    private EditActivity editActivity;
    private String rawURL = "";
    private String requestMethod = "";

    private UpdateProfileAsyncTask(EditActivity ea){
        this.editActivity = ea;
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
