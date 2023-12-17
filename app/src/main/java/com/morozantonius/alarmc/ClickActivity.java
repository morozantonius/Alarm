package com.morozantonius.alarmc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ClickActivity extends AppCompatActivity {

    private Button setAlarm;
    private TextView currentTimeTextView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);

        setAlarm = findViewById(R.id.alarmButton);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

        setAlarm.setOnClickListener(v -> showTimePickerDialog());
    }

    private void showTimePickerDialog() {
        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Choose a time for the alarm")
                .build();

        materialTimePicker.addOnPositiveButtonClickListener(view -> {
            int hourOfDay = materialTimePicker.getHour();
            int minute = materialTimePicker.getMinute();

            Calendar selectedTime = Calendar.getInstance();
            selectedTime.set(Calendar.SECOND, 0);
            selectedTime.set(Calendar.MILLISECOND, 0);
            selectedTime.set(Calendar.MINUTE, minute);
            selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);

            handleAlarmSetting(selectedTime);
        });

        materialTimePicker.show(getSupportFragmentManager(), "tag_picker");
    }

    private void handleAlarmSetting(Calendar selectedTime) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        PendingIntent alarmInfoPendingIntent = getAlarmInfoPendingIntent();

        alarmManager.set(AlarmManager.RTC_WAKEUP, selectedTime.getTimeInMillis(), alarmInfoPendingIntent);
        Toast.makeText(this, "Alarm set for " + new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(selectedTime.getTime()), Toast.LENGTH_SHORT).show();

        scheduleAlarmAction(selectedTime);
    }

    private void scheduleAlarmAction(Calendar selectedTime) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        PendingIntent alarmActionPendingIntent = getAlarmActionPendingIntent();

        // Schedule the alarm action for the selected time
        alarmManager.set(AlarmManager.RTC_WAKEUP, selectedTime.getTimeInMillis(), alarmActionPendingIntent);
    }

    private PendingIntent getAlarmInfoPendingIntent() {
        Intent alarmInfoIntent = new Intent(this, ClickActivity.class);
        alarmInfoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this, 0, alarmInfoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getAlarmActionPendingIntent() {
        Intent intent = new Intent(this, AlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
