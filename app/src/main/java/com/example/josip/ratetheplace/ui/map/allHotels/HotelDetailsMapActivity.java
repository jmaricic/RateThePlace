package com.example.josip.ratetheplace.ui.map.allHotels;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.josip.ratetheplace.R;
import com.example.josip.ratetheplace.ui.base.BaseActivity;
import com.example.josip.ratetheplace.ui.list.ListActivity;
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
 * Created by Josip on 13.4.2016..
 */
public class HotelDetailsMapActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.InfoWindowAdapter {

    @Bind(R.id.author_name)
    TextView mAuthorName;

    @Bind(R.id.reviewTv)
    TextView mComment;

    @Bind(R.id.food_rating)
    RatingBar mFoodRating;

    @Bind(R.id.service_rating)
    RatingBar mServiceRating;

    @Bind(R.id.comfort_rating)
    RatingBar mComfortRating;

    @Bind(R.id.app_bar)
    Toolbar mToolbar;

    private GoogleMap mGoogleMap;

    private Marker marker;

    private Map<String, String> nameMap = new HashMap<>();
    private Map<String, Float> averageMap = new HashMap<>();
    private Map<String, String> reviewMap = new HashMap<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details_map);
        initUi();
    }

    @Override
    public void initUi() {
        ButterKnife.bind(this);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setToolbar();
        setHotelRatings();

    }

    private void setToolbar() {

        mToolbar.setTitle(getIntent().getStringExtra("name"));

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(HotelDetailsMapActivity.this, ListActivity.class);
                    i.putExtra("username", getIntent().getStringExtra("author"));
                    startActivity(i);
                }
            });
        }
    }

    private void setHotelRatings() {
        mAuthorName.setText(getIntent().getStringExtra("author"));

        mFoodRating.setRating(Float.valueOf(getIntent().getStringExtra("food")));
        mServiceRating.setRating(Float.valueOf(getIntent().getStringExtra("service")));
        mComfortRating.setRating(Float.valueOf(getIntent().getStringExtra("comfort")));

        mComment.setText(getIntent().getStringExtra("review"));
        mComment.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        setupMap();
    }

    private void setupMap() {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setInfoWindowAdapter(this);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

        double lat = Double.valueOf(getIntent().getStringExtra("latit"));
        double lon = Double.valueOf(getIntent().getStringExtra("longit"));
        LatLng hotelLatLng = new LatLng(lat, lon);

        marker = mGoogleMap.addMarker(new MarkerOptions().title(getIntent().getStringExtra("name"))
                .position(hotelLatLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(hotelLatLng, 6);
        mGoogleMap.animateCamera(cameraUpdate);

        nameMap.put(marker.getId(), getIntent().getStringExtra("name"));
        averageMap.put(marker.getId(), Float.valueOf(getIntent().getStringExtra("rating")));
        reviewMap.put(marker.getId(), getIntent().getStringExtra("review"));

    }

    @Override
    public boolean onMarkerClick(final Marker mark) {
        marker.showInfoWindow();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mark.getPosition(), 14);
        mGoogleMap.animateCamera(cameraUpdate);
        return true;
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
}
