package com.example.myalaram;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class AlarmRe extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.ic_action_settings);
        builder.setContentTitle("Message");
        builder.setContentText("Alarm is ringing");
       // builder.setPriority(Notification.PRIORITY_DEFAULT);
       // builder.build();

        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0,builder.build());

        Toast.makeText(context,"Alarm is ringing",Toast.LENGTH_LONG).show();


    }
}
