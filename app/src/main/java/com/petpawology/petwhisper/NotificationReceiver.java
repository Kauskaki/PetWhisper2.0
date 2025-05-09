package com.petpawology.petwhisper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra("notificationType");

        int notificationId;
        String title;
        String message;
        String petName = intent.getStringExtra("petName");



        switch (type) {
            case "Mealtime":
                notificationId = 101;
                title = "Mealtime Reminder";
                message = "It's time for" + petName + "to eat!";
                break;
            case "Medications":
                notificationId = 102;
                title = "Medication Reminder";
                message = "Don't forget " + petName + " needs their medication!";
                break;
            case "Vaccines":
                notificationId = 103;
                title = "Vaccine Reminder";
                message = "Time to check your vaccination schedule.";
                break;
            default:
                notificationId = 104;
                title = "General Reminder";
                message = "Here's your scheduled notification.";
                break;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "my_channel_id")
                .setSmallIcon(R.mipmap.petwhisper_tempic)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Intent settingsIntent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                context.startActivity(settingsIntent);
                return;
            }

            return;
        }
        manager.notify(notificationId, builder.build()); // Use unique ID per type
    }
}
