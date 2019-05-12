package com.example.inspirationrewards;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
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
import android.view.TextureView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditActivity extends AppCompatActivity {

    private EditText editUsernameText;
    private EditText editPasswordText;
    private CheckBox editAdminCheckBox;
    private EditText editFirstnameText;
    private EditText editLastnameText;
    private TextView editDepartmentLabel;
    private EditText editDepartmentText;
    private TextView editPositionLabel;
    private EditText editPositionText;
    private TextView editStoryLabel;
    private EditText editStoryText;
    private TextView editStoryCharCount;
    private ImageView editPhoto;
    private ImageView editAddIcon;

    private File currentImageFile;
    private int CAMERA_REQ = 1;
    private int GALLERY_REQ = 2;
    private String encodedImage = "";

    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        profile = (Profile) getIntent().getSerializableExtra("profile");

        editUsernameText = findViewById(R.id.editUsernameText);
        editUsernameText.setText(profile.getUsername());


        editPasswordText = findViewById(R.id.editPasswordText);
        editPasswordText.setText(profile.getPassword());

        editAdminCheckBox = findViewById(R.id.editAdminCheckBox);
        editAdminCheckBox.setChecked(profile.isAdmin());

        editFirstnameText = findViewById(R.id.editFirstnameText);
        editFirstnameText.setText(profile.getFirstname());

        editLastnameText = findViewById(R.id.editLastnameText);
        editLastnameText.setText(profile.getLastname());

        editDepartmentLabel = findViewById(R.id.editDepartmentLabel);
        editDepartmentText = findViewById(R.id.editDepartmentText);
        editDepartmentText.setText(profile.getDepartment());

        editPositionLabel = findViewById(R.id.editPositionLabel);
        editPositionText = findViewById(R.id.editPositionText);
        editPositionText.setText(profile.getPosition());


        editStoryLabel = findViewById(R.id.editStoryLabel);
        editStoryText = findViewById(R.id.editStoryText);
        editStoryText.setText(profile.getStory());

        editStoryText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(360)});
        addTextListener();
        editStoryCharCount = findViewById(R.id.editStoryCharCount);

        editPhoto = findViewById(R.id.editPhoto);
        Bitmap bm = BitmapFactory.decodeByteArray(profile.getPhoto(), 0, profile.getPhoto().length);
        editPhoto.setImageBitmap(bm);

        editAddIcon = findViewById(R.id.editAddIcon);

        //ActionBar v7
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Profile");
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_with_logo);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent toProfile = new Intent(this, ProfileActivity.class);

        switch (item.getItemId()) {
            case (android.R.id.home):
                finish();
                break;
            case (R.id.editSave):
                //Save The data
                //NEED TO CHECK IF fields are null
                //i.e. we need a username, pass, fn, ln, "story"?, etc...


                //startActivity(toProfile);
                updateProfile();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addTextListener(){
        editStoryText.addTextChangedListener(new TextWatcher() {
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
                editStoryCharCount.setText(countText);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Don't need to impl
            }
        });
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
    // Functi
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

    public void updateProfile() {
        profile.setPassword(editPasswordText.getText().toString());
        profile.setAdmin(editAdminCheckBox.isChecked());
        profile.setFirstname(editFirstnameText.getText().toString());
        profile.setLastname(editLastnameText.getText().toString());
        profile.setDepartment(editDepartmentText.getText().toString());
        profile.setPosition(editPositionText.getText().toString());
        profile.setStory(editStoryText.getText().toString());

        if (!encodedImage.isEmpty()) {
            profile.setPhoto(Base64.decode(encodedImage, Base64.DEFAULT));
        }

        // TODO: Add profile picture option

        UpdateProfileAsyncTask updateProfileAsyncTask = new UpdateProfileAsyncTask(this, profile);
        updateProfileAsyncTask.execute();
    }

    //toIV - camera and gallery to imageview
    private void toIVCamera() {
        Uri imageRef = Uri.fromFile(currentImageFile);
        editPhoto.setImageURI(imageRef);

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
            editPhoto.setImageBitmap(selectedImage);
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
        Bitmap origBitmap = ((BitmapDrawable) editPhoto.getDrawable()).getBitmap();

        ByteArrayOutputStream bitmapAsByteArrayStream = new ByteArrayOutputStream();
        origBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bitmapAsByteArrayStream);

        String imgString = Base64.encodeToString(bitmapAsByteArrayStream.toByteArray(), Base64.DEFAULT);
        Log.d("Base64", "convertToBase64: " + imgString);

        return imgString;
    }
}
