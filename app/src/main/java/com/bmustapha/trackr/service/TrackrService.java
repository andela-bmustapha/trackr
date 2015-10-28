package com.bmustapha.trackr.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bmustapha.trackr.R;
import com.bmustapha.trackr.broadcasts.NotificationBroadcast;
import com.bmustapha.trackr.db.LocationDb;
import com.bmustapha.trackr.interfaces.TrackrTimerListener;
import com.bmustapha.trackr.utilities.Constants;
import com.bmustapha.trackr.utilities.LocationAddressHelper;
import com.bmustapha.trackr.utilities.TrackrTimer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

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

    private TrackrTimer trackrTimer;
    private Location lastLocation = null;
    private LocationDb locationDb;


    @Override
    public void onCreate() {
        //create the service
        super.onCreate();
        // put the service instance in a static variable
        trackrService = this;

        locationDb = new LocationDb(getApplicationContext());

        setTrackingIntents();

        setLocationListenerObjects();
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

    private void setTrackingIntents() {
        serviceActionsIntent = new Intent(Constants.BROADCAST_ACTION);

        // instance of custom broadcast receiver
        notificationBroadcast = new NotificationBroadcast();

        IntentFilter notificationIntentFilter = new IntentFilter();
        notificationIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        notificationIntentFilter.addAction(Constants.NOTIFY_CLOSE);
        registerReceiver(notificationBroadcast, notificationIntentFilter);
    }

    private void setLocationListenerObjects() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(20 * 1000)
                .setFastestInterval(3 * 1000);
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

    private void startTimer() {
        stopTimer();
        start();
    }

    private void stopTimer() {
        if (trackrTimer != null) {
            trackrTimer.cancel();
        }
    }

    private long getTrackingLimit() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return TimeUnit.MINUTES.toMillis(Long.valueOf(sharedPreferences.getString("time", "5")));
    }

    private void start() {
        int interval = 3000;
        trackrTimer = (TrackrTimer) new TrackrTimer(getTrackingLimit(), interval, new TrackrTimerListener() {

            @Override
            public void finished() {
                // log the current location to database, with date
                String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
                String address = LocationAddressHelper.getAddressFromLocation(getApplicationContext(), lastLocation.getLatitude(), lastLocation.getLongitude());
                String longitude = String.valueOf(lastLocation.getLongitude());
                String latitude = String.valueOf(lastLocation.getLatitude());
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Long maximumTime = TimeUnit.MINUTES.toMillis(Long.valueOf(sharedPreferences.getString("time", "5")));
                String time = String.valueOf(maximumTime);
                com.bmustapha.trackr.models.Location location = new com.bmustapha.trackr.models.Location(address, currentDate, latitude, longitude, time);
                try {
                    locationDb.insertLocation(location);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendNotification() {

        String notificationService = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) getSystemService(notificationService);

        RemoteViews simpleContentView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.custom_notification);

        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Trackr Notification").build();

        notification.contentView = simpleContentView;

        String notificationMessage = (tracking) ? "Tracking active..." : "Tracking inactive.";
        notification.contentView.setTextViewText(R.id.tracking_status, notificationMessage);

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
        // start timer
        startTimer();
    }

    public void updateTime() {
        startTimer();
    }

    private void connect() {
        Toast.makeText(this, "Tracking Started...", Toast.LENGTH_SHORT).show();
        mGoogleApiClient.connect();

    }

    private void disconnect() {
        Toast.makeText(this, "Tracking stopped...", Toast.LENGTH_SHORT).show();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    public void stopTracking() {
        tracking = false;
        sendNotification();
        disconnect();
        // stop timer
        stopTimer();
    }

    public boolean isTracking() {
        return tracking;
    }

    private void handleNewLocation(Location location) {
        // check if lastLocation is not null
        if ((lastLocation != null) && (lastLocation.distanceTo(location) >= 30)) {
            startTimer();
        }
        lastLocation = location;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
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
