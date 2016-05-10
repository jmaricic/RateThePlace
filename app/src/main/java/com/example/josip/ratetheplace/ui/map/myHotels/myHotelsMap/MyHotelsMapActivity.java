package com.example.josip.ratetheplace.ui.map.myHotels.myHotelsMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.josip.ratetheplace.R;
import com.example.josip.ratetheplace.ui.base.BaseActivity;
import com.example.josip.ratetheplace.ui.list.ListActivity;
import com.firebase.client.Firebase;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Josip on 18.4.2016..
 */
public class MyHotelsMapActivity extends BaseActivity implements MyHotelsView,OnMapReadyCallback, GoogleMap.InfoWindowAdapter, GoogleMap.OnMarkerClickListener {

    @Bind(R.id.app_bar)
    Toolbar toolbar;
    private Firebase firebase = new Firebase("https://myAppName.firebaseio.com/Users/");
    private GoogleMap mGoogleMap;
    private Marker marker;
    private MyHotelPresenterImpl presenter;

    private Map<String, String> nameMap = new HashMap<>();
    private Map<String, String> reviewMap = new HashMap<>();
    private Map<String, Float> averageMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_markers_map);
        initUi();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new MyHotelPresenterImpl(this);
    }

    @Override
    public void initUi() {
        ButterKnife.bind(this);
        toolbar.setTitle("Welcome " + getIntent().getStringExtra("username"));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyHotelsMapActivity.this, ListActivity.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setInfoWindowAdapter(this);
        setupMap();
    }


    private void setupMap() {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        presenter.getMyPlacedMarkers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.hibrid:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.normal:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.satelite:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
        }
        return true;
    }


    @Override
    public void onLoaded(String name, double lat, double lon,float averageRating, String review) {

        LatLng position = new LatLng(lat, lon);
        marker = mGoogleMap.addMarker(new MarkerOptions().position(position)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));

        nameMap.put(marker.getId(), name);
        reviewMap.put(marker.getId(), review);
        averageMap.put(marker.getId(), averageRating);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.info_window_detais, null, false);

        TextView hotelName = (TextView) view.findViewById(R.id.hotelNameTv);
        RatingBar averageRating = (RatingBar) view.findViewById(R.id.average);
        TextView userReview = (TextView) view.findViewById(R.id.reviewTekst);

        String name = nameMap.get(marker.getId());
        float average = averageMap.get(marker.getId());
        String review = reviewMap.get(marker.getId());

        hotelName.setText(name);
        averageRating.setRating(average);
        userReview.setText(review);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 14);
        mGoogleMap.animateCamera(cameraUpdate);
        marker.showInfoWindow();
        return true;
    }

}
