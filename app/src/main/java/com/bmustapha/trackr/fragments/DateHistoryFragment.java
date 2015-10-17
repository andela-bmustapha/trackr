package com.bmustapha.trackr.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bmustapha.trackr.R;
import com.bmustapha.trackr.adapters.DateHistoryAdapter;
import com.bmustapha.trackr.db.LocationDb;
import com.bmustapha.trackr.models.DateHistory;
import com.bmustapha.trackr.models.Location;

import java.util.ArrayList;

/**
 * Created by andela on 10/17/15.
 */
public class DateHistoryFragment extends Fragment {

    private ArrayList<DateHistory> dateHistories;
    private LocationDb locationDb;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.date_history_list, container, false);

        locationDb = new LocationDb(getActivity());

        ListView dateHistoryListView = (ListView) view.findViewById(R.id.date_history_list_view);

        getAllDateHistory();

        DateHistoryAdapter dateHistoryAdapter = new DateHistoryAdapter(getActivity(), dateHistories);
        dateHistoryListView.setAdapter(dateHistoryAdapter);

        dateHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // open up map activity to display the locations of the day

            }
        });

        return view;
    }

    private void getAllDateHistory() {
        dateHistories = new ArrayList<>();
        ArrayList<String> uniqueDates = locationDb.getUniqueLocationQueryArg();
        ArrayList<Location> locations = locationDb.getAllLocations();
        for (String date : uniqueDates) {
            int count = 0;
            for (Location location : locations) {
                if (location.getDate().equals(date)) {
                    count += 1;
                }
            }
            dateHistories.add(new DateHistory(count, date));
        }
    }
}
