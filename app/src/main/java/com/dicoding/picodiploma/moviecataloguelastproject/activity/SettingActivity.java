package com.dicoding.picodiploma.moviecataloguelastproject.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.picodiploma.moviecataloguelastproject.R;
import com.dicoding.picodiploma.moviecataloguelastproject.notification.MovieDailyReminder;
import com.dicoding.picodiploma.moviecataloguelastproject.notification.MovieReleaseReminder;
import com.dicoding.picodiploma.moviecataloguelastproject.notification.preference;

public class SettingActivity extends AppCompatActivity {
    private Switch switchReminder;
    private Switch switchRelease;
    String timeDaily = "07:00";
    String timeRelease = "08:00";

    MovieDailyReminder movieDailyReminder;
    MovieReleaseReminder movieReleaseReminder;
    preference notificationPreference;
    SharedPreferences spReleaseReminder, spDailyReminder;
    SharedPreferences.Editor edtReleaseReminder, edtDailyReminder;

    String TYPE_DAILY = "reminderDaily";
    String TYPE_RELEASE = "reminderRelease";
    String DAILY_REMINDER = "dailyReminder";
    String RELEASE_REMINDER = "releaseReminder";
    String KEY_RELEASE = "Release";
    String KEY_DAILY_REMINDER = "Daily";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.setting);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        movieDailyReminder = new MovieDailyReminder();
        movieReleaseReminder = new MovieReleaseReminder();
        notificationPreference = new preference(this);

        switchReminder = findViewById(R.id.switch_daily_reminder);
        switchRelease = findViewById(R.id.switch_release_today);

        setPreference();
        setDailyReminder();
        setReleaseReminder();
    }

    private void setPreference() {
        spDailyReminder = getSharedPreferences(DAILY_REMINDER, MODE_PRIVATE);
        boolean checkDailyReminder = spDailyReminder.getBoolean(KEY_DAILY_REMINDER, false);
        switchReminder.setChecked(checkDailyReminder);
        spReleaseReminder = getSharedPreferences(RELEASE_REMINDER, MODE_PRIVATE);
        boolean checkUpcomingReminder = spReleaseReminder.getBoolean(KEY_RELEASE, false);
        switchRelease.setChecked(checkUpcomingReminder);
    }

    public void setDailyReminder() {
        edtDailyReminder = spDailyReminder.edit();
        switchReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                edtDailyReminder.putBoolean(KEY_DAILY_REMINDER, true);
                edtDailyReminder.apply();
                dailyReminderOn();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.set_daily_reminder), Toast.LENGTH_SHORT).show();
            } else {
                edtDailyReminder.putBoolean(KEY_DAILY_REMINDER, false);
                edtDailyReminder.commit();
                dailyReminderOff();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.cancel_daily_reminder), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setReleaseReminder() {
        edtReleaseReminder = spReleaseReminder.edit();
        switchRelease.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                edtReleaseReminder.putBoolean(KEY_RELEASE, true);
                edtReleaseReminder.apply();
                releaseReminderOn();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.set_release_reminder), Toast.LENGTH_SHORT).show();
            } else {
                edtReleaseReminder.putBoolean(KEY_RELEASE, false);
                edtReleaseReminder.commit();
                releaseReminderOff();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.cancel_release_reminder), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void releaseReminderOn() {
        String message = getResources().getString(R.string.message_release_reminder);
        notificationPreference.setReleaseTime(timeRelease);
        notificationPreference.getReleaseMessage(message);
        movieReleaseReminder.setReleaseAlarm(SettingActivity.this, TYPE_RELEASE, timeRelease, message);
    }

    private void releaseReminderOff() {
        movieReleaseReminder.cancelNotification(SettingActivity.this);
    }

    private void dailyReminderOn() {
        String message = getResources().getString(R.string.message_daily_reminder);
        notificationPreference.setDailyTime(timeDaily);
        notificationPreference.getDailyMessage(message);
        movieDailyReminder.setDailyAlarm(SettingActivity.this, TYPE_DAILY, timeDaily, message);
    }

    private void dailyReminderOff() {
        movieDailyReminder.cancelNotification(SettingActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}