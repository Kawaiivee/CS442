package com.example.christopher.recycler;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {

    // The above lines are important - them make this class a listener
    // for click and long click events in the ViewHolders (in the recycler

    private static final String TAG = "MainActivity";
    private List<Country> countryList = new ArrayList<>();  // Main content is here

    private RecyclerView recyclerView; // Layout's recyclerview

    private CountryAdapter mAdapter; // Data to recyclerview adapter

    private static final int ADD_CODE = 1;
    private static final int UPDATE_CODE = 2;

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        mAdapter = new CountryAdapter(countryList, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHandler = new DatabaseHandler(this);
    }

    @Override
    protected void onResume() {
        databaseHandler.dumpDbToLog();
        ArrayList<Country> list = databaseHandler.loadCountries();
        countryList.clear();
        countryList.addAll(list);
        Log.d(TAG, "onResume: " + list);
        mAdapter.notifyDataSetChanged();

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        databaseHandler.shutDown();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks

        int pos = recyclerView.getChildLayoutPosition(v);
        Country c = countryList.get(pos);
        Intent intent = new Intent(this, CountryDetail.class);
        intent.putExtra("COUNTRY", c);
        startActivityForResult(intent, UPDATE_CODE);
    }

    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
        final int pos = recyclerView.getChildLayoutPosition(v);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                databaseHandler.deleteCountry(countryList.get(pos).getName());
                countryList.remove(pos);
                mAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.setMessage("Delete Country " + countryList.get(pos).getName() + "?");
        builder.setTitle("Delete Country");

        AlertDialog dialog = builder.create();
        dialog.show();


        return true;
    }

    public void doAdd(View v) {
        Intent intent = new Intent(this, CountryDetail.class);
        startActivityForResult(intent, ADD_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: ");

        // Unknown request code problem
        if (requestCode != ADD_CODE && requestCode != UPDATE_CODE) {
            Log.d(TAG, "onActivityResult: Unknown Request Code: " + requestCode);
            return;
        }

        // Bad result problem
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "Error adding/updating new Country", Toast.LENGTH_SHORT).show();
        }

        // Get the country
        Country country = null;
        if (data.hasExtra("COUNTRY")) {
            country = (Country) data.getSerializableExtra("COUNTRY");
        }

        // Make db request
        switch (requestCode) {
            case ADD_CODE:
                databaseHandler.addCountry(country);
                break;
            case UPDATE_CODE:
                databaseHandler.updateCountry(country);
                break;
        }


    }
}
