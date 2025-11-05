package com.example.drewglasser_brown;

/*
 * Name: Drew Glasser-Brown
 * Purpose: This service is used to send the user notifications at specified times in the choose recipe activity
 */

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class MealService extends Service {
    public MealService() {
    }

    @Override
    public void onCreate()
    {
        NotificationManager notifyMgr = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE);
        Notification n; // used later by the builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            // make a channel for the notificaiton
            String channel = "1111";
            CharSequence name = "channel1111"; // name of channel
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel nChannel = new NotificationChannel(channel, name, importance);
            notifyMgr.createNotificationChannel(nChannel);
            // use a "builder" class to generate a configured notification for us,needs our channel
            // we can use method chaining as each of these return a builder
            NotificationCompat.Builder builder = new
                    NotificationCompat.Builder(this, channel)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setChannelId(channel)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentTitle("Meal Time!")
                    .setContentText("It's time for your next meal!")
                    .setAutoCancel(true);
            n = builder.build();
        }
        else
        {
            Notification.Builder builder = new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Meal Time!")
                    .setContentText("It's time for your next meal!")
                    .setAutoCancel(true);
            n = builder.build();
        }
        notifyMgr.notify( 1, n );
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}