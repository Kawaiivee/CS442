package com.christopherhield.geography;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.AsyncTask;

import com.caverock.androidsvg.SVG;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AsyncSubRegionLoader extends AsyncTask<String, Integer, ArrayList<Country>> {

    @SuppressLint("StaticFieldLeak")
    private MainActivity mainActivity;
    private String selectedSubRegion;

    private static final String dataURL = "https://restcountries.eu/rest/v2/all";

    AsyncSubRegionLoader(MainActivity ma) {
        mainActivity = ma;
    }


    @Override
    protected void onPostExecute(ArrayList<Country> countryList) {
        mainActivity.setCountries(countryList);
    }


    @Override
    protected ArrayList<Country> doInBackground(String... params) {

        selectedSubRegion = params[0];

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

        ArrayList<Country> countryList = parseJSON(sb.toString());

        return countryList;
    }


    private ArrayList<Country> parseJSON(String s) {

        ArrayList<Country> countryList = new ArrayList<>();
        try {
            JSONArray jObjMain = new JSONArray(s);

            // Here we only want to regions and subregions
            for (int i = 0; i < jObjMain.length(); i++) {
                JSONObject jCountry = (JSONObject) jObjMain.get(i);
                String name = jCountry.getString("name");
                String capital = jCountry.getString("capital");
                String region = jCountry.getString("region");
                String subRegion = jCountry.getString("subregion");

                if (subRegion.isEmpty())
                    subRegion = "Unspecified";

                if (!subRegion.equals(selectedSubRegion))
                    continue;

                String flag = jCountry.getString("flag");

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

                Drawable drawable = null;
                try {
                    InputStream input = new java.net.URL(flag).openStream();
                    SVG svg = SVG.getFromInputStream(input);
                    drawable = new PictureDrawable(svg.renderToPicture());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                countryList.add(
                        new Country(name, capital, population, region, subRegion,
                                area, citizen, codes, borders, drawable));

            }
            return countryList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
