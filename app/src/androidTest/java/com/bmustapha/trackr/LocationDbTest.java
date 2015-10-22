package com.bmustapha.trackr;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.bmustapha.trackr.db.LocationDb;
import com.bmustapha.trackr.models.AddressHistory;
import com.bmustapha.trackr.models.DateHistory;
import com.bmustapha.trackr.models.Location;

import java.util.ArrayList;

/**
 * Created by tunde on 10/22/15.
 */
public class LocationDbTest extends AndroidTestCase {

    LocationDb locationDb;
    private final String address = "16, Funsho Street";
    private final String date = "12-09-2015";
    private final String latitude = "2.90321";
    private final String longitude = "6.12321";
    private final String time = "34332222";

    private final String address2 = "4, Alara Street";
    private final String date2 = "12-09-2015";
    private final String latitude2 = "2.90351";
    private final String longitude2 = "1.133721";
    private final String time2 = "65322";

    Location location = new Location(address, date, latitude, longitude, time);
    Location location2 = new Location(address2, date2, latitude2, longitude2, time2);

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test");
        locationDb = new LocationDb(context);
    }

    public void testLocationDbNotNull() {
        assertNotNull(locationDb);
    }

    public void testInsertLocation(){
        boolean status = locationDb.insertLocation(location);
        assertTrue(status);
    }

    public void testGetDateLocation() {
        locationDb.insertLocation(location);
        ArrayList<Location> locations = locationDb.getDateLocations(date);
        assertTrue(locations.size() > 0);
    }

    public void testGetAddressLocationHistory() {
        AddressHistory addressHistory = null;
        locationDb.insertLocation(location);
        addressHistory = locationDb.getAddressLocationsHistory(address);
        assertEquals(address, addressHistory.getAddress());
    }

    public void testGetDateLocationHistory() {
        DateHistory dateHistory = null;
        locationDb.insertLocation(location);
        dateHistory = locationDb.getDateLocationsHistory(date);
        assertEquals(date, dateHistory.getDate());
    }

    public void testGetUniqueLocationDateQueryArg() {
        ArrayList<String> dateList = null;
        locationDb.insertLocation(location);
        locationDb.insertLocation(location2);
        dateList = locationDb.getUniqueLocationDateQueryArg();
        assertTrue(dateList.size() == 1);
    }

    public void testGetUniqueLocationAddressQueryArg() {
        ArrayList<String> addressList = null;
        locationDb.insertLocation(location);
        locationDb.insertLocation(location2);
        addressList = locationDb.getUniqueLocationAddressQueryArg();
        assertTrue(addressList.size() > 1);
    }

    @Override
    public void tearDown() throws Exception {
        locationDb.close();
        super.tearDown();
    }
}
