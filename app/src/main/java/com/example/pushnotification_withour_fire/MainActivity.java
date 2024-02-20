package com.example.pushnotification_withour_fire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.window.Notification;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "MyChannel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        createNotificationChannel();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showNotification();
            }
        });

        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.NANOSECONDS)
                        .build();
        WorkManager.getInstance(this).enqueue(periodicWorkRequest);


    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Channel";
            String description = "Channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void showNotification() {
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());
        Intent intent = new Intent(this, MyApplications.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        // Create a Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle("New Message")
                .setContentText("You have a new message")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.baseline_notifications_active_24);

        builder.setContentIntent(pendingIntent);

        // Show the notification
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}