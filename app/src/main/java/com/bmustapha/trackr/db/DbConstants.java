package com.bmustapha.trackr.db;

/**
 * Created by tunde on 10/16/15.
 */
public class DbConstants {

    public static final String DATABASE_NAME = "Trackr.db";
    public static final int DATABASE_VERSION = 1;

    // define tables name
    public static final String LOCATIONS_TABLE = "locations";

    // define table creation statements
    public static final String LOCATIONS_TABLE_CREATE_STATEMENT = "create table " + LOCATIONS_TABLE + " (" +
            "id integer primary key," +
            "date text," +
            "longitude text," +
            "latitude text," +
            "address text)";

    public static final String LOCATIONS_TABLE_UPGRADE_STATEMENT = "DROP TABLE IF EXISTS " + LOCATIONS_TABLE;
}
