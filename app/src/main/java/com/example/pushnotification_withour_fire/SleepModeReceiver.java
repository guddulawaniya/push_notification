package com.example.pushnotification_withour_fire;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class SleepModeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action != null && action.equals(Intent.ACTION_SCREEN_OFF)) {
            // The screen has turned off, show your notification here
            showNotification(context);
        }
    }

    private void showNotification(Context context) {
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        // Create a Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setContentTitle("New Message")
                .setContentText("You have a new message")
                .setSmallIcon(R.drawable.baseline_notifications_active_24);

        // Show the notification
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        notificationManager.notify(1, builder.build());
    }
}
