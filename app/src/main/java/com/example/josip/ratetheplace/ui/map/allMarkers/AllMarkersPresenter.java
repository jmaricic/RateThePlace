package com.example.josip.ratetheplace.ui.map.allMarkers;


/**
 * Created by Josip on 4.4.2016..
 */
public interface AllMarkersPresenter {
    void getAllPlacedMarkers();
    void onMarkersLoaded(String name, double lat, double lon, float averageRating, String review);
}
