package com.bmustapha.trackr.activities;

import android.graphics.Color;
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
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class HistoryMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_map);

        // get the arraylist from the intent
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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (locations != null) {
            LatLng latLng = null;
            PolylineOptions line = new PolylineOptions();
            // go through the list and create markers
            for (Location location : locations) {
                latLng = new LatLng(Double.parseDouble(location.getLatitude()), Double.parseDouble(location.getLongitude()));
                mMap.addMarker(new MarkerOptions().position(latLng).title(location.getAddress()));
                line.add(latLng);
            }
            line.width(3).color(Color.RED);
            CameraUpdate updateFactory = CameraUpdateFactory.newLatLngZoom(latLng, 16);
            mMap.addPolyline(line);
            mMap.moveCamera(updateFactory);
            mMap.animateCamera(updateFactory);
        }
    }
}
