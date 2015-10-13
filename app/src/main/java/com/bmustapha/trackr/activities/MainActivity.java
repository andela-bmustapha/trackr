package com.bmustapha.trackr.activities;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private Button trackingButton;
    private Button stopTrackingButton;
    private boolean isTracking = false;

    final int sdk = android.os.Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

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

        setUp();
    }

    private void setUp() {
        trackingButton = (Button) findViewById(R.id.start_tracking_button);
        Button historyButton = (Button) findViewById(R.id.history_button);
        stopTrackingButton = (Button) findViewById(R.id.stop_tracking_button);

        handleButtonsDisplay();

        trackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleTracking();
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHistory();
            }
        });

        stopTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleTracking();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, Setting.class);
            startActivity(settingsIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleTracking() {
        handleButtonsDisplay();
    }

    private void handleButtonsDisplay() {
        if (isTracking) {
            isTracking = false;
            trackingButton.setVisibility(View.GONE);
            stopTrackingButton.setVisibility(View.VISIBLE);
        } else {
            isTracking = true;
            trackingButton.setVisibility(View.VISIBLE);
            stopTrackingButton.setVisibility(View.GONE);
        }
    }

    private void showHistory() {
        Toast.makeText(this, "History button clicked!", Toast.LENGTH_SHORT).show();
    }
}
