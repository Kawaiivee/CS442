package com.example.christopher.async;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button timeButton;
    private ProgressBar progressBar;
    private TextView textView;
    private TextView asyncStatusText;
    private boolean running = false;

    public static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.secondsEditText);
        timeButton = findViewById(R.id.button2);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.startEndText);
        asyncStatusText = findViewById(R.id.asyncStatus);

    }

    public void doAsync(View v) {
        if (running) {
            Toast.makeText(this, "Wait for Async Task to complete", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please provide a seconds-to-delay value", Toast.LENGTH_SHORT).show();
            return;
        }

        running = true;
        asyncStatusText.setText("AsyncTask Running: " + running);
        long delay = Long.parseLong(editText.getText().toString());

        progressBar.setMax((int) delay);
        progressBar.setProgress(0);

        textView.setText(MainActivity.formatter.format(new Date()) + " START");

        new MyAsyncTask(this).execute(delay);
    }

    public void updateTime(View v) {
        // onClick for the activity button
        timeButton.setText(formatter.format(new Date()));
    }

    public void updateProgressBar(int value) {
        progressBar.setProgress(value);
    }

    public void whenAsyncIsDone(String string) {
        running = false;
        asyncStatusText.setText("AsyncTask Running: " + running);
        textView.setText(textView.getText().toString() + "\n" + string.toString() + " END");
        Toast.makeText(this, "AsyncTask Complete", Toast.LENGTH_SHORT).show();

    }

}
