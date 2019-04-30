package com.example.inspirationrewards;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;
import static java.net.HttpURLConnection.HTTP_OK;

public class CreateProfileAsyncTask extends AsyncTask<String,Integer,String>{

    //raw url and my hawk ID
    private static final String baseURL = "http://inspirationrewardsapi-env.6mmagpm2pv.us-east-2.elasticbeanstalk.com";
    private static final String ID =  "A20349890";


    //data we need to create a profile
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String department;
    private String position;
    private String story;
    private boolean admin;
    private String location;
    private String photo;

    //status of request. request was successful -> true, request failed -> false
    private static boolean status = false;

    private CreateProfileAsyncTask(
            String username,
            String password,
            String firstname,
            String lastname,
            String department,
            String position,
            String story,
            boolean admin,
            String location,
            String photo
    ){
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.department = department;
        this.position = position;
        this.story = story;
        this.admin = admin;
        this.location = location;
        this.photo = photo;
    }

    @Override
    protected String doInBackground(String... strArgs){
        String rawURL = baseURL + "/profiles";
        BufferedReader BR = null;
        HttpURLConnection con = null;

        try{
            //try connecting to url
            Uri parsedURI = Uri.parse(rawURL);
            URL url = new URL(parsedURI.toString());
            con = (HttpURLConnection) url.openConnection();

            //JSON
            JSONObject jBody = getJSONBody();

            //Set Request Method to post and initialize prop
            con.setRequestMethod("POST");
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
            if(responseCode == HTTP_OK){
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
        }
        catch(MalformedURLException e){
            Log.d(TAG, "doInBackground: malformed url");
        }
        catch(IOException e){
            Log.d(TAG, "doInBackground: ioexception");
        }
        finally{
            //Close connection
            if(con != null){
                con.disconnect();
            }
            if(BR != null){
                try{
                    BR.close();
                }
                catch(IOException e){
                    Log.d(TAG, "doInBackground: error closing bufferedreader");
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String str){
        super.onPostExecute(str);
    }

    //method to parse the received JSON object
    private JSONObject getJSONBody(){

        JSONObject jBody = new JSONObject();
        try{
            jBody.put("studentId", ID);
            jBody.put("username", this.username);
            jBody.put("password", this.password);
            jBody.put("firstname", this.firstname);
            jBody.put("lastname", this.lastname);
            jBody.put("pointsToAward", 1000); //1000 initial points
            jBody.put("department", this.department);
            jBody.put("story", this.story);
            jBody.put("position", this.position);
            jBody.put("admin", this.admin);
            jBody.put("location", this.location);
            jBody.put("imageBytes", this.photo);
            jBody.put("rewardRecords", new JSONArray()); //initialize a new rewards list
        }
        catch(JSONException e){
            Log.d(TAG, "getJSONBody: something went wront in making a json body");
        }
        return jBody;
    }
}