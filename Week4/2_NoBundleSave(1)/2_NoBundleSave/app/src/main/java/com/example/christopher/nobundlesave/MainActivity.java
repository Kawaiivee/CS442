package com.example.christopher.nobundlesave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: " + savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClicked(View v) {
        EditText numText = (EditText) findViewById(R.id.phoneNum);
        TextView output = (TextView) findViewById(R.id.textView);
        String newText =  numText.getText().toString();
        String text = output.getText().toString();
        output.setText(newText + "\n" + text);
        numText.setText("");

    }


}
