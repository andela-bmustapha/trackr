package com.bmustapha.trackr.db;

/**
 * Created by tunde on 10/16/15.
 */
public class DbConstants {

    public static final String DATABASE_NAME = "Trackr.db";
    public static final int DATABASE_VERSION = 1;

    // define table and columns names
    public static final String LOCATIONS_TABLE = "locations";
    public static final String LOCATIONS_TABLE_ID_COLUMN = "id";
    public static final String LOCATIONS_TABLE_DATE_COLUMN = "date";
    public static final String LOCATIONS_TABLE_LONG_COLUMN = "longitude";
    public static final String LOCATIONS_TABLE_LAT_COLUMN = "latitude";
    public static final String LOCATIONS_TABLE_ADDRESS_COLUMN = "address";

    // define table creation statements
    public static final String LOCATIONS_TABLE_CREATE_STATEMENT = "create table " + LOCATIONS_TABLE + " (" +
            LOCATIONS_TABLE_ID_COLUMN + " integer primary key, " +
            LOCATIONS_TABLE_DATE_COLUMN + " text, " +
            LOCATIONS_TABLE_LONG_COLUMN + " text, " +
            LOCATIONS_TABLE_LAT_COLUMN + " text, " +
            LOCATIONS_TABLE_ADDRESS_COLUMN + " text)";

    public static final String LOCATIONS_TABLE_UPGRADE_STATEMENT = "DROP TABLE IF EXISTS " + LOCATIONS_TABLE;
}
