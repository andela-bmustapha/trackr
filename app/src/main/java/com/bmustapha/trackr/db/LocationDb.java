package com.bmustapha.trackr.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bmustapha.trackr.models.AddressHistory;
import com.bmustapha.trackr.models.DateHistory;
import com.bmustapha.trackr.models.Location;

import java.util.ArrayList;

/**
 * Created by tunde on 10/16/15.
 */
public class LocationDb extends SQLiteOpenHelper {

    public LocationDb(Context context) {
        super(context, DbConstants.DATABASE_NAME, null, DbConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DbConstants.LOCATIONS_TABLE_CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DbConstants.LOCATIONS_TABLE_UPGRADE_STATEMENT);
    }

    public boolean insertLocation(Location location) {
        boolean status = false;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbConstants.LOCATIONS_TABLE_DATE_COLUMN, location.getDate());
            contentValues.put(DbConstants.LOCATIONS_TABLE_LONG_COLUMN, location.getLongitude());
            contentValues.put(DbConstants.LOCATIONS_TABLE_LAT_COLUMN, location.getLatitude());
            contentValues.put(DbConstants.LOCATIONS_TABLE_ADDRESS_COLUMN, location.getAddress());
            contentValues.put(DbConstants.LOCATIONS_TABLE_TIME_COLUMN, location.getTime());
            sqLiteDatabase.insert(DbConstants.LOCATIONS_TABLE, null, contentValues);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public int getLocationHistoryCount() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DbConstants.LOCATIONS_TABLE, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /*
        Methods to help organize address related database queries
     */

    public ArrayList<String> getUniqueLocationAddressQueryArg() {
        ArrayList<String> addresses = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(true, DbConstants.LOCATIONS_TABLE, new String[]{"address"}, null, null, "address", null, null, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String address = cursor.getString(cursor.getColumnIndex("address"));
                    if (address != null) {
                        addresses.add(address);
                    }
                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addresses;
    }

    public AddressHistory getAddressLocationsHistory(String address) {
        AddressHistory addressHistory = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from locations where TRIM(address) = '" + address.trim() + "'", null);
        if (cursor.moveToFirst()) {
            String cursorAddress = cursor.getString(cursor.getColumnIndex(DbConstants.LOCATIONS_TABLE_ADDRESS_COLUMN));
            addressHistory = new AddressHistory(cursor.getCount(), cursorAddress);
            Long time = (long) 0;
            while (!cursor.isAfterLast()) {
                String stringTime = cursor.getString(cursor.getColumnIndex(DbConstants.LOCATIONS_TABLE_TIME_COLUMN));
                time += Long.valueOf(stringTime);
                cursor.moveToNext();
            }
            addressHistory.setTime(String.valueOf(time));
        }
        cursor.close();
        return addressHistory;
    }

    /*
        Methods to help organize date related database queries
     */
    public ArrayList<String> getUniqueLocationDateQueryArg() {
        ArrayList<String> dates = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(true, DbConstants.LOCATIONS_TABLE, new String[]{"date"}, null, null, "date", null, null, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    dates.add(date);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }

    public DateHistory getDateLocationsHistory(String date) {
        DateHistory dateHistory = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from locations where TRIM(date) = '" + date.trim() + "'", null);
        if (cursor.moveToFirst()) {
            String cursorDate = cursor.getString(cursor.getColumnIndex(DbConstants.LOCATIONS_TABLE_DATE_COLUMN));
            dateHistory = new DateHistory(cursor.getCount(), cursorDate);
        }
        cursor.close();
        return dateHistory;
    }

    public ArrayList<Location> getDateLocations(String date) {
        ArrayList<Location> locations = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from locations where TRIM(date) = '" + date.trim() + "'", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Location location = new Location();
                location.setDate(cursor.getString(cursor.getColumnIndex(DbConstants.LOCATIONS_TABLE_DATE_COLUMN)));
                location.setLongitude(cursor.getString(cursor.getColumnIndex(DbConstants.LOCATIONS_TABLE_LONG_COLUMN)));
                location.setLatitude(cursor.getString(cursor.getColumnIndex(DbConstants.LOCATIONS_TABLE_LAT_COLUMN)));
                location.setAddress(cursor.getString(cursor.getColumnIndex(DbConstants.LOCATIONS_TABLE_ADDRESS_COLUMN)));
                locations.add(location);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return locations;
    }
}
