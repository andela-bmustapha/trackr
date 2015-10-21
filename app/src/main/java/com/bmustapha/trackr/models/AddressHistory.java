package com.bmustapha.trackr.models;

/**
 * Created by andela on 10/20/15.
 */
public class AddressHistory {

    private String address;
    private int count;
    String time;

    public AddressHistory(int count, String address) {
        this.count = count;
        this.address = address;
    }

    // setters
    public void setCount(int count) {
        this.count = count;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // getters

    public int getCount() {
        return count;
    }

    public String getAddress() {
        return address;
    }

    public String getTime() {
        return time;
    }
}
