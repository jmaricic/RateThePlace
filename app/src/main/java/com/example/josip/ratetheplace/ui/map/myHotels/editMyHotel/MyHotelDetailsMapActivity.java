package com.example.josip.ratetheplace.ui.map.myHotels.editMyHotel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Josip on 24.2.2016..
 */
public class MyHotelDetailsMapActivity extends BaseActivity implements EditHotelView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener {

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
    private String autName;
    private double latEnd;
    private double lonEnd;
    private double latSt;
    private double lonSt;

    private EditHotelPresenterImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_hotel_details_map);
        initUi();
        autName = getIntent().getStringExtra("author");
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new EditHotelPresenterImpl(this);
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
                    Intent i = new Intent(MyHotelDetailsMapActivity.this, ListActivity.class);
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
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMarkerDragListener(this);
        setupMap();
    }

    private void setupMap() {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

        double lat = Double.valueOf(getIntent().getStringExtra("latit"));
        double lon = Double.valueOf(getIntent().getStringExtra("longit"));
        LatLng hotelLatLng = new LatLng(lat, lon);
        marker = mGoogleMap.addMarker(new MarkerOptions().position(hotelLatLng).draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(hotelLatLng, 6);
        mGoogleMap.animateCamera(cameraUpdate);

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
    public boolean onMarkerClick(final Marker marker) {
        final double lat = Double.valueOf(getIntent().getStringExtra("latit"));
        final double lon = Double.valueOf(getIntent().getStringExtra("longit"));

        LayoutInflater inflater = LayoutInflater.from(MyHotelDetailsMapActivity.this);
        @SuppressLint("InflateParams") View contentView = inflater.inflate(R.layout.dialog_edit, null);
        final EditText hotelName = (EditText) contentView.findViewById(R.id.hotel_name_edittext);
        final RatingBar ratingBarFood = (RatingBar) contentView.findViewById(R.id.hotel_ratingbar_food);
        final RatingBar ratingBarService = (RatingBar) contentView.findViewById(R.id.hotel_ratingbar_service);
        final RatingBar ratingBarComfort = (RatingBar) contentView.findViewById(R.id.hotel_ratingbar_comfort);
        final EditText comment = (EditText) contentView.findViewById(R.id.comment);

        hotelName.setText(getIntent().getStringExtra("name"));
        ratingBarFood.setRating(Float.valueOf(getIntent().getStringExtra("food")));
        ratingBarService.setRating(Float.valueOf(getIntent().getStringExtra("service")));
        ratingBarComfort.setRating(Float.valueOf(getIntent().getStringExtra("comfort")));
        comment.setText(getIntent().getStringExtra("review"));

        final Dialog dialog = new AlertDialog.Builder(MyHotelDetailsMapActivity.this)
                .setTitle("Edit hotel")
                .setView(contentView)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mToolbar.setTitle(hotelName.getText().toString());
                        mFoodRating.setRating(ratingBarFood.getRating());
                        mServiceRating.setRating(ratingBarService.getRating());
                        mComfortRating.setRating(ratingBarComfort.getRating());
                        mComment.setText(comment.getText().toString());

                        confirmSaveDialog(hotelName, ratingBarComfort, ratingBarFood, ratingBarService, lat, lon,
                                comment);

                    }
                })
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmDeleteDialog();
                    }
                })
                .create();

        dialog.show();
        return true;
    }

    private void confirmSaveDialog(final EditText name, final RatingBar ratingComfort, final RatingBar ratingFood,
                                   final RatingBar ratingService, final double lat, final double lon,
                                   final EditText comment) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you sure?");
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                float averageRating = ((ratingComfort.getRating() + ratingFood.getRating() + ratingService.getRating()) / 3);
                String hotelUid = getIntent().getStringExtra("name");
                autName = getIntent().getStringExtra("author");

                presenter.editHotel(name.getText().toString(), hotelUid, lat, lon, autName, ratingFood.getRating(),
                            ratingService.getRating(), ratingComfort.getRating(), averageRating, comment.getText().toString());
                
                if(latSt == latEnd && lonSt == lonEnd) {
                    presenter.editHotel(name.getText().toString(), hotelUid, lat, lon, autName, ratingFood.getRating(),
                            ratingService.getRating(), ratingComfort.getRating(), averageRating, comment.getText().toString());
                } else {
                    presenter.editHotel(name.getText().toString(), hotelUid, latEnd, lonEnd, autName, ratingFood.getRating(),
                            ratingService.getRating(), ratingComfort.getRating(), averageRating, comment.getText().toString());
                }


                dialog.dismiss();

            }
        });
        builder.show();
    }


    private void confirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to delete this hotel?");
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String hotelUid = getIntent().getStringExtra("name");
                double latitude = Double.parseDouble(getIntent().getStringExtra("latit"));
                double longitude = Double.parseDouble(getIntent().getStringExtra("longit"));
                float food = Float.parseFloat(getIntent().getStringExtra("food"));
                float service = Float.parseFloat(getIntent().getStringExtra("service"));
                float comfort = Float.parseFloat(getIntent().getStringExtra("comfort"));
                float average = Float.parseFloat(getIntent().getStringExtra("rating"));
                String comment = getIntent().getStringExtra("review");

                presenter.deleteHotel(hotelUid, latitude, longitude, autName, food, service, comfort, average, comment);
                dialog.dismiss();

            }
        });
        builder.show();
    }

    @Override
    public void hotelEdited() {
        Intent i = new Intent(MyHotelDetailsMapActivity.this, ListActivity.class);
        i.putExtra("username", getIntent().getStringExtra("author"));
        startActivity(i);
    }

    @Override
    public void hotelDeleted() {
        Intent i = new Intent(MyHotelDetailsMapActivity.this, ListActivity.class);
        i.putExtra("username", getIntent().getStringExtra("author"));
        startActivity(i);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        LatLng dragPosition = marker.getPosition();
        double dragLat = dragPosition.latitude;
        double dragLong = dragPosition.longitude;

        latSt = dragLat;
        lonSt = dragLong;
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng dragPosition = marker.getPosition();
        double dragLat = dragPosition.latitude;
        double dragLong = dragPosition.longitude;

        latEnd = dragLat;
        lonEnd = dragLong;
    }
}
