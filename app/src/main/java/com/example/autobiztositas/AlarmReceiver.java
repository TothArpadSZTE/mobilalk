package com.example.autobiztositas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        new NotificationHandler(context).send("Kötöttél már biztosítást az autódra?",1);
    }
}