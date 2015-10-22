package com.bmustapha.trackr;

import android.test.AndroidTestCase;

import com.bmustapha.trackr.fragments.DateHistoryFragment;
import com.bmustapha.trackr.fragments.LocationHistoryFragment;
import com.bmustapha.trackr.fragments.SettingsFragment;

/**
 * Created by tunde on 10/22/15.
 */
public class FragmentsTest extends AndroidTestCase {

    DateHistoryFragment dateHistoryFragment = new DateHistoryFragment();
    LocationHistoryFragment locationHistoryFragment = new LocationHistoryFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    public void testDateHistoryFragmentNotNull() {
        assertNotNull(dateHistoryFragment);
    }

    public void testLocationHistoryFragmentNotNull() {
        assertNotNull(locationHistoryFragment);
    }

    public void testSettingsFragmentNotNull() {
        assertNotNull(settingsFragment);
    }
}
