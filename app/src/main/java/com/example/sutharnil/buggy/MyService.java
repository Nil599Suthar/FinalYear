package com.example.sutharnil.buggy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class MyService extends Service {

    private int NOTIFICATION = 1; // Unique identifier for our notification
    public static boolean isRunning = false;
    public static MyService instance = null;

    private NotificationManager notificationManager = null;

    public MyService() {
    }


    @Override
    public void onCreate(){
        instance = this;
        isRunning = true;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        super.onCreate();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                loginActivity.class), 0);
        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher) // the status icon
                .setTicker("Service running...") // the status text
                .setWhen(System.currentTimeMillis()) // the time stamp
                .setContentTitle("Online") // the label of the entry
                .setContentText("Application running...") // the content of the entry
                .setContentIntent(contentIntent) // the intent to send when the entry is
                .setOngoing(true) // make persistent (disable swipe-away)
                .build();
        // Start service in foreground mode
        startForeground(NOTIFICATION, notification);

        return Service.START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy(){
        isRunning = false;
        instance = null;
        notificationManager.cancel(NOTIFICATION); // Remove notification
        super.onDestroy();
    }

}

