package com.example.jianingliu.myweather;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.communication.loaders.ObservationsTask;
import com.hamweather.aeris.communication.loaders.ObservationsTaskCallback;
import com.hamweather.aeris.communication.parameter.PlaceParameter;
import com.hamweather.aeris.model.AerisError;

import java.util.List;

/**
 * Created by JianingLiu on 11/18/15.
 */
public class MapActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        Intent intent = getIntent();

        String lat = intent.getStringExtra(ResultActivity.LAT);
        String lng = intent.getStringExtra(ResultActivity.LNG);
        Log.e("act",lat);

        AerisEngine.initWithKeys(this.getString(R.string.aeris_client_id), this.getString(R.string.aeris_client_secret), this);
        Bundle bundle = new Bundle();
        bundle.putString("lat",lat);
        bundle.putString("lng",lng);
        MapFragment fragment = new MapFragment();
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.map_fragment_container, fragment);
        fragmentTransaction.commit();


        }


}
