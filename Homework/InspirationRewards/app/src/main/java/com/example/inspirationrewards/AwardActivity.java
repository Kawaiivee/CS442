package com.example.inspirationrewards;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

public class AwardActivity extends AppCompatActivity {

    private TextView awardFirstname;
    private TextView awardLastname;
    private ImageView awardPhoto;
    private TextView awardPointsAwardedLabel;
    private TextView awardPointsAwarded;
    private TextView awardDepartmentLabel;
    private TextView awardDepartment;
    private TextView awardPositionLabel;
    private TextView awardPosition;
    private TextView awardStoryLabel;
    private TextView awardStory;
    private TextView awardPointsToSendLabel;
    private EditText awardPointsToSendText;
    private TextView awardCommentLabel;
    private TextView awardCommentCharCount;
    private EditText awardCommentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_award);

        awardFirstname = findViewById(R.id.awardFirstname);
        awardLastname = findViewById(R.id.awardLastname);
        awardPhoto = findViewById(R.id.awardPhoto);
        awardPointsAwardedLabel = findViewById(R.id.awardPointsAwardedLabel);
        awardPointsAwarded = findViewById(R.id.awardPointsAwarded);
        awardDepartmentLabel = findViewById(R.id.awardDepartmentLabel);
        awardDepartment = findViewById(R.id.awardDepartment);
        awardPositionLabel = findViewById(R.id.awardPositionLabel);
        awardPosition = findViewById(R.id.awardPosition);
        awardStoryLabel = findViewById(R.id.awardStoryLabel);
        awardStory = findViewById(R.id.awardStory);
        awardPointsToSendLabel = findViewById(R.id.awardPointsToSendLabel);
        awardPointsToSendText = findViewById(R.id.awardPointsToSendText);
        awardCommentLabel = findViewById(R.id.awardCommentLabel);
        awardCommentCharCount = findViewById(R.id.awardCommentCharCount);
        awardCommentText = findViewById(R.id.awardCommentText);
        awardCommentText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(80)});
        addTextListener();


        //ActionBar v7 Shit - NEED TO PUT PERSON'S NAME IN TITLE
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile Name");
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_with_logo);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    //Text-Listener
    private void addTextListener(){
        awardCommentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Don't need to impl
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = s.length();
                //Leaving a toast right here for debugging is pretty hilarious
                //Toast.makeText(AwardActivity.this, s, Toast.LENGTH_SHORT).show();
                String countText = "(" + len + " of 80)";
                awardCommentCharCount.setText(countText);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Don't need to impl
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_award, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent toLeaderboard = new Intent(this, LeaderboardActivity.class);
        switch(item.getItemId()){
            case(android.R.id.home):
                finish();
                break;
            case(R.id.awardSave):
                //To do things in submitting a reward, comment, and points to someone's reward history
                Toast.makeText(this, "Sending Points!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // If there is an internet connection, return true. else false
    //Use this for anything that attemps to use the internet
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
