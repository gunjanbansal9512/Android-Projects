package com.example.smsaleart;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
    }
    private void checkPermission()
    {
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS},1);
        }


        else
        {
            if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},1);
            }
            else {
                Intent receiveIntent = new Intent("android.provider.Telephony.SMS_RECEIVED");
                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 1, receiveIntent, 0);
            }
            }
    }
}
