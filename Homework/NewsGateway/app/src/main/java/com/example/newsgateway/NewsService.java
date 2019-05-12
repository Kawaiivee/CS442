package com.example.newsgateway;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class NewsService extends Service {
    private String source;
    private ArrayList<Article> articleList = new ArrayList<>();
    private ServiceReceiver serviceReceiver;
    private NewsService newsService;
    private boolean running = true;

    public NewsService() {
    }

    public class ServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            ArticleAsyncTask articleAsyncTask = new ArticleAsyncTask(newsService, articleList);
            articleAsyncTask.execute(intent.getStringExtra("source"));
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //Log.d(TAG, "onStartCommand: ");
        articleList = new ArrayList<Article>();

        //start thread
        new Thread(new Runnable(){
            @Override
            public void run(){
                Log.d("Service", "run: ");
                serviceReceiver = new ServiceReceiver();
                registerReceiver(serviceReceiver, new IntentFilter(MainActivity.REQUEST));

                while(running){
                    while(articleList.size() == 0 ){
                        try{
                            Thread.sleep(250);
                            continue;
                        }
                        catch(InterruptedException e){
                            //Log.d(TAG, "run: Interrupted Exception");
                        }
                    }
                        //We're done, so put the articles we received into the intent
                        //And start broadcasting that we got a response
                        Log.d("Service", "run: fetching articles");
                        Intent response = new Intent();
                        response.setAction(MainActivity.RESPONSE);
                        response.putExtra("articleList", articleList);
                        sendBroadcast(response);
                        articleList.clear();


                }
            }
            //Actually run this thread
        }).start();
        return START_STICKY;
    }

    //Need to add omre articles to the list from other activities
    public void sendIntent(){
        Intent response = new Intent();
        response.setAction(MainActivity.RESPONSE);
        response.putExtra("articleList", articleList);
        sendBroadcast(response);
        articleList.clear();
    }
}

