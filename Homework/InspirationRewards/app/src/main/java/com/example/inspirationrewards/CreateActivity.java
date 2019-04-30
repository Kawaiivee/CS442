package com.example.inspirationrewards;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity {

    private EditText createUsernameText;
    private EditText createPasswordText;
    private CheckBox createAdminCheckBox;
    private EditText createFirstnameText;
    private EditText createLastnameText;
    private TextView createDepartmentLabel;
    private EditText createDepartmentText;
    private TextView createPositionLabel;
    private EditText createPositionText;
    private TextView createStoryLabel;
    private EditText createStoryText;
    private TextView createStoryCharCount;
    private ImageView createPhoto;
    private ImageView createAddIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //Initialize Views
        createUsernameText = findViewById(R.id.createUsernameText);
        createPasswordText = findViewById(R.id.createPasswordText);
        createAdminCheckBox = findViewById(R.id.createAdminCheckBox);
        createFirstnameText = findViewById(R.id.createFirstnameText);
        createLastnameText = findViewById(R.id.createLastnameText);
        createDepartmentLabel = findViewById(R.id.createDepartmentLabel);
        createDepartmentText = findViewById(R.id.createDepartmentText);
        createPositionLabel = findViewById(R.id.createPositionLabel);
        createPositionText = findViewById(R.id.createPositionText);
        createStoryLabel = findViewById(R.id.createStoryLabel);
        createStoryText = findViewById(R.id.createStoryText);
        createStoryText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(360)});
        addTextListener();
        createStoryCharCount = findViewById(R.id.createStoryCharCount);
        createPhoto = findViewById(R.id.createPhoto);
        createAddIcon = findViewById(R.id.createAddIcon);

        //ActionBar v7 Shit
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Profile");
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_with_logo);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void addTextListener(){
        createStoryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Don't need to impl
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = s.length();
                //Leaving a toast right here for debugging is pretty hilarious
                //Toast.makeText(CreateActivity.this, s, Toast.LENGTH_SHORT).show();
                String countText = "(" + len + " of 360)";
                createStoryCharCount.setText(countText);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Don't need to impl
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent toProfile = new Intent(this, ProfileActivity.class);

        switch(item.getItemId()){
            case(android.R.id.home):
                finish();
                break;
            case(R.id.createSave):
                //Save The data
                //NEED TO CHECK IF fields are null
                //i.e. we need a username, pass, fn, ln, "story"?, etc...
                startActivity(toProfile);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // If there is an internet connection, return true. else false
    //Use this for anything that attemps to use the internet
    public boolean connected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        else{
            return false;
        }
    }
}
