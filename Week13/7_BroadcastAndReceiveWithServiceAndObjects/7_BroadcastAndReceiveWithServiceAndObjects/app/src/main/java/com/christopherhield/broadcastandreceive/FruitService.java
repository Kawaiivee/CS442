package com.christopherhield.broadcastandreceive;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

public class FruitService extends Service {

    private static final String TAG = "CountService";
    private boolean running = true;
    private final ArrayList<Fruit> fruitList = new ArrayList<>();
    private int count = 1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.hasExtra("FRUIT_LIST")) {
            ArrayList<Fruit> listIn = (ArrayList<Fruit>) intent.getSerializableExtra("FRUIT_LIST");
            fruitList.addAll(listIn);
        }


        //Creating new thread for my service
        //ALWAYS write your long running tasks in a separate thread, to avoid ANR

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (running) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Get random index
                    int index = (int) (Math.random() * fruitList.size());
                    sendFruit(fruitList.get(index)); // Send fruit at index
                }

                sendMessage("Service Thread Stopped");


                Log.d(TAG, "run: Ending loop");
            }
        }).start();

        
        return Service.START_NOT_STICKY;
    }

    private void sendFruit(Fruit fruitToSend) {
        Intent intent = new Intent();
        intent.setAction(MainActivity.FRUIT_BROADCAST_FROM_SERVICE);
        intent.putExtra(MainActivity.FRUIT_DATA, fruitToSend);
        intent.putExtra(MainActivity.COUNT_DATA, count++);

        sendBroadcast(intent);
    }

    private void sendMessage(String msg) {
        Intent intent = new Intent();
        intent.setAction(MainActivity.MESSAGE_BROADCAST_FROM_SERVICE);
        intent.putExtra(MainActivity.MESSAGE_DATA, msg);
        sendBroadcast(intent);
    }
    @Override
    public void onDestroy() {
        sendMessage("Service Destroyed");
        running = false;
        super.onDestroy();
    }
}
