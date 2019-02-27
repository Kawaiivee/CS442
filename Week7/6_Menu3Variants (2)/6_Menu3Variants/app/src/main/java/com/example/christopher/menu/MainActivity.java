package com.example.christopher.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        registerForContextMenu(textView); // This connects the textview to the context menu

    }

    // This method builds the options (app-bar) menu (upper-right)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    // This is called when an options (app-bar) menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.optionItemA:
                Toast.makeText(this, "You want to do A", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.optionItemB:
                Toast.makeText(this, "You have chosen B", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.optionItemC:
                Toast.makeText(this, "C is your selection", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // This method builds the context menu (long-press on textview)
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    // This is called when a Context menu item is selected
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contextMenuUpper:
                textView.setText(textView.getText().toString().toUpperCase());
                return true;
            case R.id.contextMenuLower:
                textView.setText(textView.getText().toString().toLowerCase());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    // This is called when the button is pressed - it opens a pop-up menu
    public void showPopup(View v) {

        PopupMenu popup = new PopupMenu(this, v);

        // This makes THIS class be the menu listener (onMenuItemClick)
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popUpItem1:
                        Toast.makeText(MainActivity.this, "You want to do 1", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.popUpItem2:
                        Toast.makeText(MainActivity.this, "You have chosen 2", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.popUpItem3:
                        Toast.makeText(MainActivity.this, "3 is your selection", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        popup.show();
    }

}
