package com.dicoding.picodiploma.moviecataloguelastproject.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.dicoding.picodiploma.moviecataloguelastproject.MainActivity;
import com.dicoding.picodiploma.moviecataloguelastproject.R;

import java.util.Calendar;
import java.util.Objects;

public class MovieDailyReminder extends BroadcastReceiver {
    int NOTIFICATION_ID = 1;

    public MovieDailyReminder(){
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        showDailyNotification(context, context.getString(R.string.app_name),
                context.getString(R.string.message_daily_reminder), NOTIFICATION_ID);

    }
    public void setDailyAlarm(Context context, String type, String time, String message) {
        cancelNotification(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MovieDailyReminder.class);
        intent.putExtra("message", message);
        intent.putExtra("type", type);
        String[] timeArray = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        Objects.requireNonNull(alarmManager).setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    private void showDailyNotification(Context context, String title, String message, int id) {
        String CHANNEL_ID = "channel_01";
        String CHANNEL_NAME = "AlarmManager channel";


        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, message)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setColorized(true)
                .setColor(ContextCompat.getColor(context, R.color.colorBackgroundModel))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(true)
                .setSound(alarmSound);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            channel.enableLights(true);
            channel.setLightColor(R.color.colorBackgroundModel);
            builder.setChannelId(CHANNEL_ID);

            Objects.requireNonNull(notificationManagerCompat).createNotificationChannel(channel);
        }
        Objects.requireNonNull(notificationManagerCompat).notify(id, builder.build());
    }
    public void cancelNotification(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MovieDailyReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        Objects.requireNonNull(alarmManager).cancel(pendingIntent);
    }
}
