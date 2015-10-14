package com.bmustapha.trackr.utilities;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by tunde on 10/14/15.
 */
public class ServiceChecker {

    public static boolean isServiceRunning(String serviceName, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if(serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
