package com.bmustapha.trackr.models;

import java.io.Serializable;

/**
 * Created by andela on 10/16/15.
 */
public class Location implements Serializable {

    private String date;
    private String longitude;
    private String latitude;
    private String address;
    private String time;

    public Location() {

    }

    public Location(String address, String date, String latitude, String longitude, String time) {
        this.address = address;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public Location(String address, String date, String latitude, String longitude) {
        this.address = address;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // setters
    public void setAddress(String address) {
        this.address = address;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // getters
    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTime() {
        return time;
    }
}
