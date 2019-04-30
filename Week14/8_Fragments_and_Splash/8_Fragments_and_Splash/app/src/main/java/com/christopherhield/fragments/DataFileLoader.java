package com.christopherhield.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by chris on 4/13/2017.
 */

public class DataFileLoader {

    // This class loads a json file (animal_data.json) from the "raw" resource folder
    private static final String TAG = "DataFileLoader";


    public static void loadJSONFile(Activity owner) {
        InputStream is = owner.getResources().openRawResource(R.raw.animal_data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuilder result = new StringBuilder();
        String line;
        try {
            while (null != (line = reader.readLine())) {
                result.append(line).append("\n");
            }

            parseJSON(result.toString());

        } catch (IOException e) {
            Log.e(TAG, "doInBackground: IO Exception reading data: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: Error closing stream: " + e.getMessage());
                }
            }
        }
    }

    private static void parseJSON(String s) {
        try {
            ZooAnimal.clear();
            JSONArray itemArray = new JSONArray(s);
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject animal = itemArray.getJSONObject(i);
                String name = animal.getString("name");
                String latin = animal.getString("latin_name");
                String details = animal.getString("details");
                String height = animal.getString("height");
                String weight = animal.getString("weight");
                String distribution = animal.getString("distribution");
                String habitat = animal.getString("habitat");
                String wild_diet = animal.getString("wild_diet");
                String zoo_diet = animal.getString("zoo_diet");

                // Call static method of ZooAnimal class
                ZooAnimal.addNew(
                        name, latin, height, weight, distribution, habitat,
                        wild_diet, zoo_diet, details);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "parseJSON: JSON parse error: " + e.getMessage());
        }
    }
}
