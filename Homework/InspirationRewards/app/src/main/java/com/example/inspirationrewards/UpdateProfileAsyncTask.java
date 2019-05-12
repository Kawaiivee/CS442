package com.example.inspirationrewards;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.net.HttpURLConnection.HTTP_OK;

public class UpdateProfileAsyncTask extends AsyncTask<String, Double, String> {

    private static final String baseURL = "http://inspirationrewardsapi-env.6mmagpm2pv.us-east-2.elasticbeanstalk.com";
    private static final String ID =  "A20349890";

    private static boolean status = false;



    //not yet implemented
    //same as create but keep track of unchanged data and points?
    @SuppressLint("StaticFieldLeak")
    private EditActivity editActivity;
    private Profile profile;
    private String rawURL = "";
    private String requestMethod = "";

    public UpdateProfileAsyncTask(EditActivity ea, Profile p){
        this.editActivity = ea;
        this.profile = p;
    }

    @Override
    protected String doInBackground(String... strArgs){
        //url stuff here
        String rawURL = baseURL + "/profiles";
        BufferedReader BR = null;
        HttpURLConnection con = null;

        //JSON
        JSONObject jBody = new JSONObject();
        try {
            jBody.put("studentId", ID);
            jBody.put("username", profile.getUsername());
            jBody.put("password", profile.getPassword());
            jBody.put("firstName", profile.getFirstname());
            jBody.put("lastName", profile.getLastname());
            jBody.put("pointsToAward", profile.getPoints());
            jBody.put("department", profile.getDepartment());
            jBody.put("story", profile.getStory());
            jBody.put("position", profile.getPosition());
            jBody.put("admin", profile.isAdmin());
            jBody.put("location", profile.getLocation());
            jBody.put("imageBytes", Base64.encode(profile.getPhoto(), Base64.DEFAULT));

            JSONArray jsonArray = new JSONArray();

            for (int i = 0; i < profile.getRewardList().size(); i++) {
                JSONObject jsonObject = new JSONObject();
                Reward reward = profile.getRewardList().get(i);
                jsonObject.put("name", reward.getSenderName());
                jsonObject.put("date", reward.getDate());
                jsonObject.put("notes", reward.getComment());
                jsonObject.put("value", reward.getAmount());

                jsonArray.put(jsonObject);
            }

            jBody.put("rewardRecords", jsonArray);


            //try connecting to url
            Uri parsedURI = Uri.parse(rawURL);
            URL url = new URL(parsedURI.toString());
            con = (HttpURLConnection) url.openConnection();

            //Set Request Method to post and initialize prop
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.connect();

            //Output stream to 'POST'
            OutputStreamWriter OSW = new OutputStreamWriter(con.getOutputStream());
            OSW.write(jBody.toString());
            OSW.close();

            //Response code
            int responseCode = con.getResponseCode();

            //Init a string builder to get ready for the response
            StringBuilder SB = new StringBuilder();
            if (responseCode == HTTP_OK){
                BR = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String currentLine;
                while(null != (currentLine = BR.readLine())){
                    SB.append(currentLine).append("\n");
                }

                //Set status to true and final StringBuilder object
                status = true;
                return SB.toString();
            }

            //Request fails
            else{
                BR = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String currentLine;
                while(null != (currentLine = BR.readLine())){
                    SB.append(currentLine).append("\n");
                }
                return SB.toString();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    protected void onPostExecute(String str){
        if (status) {
            Log.d("Update", "status: " + str);
            Intent intent = new Intent();
            intent.putExtra("profile", profile);
            editActivity.setResult(1, intent);
            editActivity.finish();
        }
    }
}
