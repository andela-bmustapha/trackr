package com.bmustapha.trackr.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by andela on 10/13/15.
 */
public class TrackerService extends Service {

    // assign service to its static self
    TrackerService trackerService;

    @Override
    public void onCreate() {
        //create the service
        super.onCreate();
        // put the service instance in a static variable
        trackerService = this;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
