package com.bmustapha.trackr.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bmustapha.trackr.R;
import com.bmustapha.trackr.db.LocationDb;
import com.bmustapha.trackr.dialogs.HelpDialog;
import com.bmustapha.trackr.service.TrackrService;
import com.bmustapha.trackr.utilities.Constants;

public class TrackrMainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button trackingButton;
    private Button stopTrackingButton;
    TrackrService trackrService = null;

    final int sdk = android.os.Build.VERSION.SDK_INT;
    private boolean broadcastIsRegistered;

    LocationDb locationDb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set default preference the first time user installs application
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        trackrService = TrackrService.trackrService;

        locationDb = new LocationDb(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setTitle("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/1942.ttf");
        TextView logoTextView = (TextView) findViewById(R.id.logo);
        logoTextView.setTypeface(face);

        registerReceiver(broadcastReceiver, new IntentFilter(Constants.BROADCAST_ACTION));
        broadcastIsRegistered = true;

        setUp();
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        trackrService = TrackrService.trackrService;
        if (trackrService == null) {
            Intent splashIntent = new Intent(getApplicationContext(), SplashActivity.class);
            startActivity(splashIntent);
            finish();
        }
    }

    private void setUp() {

        trackingButton = (Button) findViewById(R.id.start_tracking_button);
        Button historyButton = (Button) findViewById(R.id.history_button);
        Button howItWorksButton = (Button) findViewById(R.id.how_it_works_button);
        stopTrackingButton = (Button) findViewById(R.id.stop_tracking_button);

        handleButtonsDisplay();

        trackingButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);
        stopTrackingButton.setOnClickListener(this);
        howItWorksButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, TrackrSettingsActivity.class);
            startActivity(settingsIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_tracking_button:
                handleTrackingClick();
                break;
            case R.id.history_button:
                showHistory();
                break;
            case R.id.stop_tracking_button:
                handleTrackingClick();
                break;
            case R.id.how_it_works_button:
                displayHelp();
                break;
        }
    }

    private void handleTrackingClick() {
        try {
            if (!broadcastIsRegistered) {
                registerReceiver(broadcastReceiver, new IntentFilter(Constants.BROADCAST_ACTION));
                broadcastIsRegistered = true;

            } else {
                unregisterReceiver(broadcastReceiver);
                broadcastIsRegistered = false;

            }
            toggleTracking();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayHelp() {
        View view = getLayoutInflater().inflate(R.layout.help, null);
        new HelpDialog().showDialog(this, view);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            handleBroadCast(intent);
        }
    };

    private void handleBroadCast(Intent intent) {
        boolean stopped = intent.getBooleanExtra("trackingStopped", false);
        if (stopped) {
            handleButtonsDisplay();
        }
    }

    private void toggleTracking() {
        if (trackrService.isTracking()) {
            trackrService.stopTracking();
        } else {
            trackrService.startTracking();
        }
        handleButtonsDisplay();
    }

    private void handleButtonsDisplay() {
        if (trackrService.isTracking()) {
            trackingButton.setVisibility(View.GONE);
            stopTrackingButton.setVisibility(View.VISIBLE);
        } else {
            trackingButton.setVisibility(View.VISIBLE);
            stopTrackingButton.setVisibility(View.GONE);
        }
    }

    private void showHistory() {
        if (locationDb.getLocationHistoryCount() < 1) {
            Toast.makeText(TrackrMainActivity.this, R.string.history_error_message, Toast.LENGTH_SHORT).show();
        } else {
            Intent historyIntent = new Intent(this, HistoryActivity.class);
            startActivity(historyIntent);
        }
    }
}
