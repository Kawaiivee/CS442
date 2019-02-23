package com.example.kawaiivee.temperatureconverter;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.*;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private TextView output;
    private TextView history;
    private TextView inputLabel;
    private TextView outputLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.inputData);
        output = findViewById(R.id.outputData);
        history = findViewById(R.id.historyData);
        history.setMovementMethod(new ScrollingMovementMethod());
        inputLabel = findViewById(R.id.inputTempLabel);
        outputLabel = findViewById(R.id.outputTempLabel);
    }

    public static int mode = 0;
    public void radioButtonClicked(View V){
        String selectionText = ((RadioButton) V).getText().toString();
        Toast.makeText(this, selectionText, Toast.LENGTH_SHORT).show();
        if(selectionText.charAt(0)=='F'){
            mode = 0;                   //This is Fahrenheit to Celsius Mode
            inputLabel.setText("Fahrenheit: ");
            outputLabel.setText("Celsius: ");
        }
        else if(selectionText.charAt(0)=='C'){
            mode = 1;                   //This is Celsius to Fahrenheit Mode
            inputLabel.setText("Celsius: ");
            outputLabel.setText("Fahrenheit: ");
        }

    }

    public static float calculateTemp(float temp){
        float result;
        if(mode == 0){
            result = (temp-32.0f)/1.8f;       //F to C
            return result;
        }
        else{
            result = (temp * 1.8f) + 32.0f;     //C to F
            return result;
        }
    }

    static String fullHistory = "";
    public void historyTrace(View V, String s1, String s2){
        fullHistory = s1 + " ==> " + s2 + "\n" + fullHistory;
        history.setText(fullHistory);
    }

    public void convertButtonClicked(View V){
        String rawData = input.getText().toString();
        if(rawData.equals("")){
            Toast.makeText(this,"Enter a temperature value", Toast.LENGTH_SHORT).show();
        }
        else{
            float data1 = Float.parseFloat(rawData);
            float data2 = calculateTemp(data1);
            String s1 = String.format("%.1f", data1);
            String s2 = String.format("%.1f", data2);
            output.setText(s2);
            historyTrace(V, s1, s2);
        }
    }

    public void clearButtonClicked(View V){
        history.setText("");
        fullHistory = "";
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("H", history.getText().toString());
        outState.putFloat("I", Float.valueOf(input.getText().toString()));
        outState.putFloat("O", Float.valueOf(output.getText().toString()));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        history.setText(savedInstanceState.getString("H"));
        input.setText(Float.toString(savedInstanceState.getFloat("I")));
        output.setText(Float.toString(savedInstanceState.getFloat("O")));
    }
}