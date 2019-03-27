
package com.example.christopher.recycler;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHandler";

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    // DB Name
    private static final String DATABASE_NAME = "CountryAppDB";

    // DB Table Name
    private static final String TABLE_NAME = "CountryTable";

    ///DB Columns
    private static final String COUNTRY = "CountryName";
    private static final String REGION = "Region";
    private static final String SUBREGION = "SubRegion";
    private static final String CAPITAL = "Capital";
    private static final String POPULATION = "Population";

    // DB Table Create Code
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COUNTRY + " TEXT not null unique," +
                    REGION + " TEXT not null, " +
                    SUBREGION + " TEXT not null, " +
                    CAPITAL + " TEXT not null, " +
                    POPULATION + " INT not null)";

    private SQLiteDatabase database;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase(); // Inherited from SQLiteOpenHelper
        Log.d(TAG, "DatabaseHandler: C'tor DONE");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // onCreate is only called is the DB does not exist
        Log.d(TAG, "onCreate: Making New DB");
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<Country> loadCountries() {

        // Load countries - return ArrayList of loaded countries
        Log.d(TAG, "loadCountries: START");
        ArrayList<Country> countries = new ArrayList<>();

        Cursor cursor = database.query(
                TABLE_NAME,  // The table to query
                new String[]{COUNTRY, REGION, SUBREGION, CAPITAL, POPULATION}, // The columns to return
                null, // The columns for the WHERE clause
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null); // The sort order

        if (cursor != null) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                String country = cursor.getString(0);
                String region = cursor.getString(1);
                String subRegion = cursor.getString(2);
                String capital = cursor.getString(3);
                int population = cursor.getInt(4);
                Country c = new Country(country, capital, population, region, subRegion);
                countries.add(c);
                cursor.moveToNext();
            }
            cursor.close();
        }
        Log.d(TAG, "loadCountries: DONE");

        return countries;
    }

    public void addCountry(Country country) {
        ContentValues values = new ContentValues();

        values.put(COUNTRY, country.getName());
        values.put(REGION, country.getRegion());
        values.put(SUBREGION, country.getSubRegion());
        values.put(CAPITAL, country.getCapital());
        values.put(POPULATION, country.getPopulation());

        //deleteCountry(country.getName());

        long key = database.insert(TABLE_NAME, null, values);
        Log.d(TAG, "addCountry: " + key);
    }

    public void updateCountry(Country country) {
        ContentValues values = new ContentValues();

        values.put(COUNTRY, country.getName());
        values.put(REGION, country.getRegion());
        values.put(SUBREGION, country.getSubRegion());
        values.put(CAPITAL, country.getCapital());
        values.put(POPULATION, country.getPopulation());

        long key = database.update(TABLE_NAME, values, COUNTRY + " = ?", new String[]{country.getName()});

        Log.d(TAG, "updateCountry: " + key);
    }

    public void deleteCountry(String name) {
        Log.d(TAG, "deleteCountry: " + name);

        int cnt = database.delete(TABLE_NAME, COUNTRY + " = ?", new String[]{name});

        Log.d(TAG, "deleteCountry: " + cnt);
    }

    public void dumpDbToLog() {
        Cursor cursor = database.rawQuery("select * from " + TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();

            Log.d(TAG, "dumpDbToLog: vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
            for (int i = 0; i < cursor.getCount(); i++) {
                String country = cursor.getString(0);
                String region = cursor.getString(1);
                String subRegion = cursor.getString(2);
                String capital = cursor.getString(3);
                int population = cursor.getInt(4);
                Log.d(TAG, "dumpDbToLog: " +
                        String.format("%s %-18s", COUNTRY + ":", country) +
                        String.format("%s %-18s", REGION + ":", region) +
                        String.format("%s %-18s", SUBREGION + ":", subRegion) +
                        String.format("%s %-18s", CAPITAL + ":", capital) +
                        String.format("%s %-18s", POPULATION + ":", population));
                cursor.moveToNext();
            }
            cursor.close();
        }

        Log.d(TAG, "dumpDbToLog: ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }

    public void shutDown() {
        database.close();
    }
}
