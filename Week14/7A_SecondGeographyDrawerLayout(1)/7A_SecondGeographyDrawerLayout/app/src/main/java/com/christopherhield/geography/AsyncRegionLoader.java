package com.christopherhield.geography;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;


public class AsyncRegionLoader extends AsyncTask<String, Integer, String> {

    @SuppressLint("StaticFieldLeak")
    private MainActivity mainActivity;

    private static final String dataURL = "https://restcountries.eu/rest/v2/all?fields=region;subregion";

    AsyncRegionLoader(MainActivity ma) {
        mainActivity = ma;
    }


    @Override
    protected void onPostExecute(String s) {
        HashMap<String, HashSet<String>> regionMap = parseJSON(s);
        mainActivity.setupRegions(regionMap);
    }


    @Override
    protected String doInBackground(String... params) {


        Uri dataUri = Uri.parse(dataURL);
        String urlToUse = dataUri.toString();

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return sb.toString();
    }


    private HashMap<String, HashSet<String>> parseJSON(String s) {

        HashMap<String, HashSet<String>> regionMap = new HashMap<>();
        try {
            JSONArray jObjMain = new JSONArray(s);

            // Here we only want to regions and subregions
            for (int i = 0; i < jObjMain.length(); i++) {
                JSONObject jCountry = (JSONObject) jObjMain.get(i);
                String region = jCountry.getString("region");
                String subRegion = jCountry.getString("subregion");

                if (region.isEmpty())
                    continue;

                if (subRegion.isEmpty())
                    subRegion = "Unspecified";

                if (!regionMap.containsKey(region))
                    regionMap.put(region, new HashSet<String>());

                regionMap.get(region).add(subRegion);

            }
            return regionMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
