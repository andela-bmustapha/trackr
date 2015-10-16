package com.bmustapha.trackr.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public ArrayList<String> getUniqueLocationQueryArg(boolean flag) {
        ArrayList<String> dates = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String column = getDatabaseColumn(flag);
        Cursor res =  sqLiteDatabase.rawQuery("select distinct " + column + " from " + DbConstants.LOCATIONS_TABLE, null);
        if (res.moveToFirst()) {
            while (!res.isAfterLast()) {
                String date = res.getString(res.getColumnIndex(column));
                dates.add(date);
            }
            res.close();
        }
        return dates;
    }

    public ArrayList<Location> getCustomLocations(String query, boolean flag) {
        ArrayList<Location> locations = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String column = getDatabaseColumn(flag);
        Cursor res =  sqLiteDatabase.rawQuery("select * from " + DbConstants.LOCATIONS_TABLE + " where " + column + "="  + query, null);
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

    private String getDatabaseColumn(boolean flag) {
        return (flag) ? DbConstants.LOCATIONS_TABLE_DATE_COLUMN : DbConstants.LOCATIONS_TABLE_ADDRESS_COLUMN;
    }
}
