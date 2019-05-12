package com.christopherhield.a_widget;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


class AsyncLoaderTask extends AsyncTask<String, Void, Void> {

    private static final String TAG = "AsyncLoaderTask";
    private Widget widget;

    private static final String weatherURL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String iconUrl = "http://openweathermap.org/img/w/";
    private static final String yourAPIKey = "6bfc226f3d6885de5b239a8c33047524";

    private Bitmap bitmap; // This is set in the doInBackground -> parseJSON method
    private String temp; // This is set in the doInBackground -> parseJSON method

    AsyncLoaderTask(Widget w) {
        widget = w;
    }

    @Override
    protected Void doInBackground(String... params) {

        Log.d(TAG, "doInBackground: City: " + params[0]);
        Uri.Builder buildURL = Uri.parse(weatherURL).buildUpon();

        buildURL.appendQueryParameter("q", params[0]);
        buildURL.appendQueryParameter("units", "imperial");
        buildURL.appendQueryParameter("appid", yourAPIKey);
        String urlToUse = buildURL.build().toString();
        Log.d(TAG, "doInBackground: URL: " + urlToUse);

        HttpURLConnection conn = null;
        BufferedReader reader = null;

        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append('\n');
            }

            parseJSON(result.toString());

        } catch (Exception e) {
            Log.e(TAG, "doInBackground: Invalid URL: " + e.getMessage());
        }  finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: Error closing stream: " + e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        Log.d(TAG, "onPostExecute: , TEMP: " + temp);
        Log.d(TAG, "onPostExecute: , BITMAP: " + bitmap.getWidth() + "x" + bitmap.getHeight());
        double d = Double.parseDouble(temp);
        widget.updateData((int) Math.round(d), bitmap);
    }


    private void parseJSON(String s) {

        try {
            JSONObject jObjMain = new JSONObject(s);

            JSONObject jMain = jObjMain.getJSONObject("main");
            temp =  jMain.getString("temp");

            JSONArray weather = jObjMain.getJSONArray("weather");
            JSONObject jWeather = (JSONObject) weather.get(0);
            String icon = jWeather.getString("icon");

            bitmap = null;
            try {
                InputStream input = new java.net.URL(iconUrl + icon + ".png").openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
