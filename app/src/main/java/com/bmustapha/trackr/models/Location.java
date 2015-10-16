package com.bmustapha.trackr.models;

/**
 * Created by andela on 10/16/15.
 */
public class Location {

    private String date;
    private String longitude;
    private String latitude;
    private String address;

    public Location(String address, String date, String latitude, String longitude) {
        this.address = address;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
