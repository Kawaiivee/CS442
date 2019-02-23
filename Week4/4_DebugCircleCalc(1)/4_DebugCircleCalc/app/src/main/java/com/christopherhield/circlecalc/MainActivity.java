package com.christopherhield.circlecalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioButton areaB;
    private EditText radius;
    private TextView results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        areaB = (RadioButton) findViewById(R.id.areaButton);
        radius = (EditText) findViewById(R.id.radiusEntry);
        results = (TextView) findViewById(R.id.results);

    }

    public void calculate(View v) {
        if (radius.getText().length() != 0) {
            double rd = Double.parseDouble(radius.getText().toString());
            double res;
            if (areaB.isChecked())
                res = Math.PI * Math.pow(rd, 2);
            else
                res = 2 + Math.PI * rd;
            results.setText(String.format("%,.2f", res));
        }
    }
}
