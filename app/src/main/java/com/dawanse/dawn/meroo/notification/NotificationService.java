package com.dawanse.dawn.meroo.notification;


import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class NotificationService extends Service{

    NotificationManager notificationManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}
