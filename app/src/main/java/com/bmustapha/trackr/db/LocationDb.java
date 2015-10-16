package com.bmustapha.trackr.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public boolean insertLocation(String date, String longitude, String latitude, String address) {
        boolean status = false;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbConstants.LOCATIONS_TABLE_DATE_COLUMN, date);
            contentValues.put(DbConstants.LOCATIONS_TABLE_LONG_COLUMN, longitude);
            contentValues.put(DbConstants.LOCATIONS_TABLE_LAT_COLUMN, latitude);
            contentValues.put(DbConstants.LOCATIONS_TABLE_ADDRESS_COLUMN, address);
            sqLiteDatabase.insert(DbConstants.LOCATIONS_TABLE, null, contentValues);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public ArrayList<PlayList> getAllPlayLists() {
        ArrayList<PlayList> allPlayLists = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from playlist", null);
        if (res.moveToFirst()) {
            while (!res.isAfterLast()) {
                PlayList playList = new PlayList();
                playList.setName(res.getString(res.getColumnIndex(PLAYLIST_COLUMN_NAME)));
                playList.setDescription(res.getString(res.getColumnIndex(PLAYLIST_COLUMN_DESCRIPTION)));
                playList.setDbId(res.getInt(res.getColumnIndex(PLAYLIST_COLUMN_ID)));
                allPlayLists.add(playList);
                res.moveToNext();
            }
            res.close();
        }
        return allPlayLists;
    }
}
