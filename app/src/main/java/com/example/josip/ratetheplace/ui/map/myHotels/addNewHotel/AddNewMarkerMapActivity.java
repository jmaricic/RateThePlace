package com.example.josip.ratetheplace.ui.map.myHotels.addNewHotel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.josip.ratetheplace.R;
import com.example.josip.ratetheplace.ui.base.BaseActivity;
import com.example.josip.ratetheplace.ui.list.ListActivity;
import com.firebase.client.Firebase;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Josip on 16.4.2016..
 */
public class AddNewMarkerMapActivity extends BaseActivity implements AddNewMarkerView, GoogleMap.OnMapClickListener,OnMapReadyCallback {

    @Bind(R.id.app_bar)
    Toolbar mToolbar;

    private Firebase firebase = new Firebase("https://myAppName.firebaseio.com/Users/");
    private GoogleMap mGoogleMap;
    private Marker marker;
    private AddNewMarkerPresenterImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_marker_map);
        initUi();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new AddNewMarkerPresenterImpl(this);
    }

    @Override
    public void initUi() {
        ButterKnife.bind(this);
        setToolbar();
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setToolbar() {
        mToolbar.setTitle("Welcome " + getIntent().getStringExtra("username"));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(AddNewMarkerMapActivity.this, ListActivity.class);
                    i.putExtra("username", getIntent().getStringExtra("username"));
                    startActivity(i);
                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMapClickListener(this);
        setupMap();
    }


    private void setupMap() {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onMapClick(final LatLng latLng) {
        final double lat = latLng.latitude;
        final double lon = latLng.longitude;
        LayoutInflater inflater = LayoutInflater.from(AddNewMarkerMapActivity.this);
        @SuppressLint("InflateParams") View contentView = inflater.inflate(R.layout.dialog_edit, null);
        final EditText hotelName = (EditText) contentView.findViewById(R.id.hotel_name_edittext);
        final RatingBar ratingBarFood = (RatingBar) contentView.findViewById(R.id.hotel_ratingbar_food);
        final RatingBar ratingBarService = (RatingBar) contentView.findViewById(R.id.hotel_ratingbar_service);
        final RatingBar ratingBarComfort = (RatingBar) contentView.findViewById(R.id.hotel_ratingbar_comfort);
        final EditText comment = (EditText) contentView.findViewById(R.id.comment);

        final Dialog dialog = new AlertDialog.Builder(AddNewMarkerMapActivity.this)
                .setTitle("Add new marker")
                .setView(contentView)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        float averageRating = ((ratingBarComfort.getRating() + ratingBarFood.getRating() + ratingBarService.getRating()) / 3);

                        confirmDialog(hotelName.getText().toString(), lat, lon,
                                getIntent().getStringExtra("username"), ratingBarFood.getRating(),
                                ratingBarService.getRating(), ratingBarComfort.getRating(),
                                averageRating, comment.getText().toString());

                    }
                })
                .create();
        dialog.show();
    }

    @Override
    public void onSuccess(String name, double lat, double lon, String author) {
        LatLng position = new LatLng(lat, lon);
        marker = mGoogleMap.addMarker(new MarkerOptions().title(name).position(position).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));

        Intent i = new Intent(this, ListActivity.class);
        i.putExtra("username", getIntent().getStringExtra("username"));
        startActivity(i);
    }

    private void confirmDialog(final String name, final double lat, final double lon, final String username, final float food, final float service, final float comfort, final float average, final String review) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to place marker here?");
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.placeMarker(name, lat, lon, username, food,service, comfort, average, review);
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
