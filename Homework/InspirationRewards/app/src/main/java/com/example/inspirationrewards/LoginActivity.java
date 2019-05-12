package com.example.inspirationrewards;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class LoginActivity extends AppCompatActivity {

    public ProgressBar progressBar;
    private EditText usernameView;
    private EditText passwordView;
    private TextView createAccountView;
    private CheckBox rememberCheckBox;
    private Location location;
    private LocationManager locationManager;
    private Criteria criteria;
    private static int locationRequestCode = 1;
    private SharedPreferences userPrefs;
    private SharedPreferences.Editor editor;

    public String state = "";
    public String city = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Username and Password View
        usernameView = findViewById(R.id.loginUsernameText);
        passwordView = findViewById(R.id.loginPasswordText);

        //Preferences for username and password
        userPrefs = getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
        editor = userPrefs.edit();
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

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Criteria for location
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, locationRequestCode);
        }
        else {
            fetchLocation();
        }

        // Initialize preferences with the SharedPreferences of Main Activity
        userPrefs = this.getSharedPreferences("REMEMBER_CREDENTIALS", Context.MODE_PRIVATE);
        editor = userPrefs.edit();

        // Initialize userName and password. These values will only exist if the user checked the remember my credentials
        if (userPrefs.contains("username") && userPrefs.contains("password")) {
            // Set editText to the username and mark the checkbox
            String savedUserName = userPrefs.getString("username", "");
            usernameView.setText(savedUserName);

            // Set editText to the password and mark the checkbox
            String savedPassword = userPrefs.getString("password", "");
            passwordView.setText(savedPassword);

            // Set the checkbox to true
            rememberCheckBox.setChecked(true);
        }
    }

    public void login(View v) {
        if (!usernameView.getText().toString().isEmpty() && !passwordView.getText().toString().isEmpty()) {
            // Set visibility for progress bar
            progressBar.setVisibility(View.VISIBLE);


            LoginAsyncTask loginAsyncTask = new LoginAsyncTask(this, usernameView.getText().toString(), passwordView.getText().toString(), city, state);
            loginAsyncTask.execute();
        }
        else {
            Toast.makeText(this, "Please enter your username or password", Toast.LENGTH_SHORT).show();
        }
    }

    public void toProfile(Profile p){
        progressBar.setVisibility(View.GONE);

        Intent goToProfileIntent = new Intent(this, ProfileActivity.class);
        goToProfileIntent.putExtra("profile", p);
        startActivity(goToProfileIntent);
    }

    public void createAccount(View v){
        Intent toCreate = new Intent(this, CreateActivity.class);
        toCreate.putExtra("State", state);
        toCreate.putExtra("City", city);
        startActivity(toCreate);
    }

    @SuppressLint("MissingPermission")
    private void fetchLocation() {
        String bestProvider = locationManager.getBestProvider(criteria, true);
        location = locationManager.getLastKnownLocation(bestProvider);
        //latitude and longitude
        if (location != null) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            try {

                //address list returned by the geocoder
                List<Address> address = geocoder.getFromLocation(latitude, longitude, 1);
                //Save the city and state part into the city and state vars of the user when they log in
                state = address.get(0).getAdminArea();
                city = address.get(0).getLocality();

                Log.d("Location", "city: " + city);
                Log.d("Location", "state: " + state);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            fetchLocation();
        }
    }
}