package com.bmustapha.trackr.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.bmustapha.trackr.R;
import com.bmustapha.trackr.fragments.SettingsFragment;

public class TrackrSettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        getFragmentManager().beginTransaction().replace(R.id.settings_container, new SettingsFragment()).commit();
    }

}
