package com.bmustapha.trackr.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.bmustapha.trackr.R;
import com.bmustapha.trackr.service.TrackrService;

/**
 * Created by tunde on 10/13/15.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        String time = sharedPreferences.getString("time", "5");
        EditTextPreference preference = (EditTextPreference) findPreference("time");
        String tag = (Double.parseDouble(time) > 1) ? " minutes" : " minute";
        preference.setSummary("Maximum time: " + time +  tag);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference pref = findPreference(key);
        if (pref instanceof EditTextPreference) {
            EditTextPreference etp = (EditTextPreference) pref;
            String tag = (Double.parseDouble(etp.getText()) > 1) ? " minutes" : " minute";
            pref.setSummary("Maximum time: " + etp.getText() + tag);
        }

        if (TrackrService.trackrService != null) {
            TrackrService.trackrService.updateTime();
        }
    }
}
