package com.example.jianingliu.myweather;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.tiles.AerisTile;


public class MapFragment extends MapViewFragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String lat = bundle.getString("lat");
        String lng = bundle.getString("lng");
        Log.e("lat",lat);
        Log.e("lng",lng);

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (AerisMapView)view.findViewById(R.id.aerisfragment_map);
        LatLng loc = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));

        mapView.init(savedInstanceState, AerisMapView.AerisMapType.GOOGLE);
        mapView.moveToLocation(loc,9);
        mapView.addLayer(AerisTile.RADSAT);

        return view;
    }
}
