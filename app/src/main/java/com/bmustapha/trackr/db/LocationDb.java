package com.bmustapha.trackr.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
