package com.christopherhield.countdowntimer;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    private static final String TAG = "VideoActivity";
    private VideoView videoView;
    private Uri uri;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setupFullScreen();

        videoView = findViewById(R.id.videoView);
        videoView.setZ(-1);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                videoView.start();
                Log.d(TAG, "onPrepared: BUF %: " + videoView.getBufferPercentage());

            }
        });

        startVideo(getString(R.string.video_url));

        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                videoView.stopPlayback();

                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                return false;
            }
        });


    }

    public void startVideo(String s) {
        //uri = Uri.parse(s);
        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.reduced_video); //do not add any extension
        uri = Uri.parse("http://christopherhield.com/video/video.mp4");
        videoView.setVideoURI(uri);
    }

    private void setupFullScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
