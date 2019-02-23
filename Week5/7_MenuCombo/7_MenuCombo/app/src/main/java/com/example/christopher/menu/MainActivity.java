package com.example.christopher.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuA:
                textView.setText("You want to do A");
                return true;
            case R.id.menuB:
                textView.setText("You have chosen B");
                return true;
            case R.id.menuC:
                textView.setText("C is your selection");
                return true;
            case R.id.menuD:
                textView.setText("You picked pull-down menu D");
                return true;
            case R.id.menuE:
                textView.setText("Pull-down menu E was selected");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
