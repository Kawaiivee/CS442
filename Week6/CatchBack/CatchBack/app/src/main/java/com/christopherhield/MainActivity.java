package com.christopherhield;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int MAKE_DATA_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doNextActivity(View v) {
        Intent intent = new Intent(MainActivity.this, MakeDataActivity.class);
        startActivityForResult(intent, MAKE_DATA_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAKE_DATA_CODE) {
            if (resultCode == RESULT_OK) {
                DataHolder dh = (DataHolder) data.getSerializableExtra(MakeDataActivity.extraName);

                ((TextView) findViewById(R.id.textViewD1)).setText(dh.getData1());
                ((TextView) findViewById(R.id.textViewD2)).setText(dh.getData2());
                ((TextView) findViewById(R.id.textViewD3)).setText(dh.getData3());
            }
        }
    }
}
