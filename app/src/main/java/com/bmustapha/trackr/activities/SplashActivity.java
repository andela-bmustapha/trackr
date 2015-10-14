package com.bmustapha.trackr.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bmustapha.trackr.R;
import com.bmustapha.trackr.service.TrackrService;
import com.bmustapha.trackr.utilities.Constants;
import com.bmustapha.trackr.utilities.ServiceChecker;
import com.victor.loading.rotate.RotateLoading;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        RotateLoading rotateLoading = (RotateLoading) findViewById(R.id.rotate_loading);
        rotateLoading.start();

        // start the service
        boolean isServiceRunning = ServiceChecker.isServiceRunning(TrackrService.class.getName(), getApplicationContext());
        if (!isServiceRunning || (TrackrService.trackrService == null)) {
            Intent trackrServiceIntent = new Intent(getApplicationContext(), TrackrService.class);
            trackrServiceIntent.setAction(Constants.START_FOREGROUND_ACTION);
            startService(trackrServiceIntent);
            // pause for a while to get the service running before moving to main screen...
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent trackrMainActivityIntent = new Intent(getApplicationContext(), TrackrMainActivity.class);
                    startActivity(trackrMainActivityIntent);
                    finish();
                }
            }, 3000);
        } else {
            // service is already running, move at once
            Intent trackrMainActivityIntent = new Intent(getApplicationContext(), TrackrMainActivity.class);
            startActivity(trackrMainActivityIntent);
            finish();
        }
    }
}
