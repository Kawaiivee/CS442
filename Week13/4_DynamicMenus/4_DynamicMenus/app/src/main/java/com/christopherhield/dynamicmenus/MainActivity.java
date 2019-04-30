package com.christopherhield.dynamicmenus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int MENU_A = 100;
    private static final int MENU_B = 200;

    private static final int GROUP_A = 10;

    private TextView textView;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.menu = menu;

        int count = 3;
        for (int i = 0; i < count; i++)
            menu.add(GROUP_A, MENU_A, 0, "Item " + (i+1));

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        textView.setText(item.getTitle());

        return super.onOptionsItemSelected(item);
    }

    public void makeMenu(View v) {
        String value = ((EditText) findViewById(R.id.editText)).getText().toString();
        if (value.trim().isEmpty())
            return;
        menu.clear();

        int count = Integer.parseInt(value);
        for (int i = 0; i < count; i++)
            menu.add(GROUP_A, MENU_A, 0, "Item " + (i+1));

    }
}
