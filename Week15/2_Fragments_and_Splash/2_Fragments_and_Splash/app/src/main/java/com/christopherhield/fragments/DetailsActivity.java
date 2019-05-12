package com.christopherhield.fragments;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;

public class DetailsActivity extends Activity {

    // This is used when not in portrait mode - activity, not fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the device is in landscape mode
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we don't need this activity.
            finish();
            return;
        }

        // Check if we have any prev. data saved in the Bundle passed in
        if (savedInstanceState == null) {

            // If not then create the DetailsFragment

            DetailsFragment details = new DetailsFragment();

            // Get the Bundle of key value pairs from the intent
            Bundle fromIntent = getIntent().getExtras();

            // Use the Bundle from the intent as the arguments for the new Fragment
            details.setArguments(fromIntent);

            // Add the details Fragment
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(android.R.id.content, details);
            ft.commit();
        }
    }
}