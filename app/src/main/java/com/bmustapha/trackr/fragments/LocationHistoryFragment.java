package com.bmustapha.trackr.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bmustapha.trackr.R;
import com.bmustapha.trackr.activities.HistoryMap;
import com.bmustapha.trackr.adapters.AddressHistoryAdapter;
import com.bmustapha.trackr.db.LocationDb;
import com.bmustapha.trackr.models.AddressHistory;
import com.bmustapha.trackr.models.Location;

import java.util.ArrayList;

/**
 * Created by tunde on 10/20/15.
 */
public class LocationHistoryFragment extends Fragment {

    private LocationDb locationDb;
    private ArrayList<AddressHistory> addressHistories;
    private ArrayList<Location> locations;

    @Override
    public void onStart() {
        super.onStart();
        addressHistories = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.date_history_list, container, false);

        locationDb = new LocationDb(getActivity());

        ListView addressHistoryListView = (ListView) view.findViewById(R.id.date_history_list_view);

        getAllAddressHistory();

        final AddressHistoryAdapter addressHistoryAdapter = new AddressHistoryAdapter(getActivity(), addressHistories);
        addressHistoryListView.setAdapter(addressHistoryAdapter);

        addressHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // open up map activity to display the locations of the day
                // fetch data first and pass it with intent
                AddressHistory addressHistory = addressHistoryAdapter.getItem(position);
                Intent mappedHistoryIntent = new Intent(getActivity(), HistoryMap.class);
                mappedHistoryIntent.putExtra("HISTORY_LIST", getAddressLocation(addressHistory.getAddress()));
                startActivity(mappedHistoryIntent);
            }
        });


        return view;
    }

    private ArrayList<Location> getAddressLocation(String address) {
        return locationDb.getAddressLocation(address);
    }


    private void getAllAddressHistory() {
        addressHistories = new ArrayList<>();
        ArrayList<String> uniqueAddresses = locationDb.getUniqueLocationAddressQueryArg();
        for (String address : uniqueAddresses) {
            addressHistories.add(locationDb.getAddressLocationsHistory(address));
        }
    }
}
