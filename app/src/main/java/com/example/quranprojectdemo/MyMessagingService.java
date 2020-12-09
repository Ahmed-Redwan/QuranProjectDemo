package com.example.quranprojectdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.webkit.JavascriptInterface;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.quranprojectdemo.Activities.mainActivity.Main_center;
import com.example.quranprojectdemo.Activities.otherActivity.SplashScreen;
import com.example.quranprojectdemo.service.GetDataService;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyMessagingService extends FirebaseMessagingService {// this service to receive Notifications

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody().toLowerCase());
        // write code to do when you receive Notifications
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent serviceIntent = new Intent(getBaseContext(), GetDataService.class);
                startService(serviceIntent);
            }
        }).start();


    }

    @Override
    public void onMessageSent(@NonNull String s) {
        super.onMessageSent(s);
    }

    public void showNotification(String title, String body) {
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();// to get token


        Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNotifications")
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.logo_foreground).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo_foreground))
                .setAutoCancel(true)
                .setContentText(body)
                .setContentIntent(pIntent);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());
    }
}
