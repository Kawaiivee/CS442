package com.christopherhield.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences myPrefs;
    private SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPrefs = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        prefsEditor = myPrefs.edit();

        String myData = myPrefs.getString("DATA", "-----");

        ((TextView) findViewById(R.id.textView)).setText(myData);


    }

    public void copy(View v) {
        String s = ((EditText) findViewById(R.id.editText)).getText().toString();
        ((TextView) findViewById(R.id.textView)).setText(s);

        prefsEditor.putString("DATA", s);
        prefsEditor.apply();
    }
}
