package com.example.christopher.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

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
                Toast.makeText(this, "You want to do A", Toast.LENGTH_SHORT).show();
                textView.setText("A is Active");
                return true;
            case R.id.menuB:
                Toast.makeText(this, "You have chosen B", Toast.LENGTH_SHORT).show();
                textView.setText("B is Active");
                return true;
            case R.id.menuC:
                Toast.makeText(this, "C is your selection", Toast.LENGTH_SHORT).show();
                textView.setText("C is Active");
                return true;
            case R.id.menuD:
                Toast.makeText(this, "D is your selection", Toast.LENGTH_SHORT).show();
                textView.setText("D is Active");
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
