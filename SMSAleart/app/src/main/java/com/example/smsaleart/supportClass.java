package com.example.smsaleart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class supportClass extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"You have one message",Toast.LENGTH_LONG).show();
     //   Toast.makeText(context,intent.getData().toString(),Toast.LENGTH_LONG).show();
    }
}
