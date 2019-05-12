package com.example.newsgateway;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class SourceAsyncTask extends AsyncTask<String, Integer, String> {
    private MainActivity mainActivity;
    private String prefix = "https://newsapi.org/v1/sources?language=en&country=us&apiKey=";
    private String apiKey = "445e50b2893a4432a727f58e7bdb41b1";

    public SourceAsyncTask(MainActivity ma){
        this.mainActivity = ma;
    }

    @Override
    protected String doInBackground(String... args){
        StringBuilder SB = new StringBuilder();

        try{
            Uri url = Uri.parse(prefix+apiKey);
            URL newUrl = new URL(url.toString());

            HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
            conn.setRequestMethod("GET");
            InputStream IS = conn.getInputStream();
            BufferedReader BR = new BufferedReader((new InputStreamReader(IS)));

            String currentLine;
            while((currentLine = BR.readLine()) != null) {
                SB.append(currentLine).append("\n");
            }
            return SB.toString();
        }
        catch(IOException e){
            Log.d("IOE", "doInBackground: IOException");
        }
        return null;
    }

    @Override
    protected void onPostExecute(String rawJSON){
        parseJSON(rawJSON);
    }

    private void parseJSON(String rawJSON){
        try{
            JSONObject object = new JSONObject(rawJSON);
            JSONArray sourceArray = object.getJSONArray("sources");
            mainActivity.clearSource();

            ArrayList<Source> sourceArrayList = new ArrayList<>();

            for(int i=0; i<sourceArray.length(); i++){
                JSONObject sourceObject = sourceArray.getJSONObject(i);

                String id = sourceObject.getString("id");
                String name = sourceObject.getString("name");
                String url = sourceObject.getString("url");
                String category = sourceObject.getString("category");

                Source source = new Source(id, name, url, category);
                sourceArrayList.add(source);
            }

            mainActivity.addSources(sourceArrayList);



        }
        catch(JSONException e){
            Log.d("IOE", "IOException");
        }
    }

}
