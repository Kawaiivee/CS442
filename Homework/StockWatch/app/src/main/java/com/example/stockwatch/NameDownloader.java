package com.example.stockwatch;

import android.os.AsyncTask;
import android.support.v7.widget.DialogTitle;

public class NameDownloader extends AsyncTask<Long, Integer, String> {

    private MainActivity mainActivity;

    public NameDownloader(MainActivity ma){
        mainActivity = ma;
    }

    @Override
    protected String doInBackground(){
        
    }
}
