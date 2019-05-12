package com.christopherhield.fragments;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load json data file - creates ZooAnimal objects
        // and stores them in a ststic list in the ZooAnimal class.
        DataFileLoader.loadJSONFile(this);

        // Set the layout for fragment_layout.xml
        setContentView(R.layout.activity_fragment_layout);
    }

}