package com.bmustapha.trackr.utilities;

import android.content.Context;
import android.location.Geocoder;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by andela on 10/17/15.
 */
public class LocationAddressHelper {

    public static String getAddressFromLocation(Context context, double latitude, double longitude) {
        Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
        String address = null;
        try {
            address = geoCoder.getFromLocation(latitude, longitude, 1).get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }
}
