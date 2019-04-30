package com.christopherhield.broadcastandreceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    static final String FRUIT_BROADCAST_FROM_SERVICE = "FRUIT_BROADCAST_FROM_SERVICE";
    static final String MESSAGE_BROADCAST_FROM_SERVICE = "MESSAGE_BROADCAST_FROM_SERVICE";
    static final String FRUIT_DATA = "FRUIT_DATA";
    static final String COUNT_DATA = "COUNT_DATA";
    static final String MESSAGE_DATA = "MESSAGE_DATA";

    private SampleReceiver sampleReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///
        ArrayList<Fruit> fruitList = new ArrayList<>();
        fruitList.add(new Fruit("Apple", "Red", 0.89));
        fruitList.add(new Fruit("Pear", "Green", 1.10));
        fruitList.add(new Fruit("Banana", "Yellow", 0.99));
        fruitList.add(new Fruit("Orange", "Orange", 1.15));
        fruitList.add(new Fruit("Eggplant", "Purple", 1.79));
        ///

        Intent intent = new Intent(MainActivity.this, FruitService.class);
        intent.putExtra("FRUIT_LIST", fruitList);
        startService(intent);

        sampleReceiver = new SampleReceiver();

        IntentFilter filter1 = new IntentFilter(FRUIT_BROADCAST_FROM_SERVICE);
        registerReceiver(sampleReceiver, filter1);

        IntentFilter filter2 = new IntentFilter(MESSAGE_BROADCAST_FROM_SERVICE);
        registerReceiver(sampleReceiver, filter2);
    }

    public void stopService(View v) {
        Intent intent = new Intent(MainActivity.this, FruitService.class);
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(sampleReceiver);
        Intent intent = new Intent(MainActivity.this, FruitService.class);
        stopService(intent);
        super.onDestroy();
    }

    /////////////////////////////////////////////////////////////
    class SampleReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action == null)
                return;
            switch (action) {
                case FRUIT_BROADCAST_FROM_SERVICE:
                    Fruit newFruit = null;
                    int count = 0;

                    if (intent.hasExtra(FRUIT_DATA))
                        newFruit = (Fruit) intent.getSerializableExtra(FRUIT_DATA);

                    if (intent.hasExtra(COUNT_DATA))
                        count = intent.getIntExtra(COUNT_DATA, 0);

                    ((TextView) findViewById(R.id.textView)).setText(
                            String.format("%d)  %s", count, newFruit.toString()));

                    break;
                case MESSAGE_BROADCAST_FROM_SERVICE:
                    String data = "";
                    if (intent.hasExtra(MESSAGE_DATA))
                        data = intent.getStringExtra(MESSAGE_DATA);
                    ((TextView) findViewById(R.id.textView)).setText(data);
                    break;
                default:
                    Log.d(TAG, "onReceive: Unknown broadcast received");
            }
        }
    }
}
