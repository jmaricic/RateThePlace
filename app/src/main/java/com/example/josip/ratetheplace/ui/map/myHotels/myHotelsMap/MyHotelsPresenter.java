package com.example.josip.ratetheplace.ui.map.myHotels.myHotelsMap;

/**
 * Created by Josip on 18.4.2016..
 */
public interface MyHotelsPresenter {
    void getMyPlacedMarkers();
    void onMarkersLoaded(String name, double lat, double lon,float averageRating, String review);

}
