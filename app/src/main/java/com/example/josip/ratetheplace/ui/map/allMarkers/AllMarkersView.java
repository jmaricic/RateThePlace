package com.example.josip.ratetheplace.ui.map.allMarkers;


/**
 * Created by Josip on 4.4.2016..
 */
public interface AllMarkersView {
    void onError();
    void onLoaded(String name, double lat, double lon, float averageRating, String review);
}
