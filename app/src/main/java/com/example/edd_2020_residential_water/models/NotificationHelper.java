package com.example.edd_2020_residential_water.models;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.edd_2020_residential_water.R;
import com.example.edd_2020_residential_water.intaking.Intake;

public class NotificationHelper {

    public static void displayNotification(Context context, String title, String body) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, Intake.CHANNEL_ID)
                .setSmallIcon(R.drawable.example_button)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationMngr = NotificationManagerCompat.from(context);
        mNotificationMngr.notify(1, mBuilder.build());
    }
}
