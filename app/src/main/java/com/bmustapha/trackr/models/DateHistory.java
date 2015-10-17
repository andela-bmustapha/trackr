package com.bmustapha.trackr.models;

/**
 * Created by tunde on 10/17/15.
 */
public class DateHistory {

    private String date;
    private int count;

    public DateHistory(int count, String date) {
        this.count = count;
        this.date = date;
    }

    // setters
    public void setCount(int count) {
        this.count = count;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // getters

    public int getCount() {
        return count;
    }

    public String getDate() {
        return date;
    }
}
