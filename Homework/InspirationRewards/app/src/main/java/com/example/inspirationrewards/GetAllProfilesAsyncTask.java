package com.example.inspirationrewards;

import android.annotation.SuppressLint;
import android.icu.util.Output;
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
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static java.net.HttpURLConnection.HTTP_OK;

public class GetAllProfilesAsyncTask extends AsyncTask<String, Double, String> {

    //Related Activity
    @SuppressLint("StaticFieldLeak")
    private LeaderboardActivity leaderboardActivity;
    private Profile currentProfile;
    private String rawURL = "";
    private String requestMethod = "";

    //raw url and my hawk ID
    private static final String baseURL = "http://inspirationrewardsapi-env.6mmagpm2pv.us-east-2.elasticbeanstalk.com";
    private static final String ID =  "A20349890";

    //status var
    private static boolean status = false;

    public GetAllProfilesAsyncTask(LeaderboardActivity la, Profile cp){
        this.leaderboardActivity = la;
        this.currentProfile = cp;
    }

    @Override
    protected void onPostExecute(String str){
        if(str != null){
            if(status){
                Log.d("GET Success", str);
                //try to add get all users
                try{
                    JSONArray jArray = new JSONArray(str);

                    for(int i=0; i < jArray.length(); i++){
                        //For each profilein the array, we need its attributes
                        JSONObject currJO = jArray.getJSONObject(i);
                        String firstname = currJO.getString("firstName");
                        String lastname = currJO.getString("lastName");
                        String username = currJO.getString("username");
                        String department = currJO.getString("department");
                        String position = currJO.getString("position");
                        String story = currJO.getString("story");
                        String password = ""; //we don't want to get this for a view of a user's profile
                        int pointsToAward = currJO.getInt("pointsToAward");
                        boolean admin = currJO.getBoolean("admin");
                        byte[] photo = Base64.decode(currJO.getString("imageBytes"), Base64.DEFAULT);
                        String location = currJO.getString("location");

                        if(!currJO.isNull("rewards")){
                            JSONArray rewardsArray = new JSONArray(currJO.getString("rewards"));
                            List<Reward> rewardList = new ArrayList<>();

                            for(int j=0; j < rewardsArray.length(); j++){
                                JSONObject rewardObject = rewardsArray.getJSONObject(j);
                                String rewardUsername = rewardObject.getString("username");
                                String rewardName = rewardObject.getString("name");
                                String rewardDate = rewardObject.getString("date");
                                String rewardNote = rewardObject.getString("notes");
                                int rewardValue = rewardObject.getInt("value");

                                rewardList.add(new Reward(rewardValue, rewardUsername, rewardName, rewardNote, rewardDate));
                            }
                            Profile newProfile = new Profile(username, password, firstname, lastname, department, position, story, admin, pointsToAward, photo, location, rewardList);
                            this.leaderboardActivity.addProfile(newProfile);
                        }
                        else {
                            Profile newProfile = new Profile(username, password, firstname, lastname, department, position, story, admin, pointsToAward, photo, location, new ArrayList<Reward>());
                            this.leaderboardActivity.addProfile(newProfile);
                        }
                    }
                }
                catch(JSONException e){
                    Log.d(TAG, "onPostExecute: JSON exception");
                }
            }
            else{
                Log.d("GET Failure", str);
            }
        }
    }

    @Override
    protected String doInBackground(String... strings){
        String rawURL = baseURL + "/allprofiles";
        BufferedReader BR = null;
        HttpURLConnection con = null;

        try{
            Uri parsedURI = Uri.parse(rawURL);
            URL url = new URL(parsedURI.toString());
            con = (HttpURLConnection) url.openConnection();

            JSONObject jBody = getJSONBody();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.connect();

            OutputStreamWriter OSW = new OutputStreamWriter(con.getOutputStream());
            OSW.write(jBody.toString());
            OSW.close();

            int responseCode = con.getResponseCode();
            StringBuilder SB = new StringBuilder();

            if(responseCode == HTTP_OK){  //request success
                BR = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String currentLine;

                while(null != (currentLine  = BR.readLine())){
                    SB.append(currentLine).append("\n");
                }

                status = true;

                return SB.toString();
            }
            else{  //request failed
                BR = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String currentLine;

                while(null != (currentLine  = BR.readLine())){
                    SB.append(currentLine).append("\n");
                }

                return SB.toString();
            }
        }
        catch(MalformedURLException e){
            Log.d(TAG, "doInBackground: Malformed url");
        }
        catch(ProtocolException e){
            Log.d(TAG, "doInBackground: Protocol Exception");
        }
        catch(IOException e){
            Log.d(TAG, "doInBackground: IOException");
        }
        return null;
    }

    private JSONObject getJSONBody() {
        JSONObject jBody = new JSONObject();
        try {
            jBody.put("studentId", ID);
            jBody.put("username", currentProfile.getUsername());
            jBody.put("password", currentProfile.getPassword());
        } catch (JSONException e) {
            Log.d(TAG, "getJSONBody: json exception");
        }
        return jBody;
    }
}
