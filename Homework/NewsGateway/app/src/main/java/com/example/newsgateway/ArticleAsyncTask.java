package com.example.newsgateway;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ArticleAsyncTask extends AsyncTask<String, Integer, String> {

    private NewsService newsService;
    private String prefix = "https://newsapi.org/v1/articles?apiKey=";
    private String apiKey = "445e50b2893a4432a727f58e7bdb41b1&source=";

    private ArrayList<Article> articleArrayList;

    public ArticleAsyncTask(NewsService ns, ArrayList<Article> arrayList){
        this.newsService = ns;
        this.articleArrayList = arrayList;
    }

    @Override
    protected String doInBackground(String... args){
        //First parameter given is the souce
        String source = args[0];
        Log.d("Articles", "id: " + source);
        StringBuilder SB = new StringBuilder();

        try{
            URL url = new URL(prefix + apiKey + source);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream IS = conn.getInputStream();
            BufferedReader BR = new BufferedReader((new InputStreamReader(IS)));

            String currentLine;
            while((currentLine = BR.readLine()) != null){
                SB.append(currentLine).append("\n");
            }
            return SB.toString();
        }
        catch(MalformedURLException e){
            Log.d(TAG, "doInBackground: Malformed url");
        }
        catch(IOException e){
            Log.d(TAG, "doInBackground: IOException");
        }
        return null;
    }

    @Override
    protected void onPostExecute(String rawJSON){
        parseJSON(rawJSON);
    }

    private void parseJSON(String rawJSON){
        try {
            JSONObject object = new JSONObject(rawJSON);
            JSONArray sourceArray = object.getJSONArray("articles");

            ArrayList<Article> temp = new ArrayList<>();

            for (int i = 0; i < sourceArray.length(); i++) {
                JSONObject sourceObject = sourceArray.getJSONObject(i);
                String author = "";
                if (!sourceObject.isNull("author")) {
                    author = sourceObject.getString("author");
                }

                String title = "";
                if (!sourceObject.isNull("title")) {
                    title = sourceObject.getString("title");
                }

                String description = "";
                if (!sourceObject.isNull("description")) {
                    description = sourceObject.getString("description");
                }

                String url = "";
                if (!sourceObject.isNull("url")) {
                    url = sourceObject.getString("url");
                }

                String urlToImage = "";
                if (!sourceObject.isNull("urlToImage")) {
                    urlToImage = sourceObject.getString("urlToImage");
                }

                String publishedAt = "";
                if (!sourceObject.isNull("publishedAt")) {
                    publishedAt = sourceObject.getString("publishedAt");
                }

                //make an article object with the above variables
                temp.add( new Article(author, title, description, url, urlToImage, publishedAt, sourceArray.length(), i));

                //if theJSON parsing doesn't work for this object, then we were parsing wrong
                //use isCancelled to return from the ddoInBackground asyncc method
            }

            articleArrayList = temp;
            Log.d("Articles", "Complete!");
        }
        catch(JSONException e){
            Log.d(TAG, "parseJSON: JSON Not Parssed Correctly");
        }
    }
}
