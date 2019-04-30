package com.christopherhield.geography;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<Country> countryList = new ArrayList<>();
    private HashMap<String, ArrayList<Country>> countryData = new HashMap<>();
    private Menu opt_menu;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.drawer_list);


        // Set up the drawer item click callback method
        mDrawerList.setOnItemClickListener(
                new ListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Country c = countryList.get(position);
                        Intent intent = new Intent(MainActivity.this, CountryDetailActivity.class);
                        intent.putExtra(Country.class.getName(), c);
                        startActivity(intent);
                        mDrawerLayout.closeDrawer(mDrawerList);
                    }
                }
        );

        // Create the drawer toggle
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        );


        // Load the data
        if (countryData.isEmpty())
            new AsyncCountryLoader(this).execute();


    }


    public void updateData(ArrayList<Country> listIn) {

        for (Country c : listIn) {
            if (c.getSubRegion().isEmpty()) {
                c.setSubRegion("Unspecified");
            }
            if (!countryData.containsKey(c.getSubRegion())) {
                countryData.put(c.getSubRegion(), new ArrayList<Country>());
            }
            countryData.get(c.getSubRegion()).add(c);
        }

        countryData.put("All", listIn);

        ArrayList<String> tempList = new ArrayList<>(countryData.keySet());
        Collections.sort(tempList);
        for (String s : tempList)
            opt_menu.add(s);


        countryList.addAll(listIn);
        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_item, countryList));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }


    // You need the 2 below to make the drawer-toggle work properly:

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



    // You need the below to open the drawer when the toggle is clicked
    // Same method is called when an options menu item is selected.

    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            Log.d(TAG, "onOptionsItemSelected: mDrawerToggle " + item);
            return true;
        }

        setTitle(item.getTitle());

        countryList.clear();
        countryList.addAll(countryData.get(item.getTitle()));

        ((ArrayAdapter) mDrawerList.getAdapter()).notifyDataSetChanged();
        return super.onOptionsItemSelected(item);

    }

    // You need this to set up the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu, menu);
        opt_menu = menu;
        return true;
    }


}
