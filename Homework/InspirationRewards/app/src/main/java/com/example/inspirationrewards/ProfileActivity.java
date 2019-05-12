package com.example.inspirationrewards;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileLastname;
    private TextView profileFirstname;
    private TextView profileUsername;
    private TextView profileLocation;
    private ImageView profilePhoto;
    private TextView profilePointsAwardedLabel;
    private TextView profilePointsAwarded;
    private TextView profileDepartmentLabel;
    private TextView profileDepartment;
    private TextView profilePositionLabel;
    private TextView profilePosition;
    private TextView profilePointsToAwardLabel;
    private TextView profilePointsToAward;
    private TextView profileStoryLabel;
    private TextView profileStory;
    private TextView profileRewardHistoryLabel;

    private static int MY_LOCATION_REQUEST_CODE = 329;
    private Location currentLocation;
    private LocationManager locationManager;
    private Criteria criteria;

    Profile profile;

    private static int EDIT_REQ = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile = (Profile) getIntent().getSerializableExtra("profile");

        profileLastname = findViewById(R.id.profileLastname);
        profileLastname.setText(profile.getLastname());

        profileFirstname = findViewById(R.id.profileFirstname);
        profileFirstname.setText(profile.getFirstname());

        profileUsername = findViewById(R.id.profileUsername);
        profileUsername.setText(profile.getUsername());

        profileLocation = findViewById(R.id.profileLocation);
        profileLocation.setText(profile.getLocation());

        profilePhoto = findViewById(R.id.profilePhoto);
        Bitmap bm = BitmapFactory.decodeByteArray(profile.getPhoto(), 0, profile.getPhoto().length);
        profilePhoto.setImageBitmap(bm);

        profilePointsAwardedLabel = findViewById(R.id.profilePointsAwardedLabel);
        profilePointsAwarded = findViewById(R.id.profilePointsAwarded);

        profileDepartmentLabel = findViewById(R.id.profileDepartmentLabel);
        profileDepartment = findViewById(R.id.profileDepartment);
        profileDepartment.setText(profile.getDepartment());

        profilePositionLabel = findViewById(R.id.profilePositionLabel);
        profilePosition = findViewById(R.id.profilePosition);
        profilePosition.setText(profile.getPosition());

        profilePointsToAwardLabel = findViewById(R.id.profilePointsToAwardLabel);
        profilePointsToAward = findViewById(R.id.profilePointsToAward);
        profilePointsToAward.setText("" + profile.getPoints());

        profileStoryLabel = findViewById(R.id.profileStoryLabel);
        profileStory = findViewById(R.id.profileStory);
        profileStory.setText(profile.getStory());

        profileRewardHistoryLabel = findViewById(R.id.profileRewardHistoryLabel);

        //LOCATION Setup
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    MY_LOCATION_REQUEST_CODE);
        }
        else{
            setLocation();
        }

        //ActionBar v7 Shit
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Your Profile");
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_with_logo);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    //LOCATION METHODS
    private void setLocation() {
        String bestProvider = locationManager.getBestProvider(criteria, true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent toLeaderboard = new Intent(this, LeaderboardActivity.class);
        Intent toEdit = new Intent(this, EditActivity.class);
        Intent toLogin = new Intent(this, LoginActivity.class);
        switch(item.getItemId()){
            case(android.R.id.home):
                //I think I have to do this via the activity stack.....but idk,
                startActivity(toLogin);
                break;
            case(R.id.profileEdit):
                //Save The data
                //NEED TO CHECK IF fields are null
                //i.e. we need a username, pass, fn, ln, "story"?, etc...
                toEdit.putExtra("profile", profile);
                startActivityForResult(toEdit, EDIT_REQ);
                break;
            case(R.id.profileLeaderboard):
                //same as said above
                toLeaderboard.putExtra("profile", profile);
                startActivity(toLeaderboard);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == EDIT_REQ) {
            if (resultCode == 1) {
                profile = (Profile) data.getSerializableExtra("profile");

                profileLastname.setText(profile.getLastname());
                profileFirstname.setText(profile.getFirstname());
                profileUsername.setText(profile.getUsername());
                profileLocation.setText(profile.getLocation());

                Bitmap bm = BitmapFactory.decodeByteArray(profile.getPhoto(), 0, profile.getPhoto().length);
                profilePhoto.setImageBitmap(bm);

                profileDepartment.setText(profile.getDepartment());
                profilePosition.setText(profile.getPosition());
                profileStory.setText(profile.getStory());

            }
        }
    }
}
