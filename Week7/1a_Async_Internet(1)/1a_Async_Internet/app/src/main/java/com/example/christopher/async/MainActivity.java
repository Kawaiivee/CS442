package com.example.christopher.async;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private boolean fahrenheit = true;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        if (!sharedPref.contains("FAHRENHEIT")) {
            editor.putBoolean("FAHRENHEIT", true);
            editor.apply();
        }

        fahrenheit = sharedPref.getBoolean("FAHRENHEIT", true);
        if (fahrenheit)
            ((RadioButton) findViewById(R.id.fDegrees)).setChecked(true);
        else
            ((RadioButton) findViewById(R.id.cDegrees)).setChecked(true);


        doAsyncLoad();

    }


    public void doNewTemp(View v) {
        doAsyncLoad();
    }

    private void doAsyncLoad() {
        EditText et = (EditText) findViewById(R.id.city);
        String cityName = et.getText().toString().trim().replaceAll(", ", ",");

        AsyncLoaderTask alt = new AsyncLoaderTask(this);
        alt.execute(cityName,  Boolean.toString(fahrenheit));
    }


    public void tempSet(View v) {
        switch (v.getId()) {
            case R.id.fDegrees:
                fahrenheit = true;
                editor.putBoolean("FAHRENHEIT", true);
                break;
            case R.id.cDegrees:
                fahrenheit = false;
                editor.putBoolean("FAHRENHEIT", false);
                break;
        }
        editor.apply();
        doAsyncLoad();
    }

    public void updateData(HashMap<String, String> wData, Bitmap bitmap) {
        if (wData.isEmpty()) {
            Toast.makeText(this, "Please Enter a Valid City Name", Toast.LENGTH_SHORT).show();
            return;
        }
        TextView city = (TextView) findViewById(R.id.city);
        city.setText(wData.get("CITY") + ", " + wData.get("COUNTRY"));

        TextView temp = (TextView) findViewById(R.id.temp);
        temp.setText(String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(wData.get("TEMP"))));

        TextView condition = (TextView) findViewById(R.id.condition);
        condition.setText(wData.get("COND"));

        TextView description = (TextView) findViewById(R.id.description);
        description.setText("(" + wData.get("DESC") + ")");

        TextView date = (TextView) findViewById(R.id.date);
        date.setText(wData.get("DATE") + " (" + wData.get("TEMP") + "°)");

        TextView wind = (TextView) findViewById(R.id.wind);
        wind.setText(String.format("Wind: %.0f " + (fahrenheit ? "mph" : "mps"), Double.parseDouble(wData.get("WIND"))));

        TextView humid = (TextView) findViewById(R.id.humidity);
        humid.setText(String.format("Humidity: %.0f%%", Double.parseDouble(wData.get("HUMID"))));

        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageBitmap(bitmap);
    }


}
