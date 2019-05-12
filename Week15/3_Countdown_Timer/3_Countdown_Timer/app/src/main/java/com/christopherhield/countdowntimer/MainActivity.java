package com.christopherhield.countdowntimer;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private CountDownTimer countDownTimer;
    private TextView timeoutText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeoutText = findViewById(R.id.timeoutText);
        doTime(null);
        startCountdown();
    }

    @Override
    protected void onRestart() {
        startCountdown();
        super.onRestart();
    }

    private void startCountdown() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.start();
            return;
        }

        countDownTimer = new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long l) {
                Log.d(TAG, "onTick: seconds remaining: " + l / 1000);
                timeoutText.setText(
                        String.format(Locale.getDefault(), "%s%d",
                                getString(R.string.timeText), l / 1000));
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: COUNTDOWN EXPIRED");
                Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        };
        countDownTimer.start();
    }

    public void layoutClick(View v) {
        startCountdown();
    }

    public void doTime(View v) {
        ((TextView) findViewById(R.id.timeText)).setText(new Date().toString());
        startCountdown();
    }

}
