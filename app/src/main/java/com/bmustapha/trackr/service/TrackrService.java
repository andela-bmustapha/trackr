package com.bmustapha.trackr.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bmustapha.trackr.R;
import com.bmustapha.trackr.broadcasts.NotificationBroadcast;
import com.bmustapha.trackr.utilities.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by tunde on 10/13/15.
 */
public class TrackrService extends Service implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    // assign service to its static self
    public static TrackrService trackrService;
    private boolean tracking = false;

    private NotificationBroadcast notificationBroadcast;
    Intent serviceActionsIntent;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;


    @Override
    public void onCreate() {
        //create the service
        super.onCreate();
        // put the service instance in a static variable
        trackrService = this;

        serviceActionsIntent = new Intent(Constants.BROADCAST_ACTION);

        // instance of custom broadcast receiver
        notificationBroadcast = new NotificationBroadcast();

        IntentFilter notificationIntentFilter = new IntentFilter();
        notificationIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        notificationIntentFilter.addAction(Constants.NOTIFY_CLOSE);
        registerReceiver(notificationBroadcast, notificationIntentFilter);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1000); // 1 second, in milliseconds
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            if (intent.getAction().equals(Constants.START_FOREGROUND_ACTION)) {
                sendNotification();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        trackrService = null;
        unregisterReceiver(notificationBroadcast);
        disconnect();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void removeNotification() {
        if (tracking) {
            stopTracking();
        }
        try {
            broadCast();
            stopForeground(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void broadCast() {
        serviceActionsIntent.putExtra("trackingStopped", true);
        sendBroadcast(serviceActionsIntent);
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
            notification.contentView.setTextViewText(R.id.tracking_status, "Tracking active...");
        } else {
            notification.contentView.setTextViewText(R.id.tracking_status, "Tracking inactive.");
        }

        Intent closeIntent = new Intent(Constants.NOTIFY_CLOSE);
        PendingIntent closePendingIntent = PendingIntent.getBroadcast(this, 100, closeIntent, 0);
        simpleContentView.setOnClickPendingIntent(R.id.close_notification, closePendingIntent);

        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        startForeground(Constants.FOREGROUND_SERVICE, notification);
    }

    public void startTracking() {
        tracking = true;
        sendNotification();
        connect();
    }

    private void connect() {
        mGoogleApiClient.connect();
    }

    private void disconnect() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    public void stopTracking() {
        tracking = false;
        sendNotification();
        disconnect();
    }

    public boolean isTracking() {
        return tracking;
    }

    private void handleNewLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        Toast.makeText(this, "Long: " + currentLongitude + "  Lat: " + currentLatitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,  this);
        if (location != null) {
            handleNewLocation(location);
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
