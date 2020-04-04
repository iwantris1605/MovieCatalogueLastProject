package com.dicoding.picodiploma.moviecataloguelastproject.notification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class preference {
    private final static String PREFERENCE_NAME = "reminderPreferences";
    private final static String DAILY_REMINDER = "DailyReminder";
    private final static String RELEASE_REMINDER = "ReleaseReminder";
    private final static String RELEASE_MESSAGE = "messageRelease";
    private final static String DAILY_MESSAGE = "messageDaily";
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public preference(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setReleaseTime(String time){
        editor.putString(RELEASE_REMINDER, time);
        editor.commit();
    }

    public void getReleaseMessage(String message){
        editor.putString(RELEASE_MESSAGE, message);
    }

    public void setDailyTime(String time){
        editor.putString(DAILY_REMINDER, time);
        editor.commit();

    }

    public void getDailyMessage(String message){
        editor.putString(DAILY_MESSAGE, message);
    }

}
