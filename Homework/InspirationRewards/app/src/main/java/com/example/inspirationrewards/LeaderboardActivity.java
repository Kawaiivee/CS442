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
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener{

    //Objects for recycler view
    private RecyclerView recyclerView;
    private ProfileAdapter profileAdapter;
    private int profileSelected = 0;
    private final List<Profile> profileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        //Recyclerview setup
        recyclerView = findViewById(R.id.leaderboardRecyclerView);
        profileAdapter = new ProfileAdapter(profileList, this);

        recyclerView.setAdapter(profileAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //ActionBar v7 Shit
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Inspiration Leaderboard");
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_with_logo);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        for(int i = 0; i < 20; i++){
            profileList.add(new Profile("Username", "Password", "FN", "LN", "DPT", "POS", "This is my story", true, 100, null));
        }
        profileAdapter.notifyDataSetChanged();
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
