package com.morozantonius.alarmc;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivity extends AppCompatActivity {

    private static final String TAG = "AlarmActivity";
    private Ringtone ringtone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Button stopAlarmButton = findViewById(R.id.stopAlarmButton);
        if (stopAlarmButton != null) {
            stopAlarmButton.setOnClickListener(v -> stopRingtoneAndNavigate());
        }

        playRingtone();
    }

    @Override
    protected void onDestroy() {
        stopRingtone();
        super.onDestroy();
    }

    private void playRingtone() {
        try {
            Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            ringtone = RingtoneManager.getRingtone(this, notificationUri);
            if (ringtone == null) {
                notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                ringtone = RingtoneManager.getRingtone(this, notificationUri);
            }
            if (ringtone != null) {
                ringtone.play();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error playing ringtone: " + e.getMessage());
        }
    }

    private void stopRingtoneAndNavigate() {
        stopRingtone();
        navigateToClickActivity();
    }

    private void stopRingtone() {
        try {
            if (ringtone != null && ringtone.isPlaying()) {
                ringtone.stop();
            }
        } catch (IllegalStateException e) {
            Log.e(TAG, "Error stopping ringtone: " + e.getMessage());
        }
    }

    private void navigateToClickActivity() {
        startActivity(new Intent(AlarmActivity.this, ClickActivity.class));
        finish();
    }
}
