package com.example.inspirationrewards;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

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
    private boolean isAdmin = false;
    private int CAMERA_REQ = 1;
    private int GALLERY_REQ = 0;
    private File currentImageFile;
    String encodedImage = "";

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

        //For saving images to gallery
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //admincheckbox listener
        createAdminCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAdmin = isChecked;
            }
        });

        //ActionBar v7
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
                CreateProfileAsyncTask createProfileAsyncTask = new CreateProfileAsyncTask(
                        createUsernameText.getText().toString(),
                        createPasswordText.getText().toString(),
                        createFirstnameText.getText().toString(),
                        createLastnameText.getText().toString(),
                        createDepartmentText.getText().toString(),
                        createPositionText.getText().toString(),
                        createStoryText.getText().toString(),
                        isAdmin,
                        "Chicago, Illinois",
                        encodedImage,
                        this
                );
                createProfileAsyncTask.execute();
                //ALERT DIALOG HERE
                Toast.makeText(this, "User Successfully Created", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    // add image (onclick for + icon)
    public void addImage(View v) {
        //Alert Dialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Profile Picture");
        builder.setMessage("Take picture from:");
        builder.setIcon(R.drawable.logo);
        //User uses camera
        builder.setNegativeButton("CAMERA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startCamera();
            }
        });

        //User goes to gallery
        builder.setPositiveButton("GALLERY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toGallery();
            }
        });

        //User hits cancel
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    // that will go to the device's gallery
    public void toGallery() {
        Intent pictureFromGalleryIntent = new Intent(Intent.ACTION_PICK);
        pictureFromGalleryIntent.setType("image/*");
        startActivityForResult(pictureFromGalleryIntent, GALLERY_REQ);
    }

    // Function that will go to the device's camera
    public void startCamera() {
        currentImageFile = new File(getExternalCacheDir(), "profileimage" + System.currentTimeMillis() + ".jpg");
        Intent pictureFromCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        pictureFromCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFile));
        startActivityForResult(pictureFromCameraIntent, CAMERA_REQ);
    }

    //toIV - camera and gallery to imageview
    private void toIVCamera() {
        Uri imageRef = Uri.fromFile(currentImageFile);
        createPhoto.setImageURI(imageRef);

        currentImageFile.delete();
    }

    private void toIVGallery(Intent data) {
        Uri imageRef = data.getData();

        if (imageRef != null) {
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(imageRef);
            } catch (FileNotFoundException e) {
                //Log.d(TAG, "toIVGallery: ");
            }

            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            createPhoto.setImageBitmap(selectedImage);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // User takes a picture
        if (requestCode == CAMERA_REQ && resultCode == RESULT_OK) {
            toIVCamera();

            // Encode image to Base64
            encodedImage = convertToBase64();
        }

        // User gets a picture from gallery
        else if (requestCode == GALLERY_REQ && resultCode == RESULT_OK) {
            toIVGallery(data);

            // Encode image to Base64
            encodedImage = convertToBase64();
        }
    }

    public String convertToBase64() {
        Bitmap origBitmap = ((BitmapDrawable) createPhoto.getDrawable()).getBitmap();

        ByteArrayOutputStream bitmapAsByteArrayStream = new ByteArrayOutputStream();
        origBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bitmapAsByteArrayStream);

        String imgString = Base64.encodeToString(bitmapAsByteArrayStream.toByteArray(), Base64.DEFAULT);
        Log.d("Base64", "convertToBase64: " + imgString);

        return imgString;
    }

    public void toProfile(Profile p) {
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("profile", p);
        startActivity(i);
    }

}
