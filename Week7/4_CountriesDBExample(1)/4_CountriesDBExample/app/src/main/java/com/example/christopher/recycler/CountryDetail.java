package com.example.christopher.recycler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class CountryDetail extends AppCompatActivity {

    private EditText country;
    private EditText region;
    private EditText subRegion;
    private EditText capital;
    private EditText population;
    private boolean isAdd = true;
    private DatabaseHandler databaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        country = findViewById(R.id.country);
        region = findViewById(R.id.region);
        subRegion = findViewById(R.id.subRegion);
        capital = findViewById(R.id.capital);
        population = findViewById(R.id.population);

        // Check to see if a Country object was provided in the activity's intent
        // Set up the textviews if so.
        Intent intent = getIntent();
        if (intent.hasExtra("COUNTRY")) {
            Country currentCountry = (Country) intent.getSerializableExtra("COUNTRY");
            country.setText(currentCountry.getName());
            country.setFocusable(false);
            region.setText(currentCountry.getRegion());
            subRegion.setText(currentCountry.getSubRegion());
            capital.setText(currentCountry.getCapital());
            population.setText(String.format("%d", currentCountry.getPopulation()));
            isAdd = false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void doSave(View v) {

        String countryData = country.getText().toString();
        String regionData = region.getText().toString();
        String subRegionData = subRegion.getText().toString();
        String capitalData = capital.getText().toString();
        int populationData = Integer.parseInt(population.getText().toString().trim());

        Country country = new Country(countryData, capitalData, populationData, regionData, subRegionData);

        Intent data = new Intent();
        data.putExtra("COUNTRY", country);
        setResult(RESULT_OK, data);
        super.onBackPressed();
    }
}
