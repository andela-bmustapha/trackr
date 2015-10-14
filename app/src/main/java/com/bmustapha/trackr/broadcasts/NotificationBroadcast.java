package com.bmustapha.trackr.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bmustapha.trackr.service.TrackrService;
import com.bmustapha.trackr.utilities.Constants;

public class NotificationBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()) {
            case Constants.NOTIFY_CLOSE:
                TrackrService.trackrService.removeNotification();
                break;
        }
    }
}
