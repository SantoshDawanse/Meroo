package com.dawanse.dawn.meroo.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.dawanse.dawn.meroo.R;
import com.dawanse.dawn.meroo.activity.MainActivity;

public class NotificationReceiver extends BroadcastReceiver{

    private static final int NOTIFICATION_ID = 1991;

    @Override
    public void onReceive(Context context, Intent intent) {

        int num = 0;

        Intent contentIntent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, contentIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setContentTitle("You have item in cart.");
        builder.setContentText("you item is ready in the cart");
        builder.setContentIntent(pendingIntent);
        builder.setTicker("you have something to buy.");
        builder.setAutoCancel(true);
        builder.setOnlyAlertOnce(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setNumber(++num);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
