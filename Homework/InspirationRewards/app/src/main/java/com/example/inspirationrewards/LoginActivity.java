package com.example.inspirationrewards;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText usernameView;
    private EditText passwordView;
    private TextView createAccountView;
    private CheckBox rememberCheckBox;

    private SharedPreferences userPrefs;
    private SharedPreferences.Editor userPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Username and Password View
        usernameView = findViewById(R.id.loginUsernameText);
        passwordView = findViewById(R.id.loginPasswordText);

        //Preferences for username and password
        userPrefs = getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
        userPrefsEditor = userPrefs.edit();
        //String userCredentials = userPrefs.getString();

        //Set Up progress bar
        progressBar = findViewById(R.id.loginProgressBar);
        progressBar.setVisibility(View.INVISIBLE);

        //Actionbar change title

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Rewards");
        actionBar.setHomeAsUpIndicator(R.drawable.icon);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void login(View v) {
        //set visibility of progress bar, set username, set password
        progressBar.setVisibility(View.VISIBLE);
    }

    public void createAccount(View v){
        Intent toCreate = new Intent(this, CreateActivity.class);
        startActivity(toCreate);
    }

    // If there is an internet connection, return true. else false
    //Use this for anything that attempts to use the internet
    public boolean connected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        else{
            return false;
        }
    }
}