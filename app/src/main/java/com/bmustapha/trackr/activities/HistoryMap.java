package com.bmustapha.trackr.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.bmustapha.trackr.R;
import com.bmustapha.trackr.models.Location;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class HistoryMap extends FragmentActivity implements OnMapReadyCallback {

    private ArrayList<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_map);

        // get the arrayList from the intent
        try {
            locations = (ArrayList<Location>) getIntent().getSerializableExtra("HISTORY_LIST");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng latLng = null;
        for (Location location : locations) {
            latLng = new LatLng(Double.parseDouble(location.getLatitude()), Double.parseDouble(location.getLongitude()));
            String address = (location.getAddress() != null) ? location.getAddress() : "";
            googleMap.addMarker(new MarkerOptions().position(latLng).title(address));
        }
        CameraUpdate updateFactory = CameraUpdateFactory.newLatLngZoom(latLng, 16);
        googleMap.moveCamera(updateFactory);
        googleMap.animateCamera(updateFactory);
    }
}
