package com.example.christopher.countries;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class AsyncCountryLoader extends AsyncTask<String, Integer, String> {

    private MainActivity mainActivity;

    private static final String DATA_URL = "https://restcountries.eu/rest/v1/all";
    private static final String TAG = "AsyncCountryLoader";

    public AsyncCountryLoader(MainActivity ma) {
        mainActivity = ma;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(mainActivity, "Loading Country Data...", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPostExecute(String s) {
        ArrayList<Country> countryList = parseJSON(s);
        if (countryList != null)
            Toast.makeText(mainActivity, "Loaded " + countryList.size() + " countries.", Toast.LENGTH_SHORT).show();
        mainActivity.updateData(countryList);
    }


    @Override
    protected String doInBackground(String... params) {

        Uri dataUri = Uri.parse(DATA_URL);
        String urlToUse = dataUri.toString();
        Log.d(TAG, "doInBackground: " + urlToUse);

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            Log.d(TAG, "doInBackground: ResponseCode: " + conn.getResponseCode());

            conn.setRequestMethod("GET");

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            Log.d(TAG, "doInBackground: " + sb.toString());

        } catch (Exception e) {
            Log.e(TAG, "doInBackground: ", e);
            return null;
        }

        Log.d(TAG, "doInBackground: " + sb.toString());

        return sb.toString();
    }


    private ArrayList<Country> parseJSON(String s) {

        ArrayList<Country> countryList = new ArrayList<>();
        try {
            JSONArray jObjMain = new JSONArray(s);

            for (int i = 0; i < jObjMain.length(); i++) {
                JSONObject jCountry = (JSONObject) jObjMain.get(i);
                String name = jCountry.getString("name");
                String capital = jCountry.getString("capital");
                String region = jCountry.getString("region");
                String subRegion = jCountry.getString("subregion");

                String pop = jCountry.getString("population");
                int population = 0;
                if (pop != null && !pop.trim().isEmpty())
                    population = Integer.parseInt(pop);

                String a = jCountry.getString("area");
                int area = 0;
                if (a != null && !a.trim().isEmpty() && !a.trim().equals("null"))
                    area = (int) Double.parseDouble(a);

                String citizen = jCountry.getString("demonym");

                String codes = "";
                JSONArray jCodes = jCountry.getJSONArray("callingCodes");
                for (int j = 0; j < jCodes.length(); j++) {
                    codes += jCodes.get(j) + " ";
                }

                String borders = "";
                JSONArray jBorders = jCountry.getJSONArray("borders");
                for (int j = 0; j < jBorders.length(); j++) {
                    borders += jBorders.get(j) + " ";
                }

                countryList.add(
                        new Country(name, capital, population, region, subRegion,
                                area, citizen, codes, borders));

            }
            return countryList;
        } catch (Exception e) {
            Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


}
