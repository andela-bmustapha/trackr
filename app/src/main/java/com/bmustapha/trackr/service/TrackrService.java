package com.bmustapha.trackr.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.bmustapha.trackr.R;
import com.bmustapha.trackr.utilities.Constants;

/**
 * Created by tunde on 10/13/15.
 */
public class TrackrService extends Service {

    // assign service to its static self
    public static TrackrService trackrService;
    private boolean tracking = false;


    @Override
    public void onCreate() {
        //create the service
        super.onCreate();
        // put the service instance in a static variable
        trackrService = this;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.START_FOREGROUND_ACTION)) {
            sendNotification();
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendNotification() {

        String notificationService = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) getSystemService(notificationService);

        RemoteViews simpleContentView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.custom_notification);

        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Trackr Notification").build();

        notification.contentView = simpleContentView;

        if (tracking) {
            notification.contentView.setTextViewText(R.id.tracking_status, "Tracking in progress...");
        } else {
            notification.contentView.setTextViewText(R.id.tracking_status, "Tracking stopped.");
        }

        Intent closeIntent = new Intent(Constants.NOTIFY_CLOSE);
        PendingIntent closePendingIntent = PendingIntent.getBroadcast(this, 100, closeIntent, 0);
        simpleContentView.setOnClickPendingIntent(R.id.close_notification, closePendingIntent);

        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        startForeground(Constants.FOREGROUND_SERVICE, notification);
    }

    public void startTracking() {
        tracking = true;
    }

    public void stopTracking() {
        tracking = false;
    }

    public boolean isTracking() {
        return tracking;
    }

}
