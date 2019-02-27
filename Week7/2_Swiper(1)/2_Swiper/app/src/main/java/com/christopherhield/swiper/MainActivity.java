package com.christopherhield.swiper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.support.v4.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private SwipeRefreshLayout swiper;
    private int ctr = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        swiper = findViewById(R.id.swiper);

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("Swipe Refresh Count: " + ctr++);
                swiper.setRefreshing(false); // This stops the busy-circle
            }
        });
    }
}
