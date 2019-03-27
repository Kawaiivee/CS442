package com.christopherhield.imagedownload;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
    }

    public void loadImage(View v) {
        switch (v.getId()) {
            case R.id.button1:
                loadRemoteImage(getString(R.string.image1));
                break;
            case R.id.button2:
                loadRemoteImage(getString(R.string.image2));
                break;
            case R.id.button3:
                loadRemoteImage(getString(R.string.image3));
                break;
        }
    }

    private void loadRemoteImage(final String imageURL) {
        //     implementation 'com.squareup.picasso:picasso:2.71828'

        Log.d(TAG, "loadImage: " + imageURL);

        Picasso picasso = new Picasso.Builder(this).build();
        picasso.load(imageURL)
                .error(R.drawable.image_not_found)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
    }

}
