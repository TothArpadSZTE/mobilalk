package com.example.autobiztositas;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private static final String CHANNEL_ID = "autobiztositas_notification_channel";
    private final int NOTICIFATION_ID = 0;
    private NotificationManager mManager;
    private Context mContext;
    public NotificationHandler(Context context){
        this.mContext = context;
        this.mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Autobiztosítás Notification", NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.RED);
        channel.setDescription("Értesítés az alkalmazástól");
        this.mManager.createNotificationChannel(channel);
    }

    public void send(String message, int notificationId) {
        Intent intent = new Intent(mContext, MainPage.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setContentTitle("Autóbiztosítás alkalmazás")
                .setContentText(message)
                .setSmallIcon(R.drawable.cart)
                .setContentIntent(pendingIntent);

        this.mManager.notify(notificationId, builder.build());
    }
}
