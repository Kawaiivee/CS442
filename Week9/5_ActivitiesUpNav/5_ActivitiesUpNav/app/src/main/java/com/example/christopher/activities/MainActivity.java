package com.example.christopher.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void clickA(View v) {
        Intent intent = new Intent(MainActivity.this, A_Activity.class);
        intent.putExtra(Intent.EXTRA_TEXT, MainActivity.class.getSimpleName());
        startActivity(intent);
    }

    public void clickB(View v) {
        Intent intent = new Intent(MainActivity.this, B_Activity.class);
        intent.putExtra(Intent.EXTRA_TEXT, MainActivity.class.getSimpleName());
        startActivity(intent);
    }
}
