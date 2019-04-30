package com.christopherhield.firstfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the introductory fragment
        Fragment fragment = new IntroFragment();

        // Get the FragmentManager so we can update the fragment on the screen
        FragmentManager fm = getSupportFragmentManager();

        // Replace the fragment held by "contentFragment" with our
        // new Intro Fragment and display it
        FragmentTransaction transaction = fm.beginTransaction(); // Start fragment transaction
        transaction.replace(R.id.contentFragment, fragment); // Indicate the new fragment to display
        transaction.commit(); // Complete the replace operation

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opt_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Get the FragmentManager so we can update the fragment on the screen
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction(); // Start fragment transaction
        Fragment fragment;

        switch (item.getItemId()) {
            case R.id.fragment1: // Selected fragment 1
                fragment = new FirstFragment(); // Create FirstFragment
                transaction.replace(R.id.contentFragment, fragment); //Container -> R.id.contentFragment
                break;
            case R.id.fragment2: // Selected fragment 2
                fragment = new SecondFragment(); // Create SecondFragment
                transaction.replace(R.id.contentFragment, fragment); //Container -> R.id.contentFragment
                break;
            case R.id.fragment3: // Selected fragment 3
                fragment = new ThirdFragment(); // Create ThirdFragment
                transaction.replace(R.id.contentFragment, fragment); //Container -> R.id.contentFragment
                break;
            case R.id.fragment4: // Selected fragment 4
                fragment = new FourthFragment(); // Create FourthFragment
                transaction.replace(R.id.contentFragment, fragment); //Container -> R.id.contentFragment
                break;
        }
        transaction.commit(); // Complete the replace operation
        return true;
    }
}