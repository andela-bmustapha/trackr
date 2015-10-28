package com.bmustapha.trackr;

import android.test.AndroidTestCase;
import android.view.View;
import android.widget.TextView;

import com.bmustapha.trackr.adapters.DateHistoryAdapter;
import com.bmustapha.trackr.models.DateHistory;

import java.util.ArrayList;

/**
 * Created by tunde on 10/22/15.
 */
public class DateHistoryActivityAdapterTest extends AndroidTestCase {

    private DateHistoryAdapter dateHistoryAdapter;

    private DateHistory firstHistory;
    private DateHistory secondHistory;

    public DateHistoryActivityAdapterTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();

        ArrayList<DateHistory> data = new ArrayList<>();

        firstHistory = new DateHistory(15, "15-12-2015");
        secondHistory = new DateHistory(6, "10-02-2015");

        data.add(firstHistory);
        data.add(secondHistory);

        dateHistoryAdapter = new DateHistoryAdapter(getContext(), data);
    }


    public void testGetItem() {
        assertEquals("15-12-2015 is expected", firstHistory.getDate(), dateHistoryAdapter.getItem(0).getDate());
    }

    public void testGetItemId() {
        assertEquals("Wrong count", 0, dateHistoryAdapter.getItemId(0));
    }

    public void testGetCount() {
        assertEquals("Date HistoryActivity count incorrect.", 2, dateHistoryAdapter.getCount());
    }


    public void testGetView() {
        View view = dateHistoryAdapter.getView(0, null, null);

        TextView date = (TextView) view.findViewById(R.id.date);
        TextView count = (TextView) view.findViewById(R.id.location_count);

        assertNotNull("View is null. ", view);
        assertNotNull("Date TextView is null. ", date);
        assertNotNull("Count TextView is null. ", count);

        assertEquals("Date doesn't match.", firstHistory.getDate(), date.getText().toString());
        assertNotSame("Count doesn't match.", String.valueOf(firstHistory.getCount()), count.getText().toString());
    }
}
