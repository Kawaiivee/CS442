package com.christopherhield;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MakeDataActivity extends AppCompatActivity {

    public static final String extraName = "DATA HOLDER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_data);
    }

    public void doReturnData(View v) {
        String d1 = ((EditText) findViewById(R.id.editText1)).getText().toString();
        String d2 = ((EditText) findViewById(R.id.editText2)).getText().toString();
        String d3 = ((EditText) findViewById(R.id.editText3)).getText().toString();

        DataHolder dh = new DataHolder(d1, d2, d3);

        Intent data = new Intent(); // Used to hold results data to be returned to original activity
        data.putExtra(extraName, dh); // Better be Serializable!
        setResult(RESULT_OK, data);
        finish(); // This closes the current activity, returning us to the original activity

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save data");
        builder.setMessage("Do you want to save this data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                doReturnData(null);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
