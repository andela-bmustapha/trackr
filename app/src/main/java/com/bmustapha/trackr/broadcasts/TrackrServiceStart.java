package com.bmustapha.trackr.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bmustapha.trackr.service.TrackrService;
import com.bmustapha.trackr.utilities.Constants;

/**
 * Created by tunde on 10/13/15.
 */
public class TrackrServiceStart extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent trackerServiceIntent = new Intent(context, TrackrService.class);
        trackerServiceIntent.setAction(Constants.START_FOREGROUND_ACTION);
        context.startService(trackerServiceIntent);
    }
}