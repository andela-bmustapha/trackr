package com.bmustapha.trackr;

import android.test.AndroidTestCase;
import android.view.View;
import android.widget.TextView;

import com.bmustapha.trackr.adapters.AddressHistoryAdapter;
import com.bmustapha.trackr.models.AddressHistory;

import java.util.ArrayList;

/**
 * Created by andela on 10/22/15.
 */
public class AddressHistoryAdapterTest extends AndroidTestCase {

    private AddressHistoryAdapter addressHistoryAdapter;

    private AddressHistory firstHistory;
    private AddressHistory secondHistory;

    public AddressHistoryAdapterTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();

        ArrayList<AddressHistory> data = new ArrayList<>();

        firstHistory = new AddressHistory(10, "16, Alara Street");
        firstHistory.setTime("78");
        secondHistory = new AddressHistory(16, "3/4, Funsho Street");
        secondHistory.setTime("21");

        data.add(firstHistory);
        data.add(secondHistory);

        addressHistoryAdapter = new AddressHistoryAdapter(getContext(), data);
    }


    public void testGetItem() {
        assertEquals("16, Alara Street is expected", firstHistory.getAddress(), addressHistoryAdapter.getItem(0).getAddress());
    }

    public void testGetItemId() {
        assertEquals("Wrong count", 0, addressHistoryAdapter.getItemId(0));
    }

    public void testGetCount() {
        assertEquals("Address History count incorrect.", 2, addressHistoryAdapter.getCount());
    }


    public void testGetView() {
        View view = addressHistoryAdapter.getView(0, null, null);

        TextView address = (TextView) view.findViewById(R.id.address);
        TextView addressCount = (TextView) view.findViewById(R.id.address_count);
        TextView timeSpent = (TextView) view.findViewById(R.id.time_spent);

        assertNotNull("View is null. ", view);
        assertNotNull("Address TextView is null. ", address);
        assertNotNull("Address Count TextView is null. ", addressCount);
        assertNotNull("Time Spent TextView not null ", timeSpent);

        assertEquals("Address doesn't match.", firstHistory.getAddress(), address.getText().toString());
        assertNotSame("Time Spent doesn't match.", firstHistory.getTime(), timeSpent.getText().toString());
    }
}
