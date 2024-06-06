package com.example.musclepal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {//Notification Handler
        NotiHandler notiHandler = new NotiHandler(context);
        NotificationCompat.Builder nb = notiHandler.getChannelNotification();
        notiHandler.getManager().notify(1,nb.build());

    }
}
