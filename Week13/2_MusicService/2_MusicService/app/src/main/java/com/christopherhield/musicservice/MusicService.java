package com.christopherhield.musicservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;


// New ==> Service ==> Service
public class MusicService extends Service {

    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Note, mp3 files go in the "raw" resource directory.
        player = MediaPlayer.create(this, R.raw.music);
        player.setLooping(true);
        player.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
        super.onDestroy();
    }
}
