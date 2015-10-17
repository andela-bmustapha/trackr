package com.bmustapha.trackr.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
            sqLiteDatabase.insert(DbConstants.LOCATIONS_TABLE, null, contentValues);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public ArrayList<Location> getAllLocations() {
        ArrayList<Location> locations = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor res =  sqLiteDatabase.rawQuery("select * from " + DbConstants.LOCATIONS_TABLE, null);
        if (res.moveToFirst()) {
            while (!res.isAfterLast()) {
                Location location = new Location();
                location.setDate(res.getString(res.getColumnIndex(DbConstants.LOCATIONS_TABLE_DATE_COLUMN)));
                location.setLongitude(res.getString(res.getColumnIndex(DbConstants.LOCATIONS_TABLE_LONG_COLUMN)));
                location.setLatitude(res.getString(res.getColumnIndex(DbConstants.LOCATIONS_TABLE_LAT_COLUMN)));
                location.setAddress(res.getString(res.getColumnIndex(DbConstants.LOCATIONS_TABLE_ADDRESS_COLUMN)));
                locations.add(location);
                res.moveToNext();
            }
            res.close();
        }
        return locations;
    }

    public ArrayList<String> getUniqueLocationQueryArg() {
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

    public ArrayList<Location> getDateLocations(String query) {
        ArrayList<Location> locations = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor =  sqLiteDatabase.rawQuery("select * from locations where date=" + query, null);
        Log.e("All Locations: ", String.valueOf(cursor.getCount()));
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

    public int getDateLocationCount(String query) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor =  sqLiteDatabase.rawQuery("select * from locations where date=" + query, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

}
