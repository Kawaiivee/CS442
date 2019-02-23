package com.christopherhield.textview_edittext_button;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText userText;
    private TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind var's to the screen widgets
        userText = findViewById(R.id.editText);
        output = findViewById(R.id.textView);
    }

    public void button1Clicked(View v) {
        String text = userText.getText().toString();
        if (!text.trim().isEmpty())
            output.setText("B1: " + text);
    }

    public void button2Clicked(View v) {
        String text = userText.getText().toString();
        if (!text.trim().isEmpty())
            output.setText("B2: " + text.toUpperCase());
    }

}
