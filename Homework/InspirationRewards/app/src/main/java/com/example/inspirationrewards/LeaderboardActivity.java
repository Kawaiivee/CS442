package com.example.inspirationrewards;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener{

    //Objects for recycler view
    private RecyclerView recyclerView;
    private ProfileAdapter profileAdapter;
    private int profileSelected = 0;
    private final List<Profile> profileList = new ArrayList<>();

    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        //Recyclerview setup
        recyclerView = findViewById(R.id.leaderboardRecyclerView);
        profileAdapter = new ProfileAdapter(profileList, this);

        recyclerView.setAdapter(profileAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Used to check if the current user is in the list
        profile = (Profile) getIntent().getSerializableExtra("profile");

        //ActionBar v7 Shit
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Inspiration Leaderboard");
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_with_logo);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        GetAllProfilesAsyncTask getAllProfilesAsyncTask = new GetAllProfilesAsyncTask(this, profile);
        getAllProfilesAsyncTask.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_leaderboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case(android.R.id.home):
                //I think I have to do this via the activity stack.....but idk,
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        Toast.makeText(this, "onClick Overriden!", Toast.LENGTH_SHORT).show();
        //Need to setup a profile activity with the attributes of someone in the list...
        //could do this in the onCreate of the profile activity

        Intent toAward = new Intent(this, AwardActivity.class);
        startActivity(toAward);
    }

    @Override
    public boolean onLongClick(View v){
        Toast.makeText(this, "onLongClick Overriden!", Toast.LENGTH_SHORT).show();
        //Need to setup a profile activity with the attributes of someone in the list...
        //could do this in the onCreate of the profile activity

        Intent toAward = new Intent(this, AwardActivity.class);
        startActivity(toAward);
        return true;
    }

    public void addProfile(Profile currProfile) {
        profileList.add(currProfile);

        if (currProfile.getUsername().equals(profile.getUsername())) {
            profileList.remove(currProfile); //Gets rid of duplicate current user profile
        }
        // Sort list based on total amount of points awarded
        Collections.sort(profileList, new Comparator<Profile>() {
            @Override
            public int compare(Profile p1, Profile p2) {
                // P1 rewards
                List<Reward> p1Rewards = p1.getRewardList();
                int p1Total = 0;
                for (int i = 0; i < p1Rewards.size(); i++) {
                    Reward currReward = p1Rewards.get(i);
                    p1Total += currReward.getAmount();
                }
                // P2 rewards
                List<Reward> p2Rewards = p2.getRewardList();
                int p2Total = 0;
                for (int i = 0; i < p2Rewards.size(); i++) {
                    Reward currReward = p2Rewards.get(i);
                    p2Total += currReward.getAmount();
                }
                if (p1Total > p2Total) {
                    return -1;
                }
                else if (p1Total < p2Total) {
                    return 1;
                }
                return 0;
            }
        });
        profileAdapter.notifyDataSetChanged();
    }

}
