package com.example.myalaram;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TimePicker tp ;
    Button set,cancel;
    AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        set = (Button) findViewById(R.id.set);
        cancel = (Button) findViewById(R.id.Cancel);
        tp=(TimePicker) findViewById(R.id.timePicker);
        alarmManager =(AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this,AlarmRe.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        set.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api= Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Calendar c=Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY,tp.getHour());
                c.set(Calendar.MINUTE,tp.getMinute());
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                Toast.makeText(getApplicationContext(),"Alarm Set",Toast.LENGTH_LONG).show();
                //  System.exit(0);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });


    }
}
