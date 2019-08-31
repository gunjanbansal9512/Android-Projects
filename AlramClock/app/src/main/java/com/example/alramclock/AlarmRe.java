package com.example.alramclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.alramclock.R;

public class AlarmRe extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentTitle("Message");
        builder.setContentText("Alarm is ringing");
        //builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.build();
        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0,builder.build());
      //  Toast.makeText(context.getApplicationContext(),"Alarm Set",Toast.LENGTH_LONG).show();


    }
}
