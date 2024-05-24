package com.app.nabhini.BroadCast;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.app.nabhini.R;
import com.app.nabhini.MainActivity;


public class AlarmsReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID ="alarms_notification_channel";
    private static final int REQUEST_CODE =100 ;
    private static final int NOTIFICATION_ID=100;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        String task = bundle.getString("task");
        String dateAndTime = bundle.getString("timeTask") + " " + bundle.getString("dateTask");

        showBasicNotification(context , task , dateAndTime);

    }

    public void showBasicNotification(Context context , String task , String dateAndTime ) {
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(new Intent(context, MainActivity.class));
        PendingIntent pendingIntent=PendingIntent.getActivities(context,REQUEST_CODE,stackBuilder.getIntents(),0);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
                        .setContentTitle(task)
                        .setContentText(dateAndTime)
                        .setVibrate(new long[]{0,500,700,900})
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setSound(soundUri)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);
        Notification notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }


}
