package com.example.waltex.trusthospital;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //get id and message from intent
        int notification = intent.getIntExtra("notification", 0);
        String message = intent.getStringExtra("todo");

        //when notification is tapped, call hearingActivity.
        Intent hearingIntent = new Intent(context, HearingCard.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, hearingIntent, 0);


        NotificationManager myNotificationManger = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //preparing notification
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("It's Time!")
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL);


        //notify
        myNotificationManger.notify(notification, builder.build());
    }
}
