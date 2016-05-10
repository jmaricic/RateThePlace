package com.example.josip.ratetheplace.ui.map.myHotels.addNewHotel;

/**
 * Created by Josip on 16.4.2016..
 */
public interface AddNewMarkerPresenter {
    void placeMarker(String name, double lat, double lon, String author, float foodRating,
                     float serviceRating,float comfortRating,float averageRating, String review);

    void onSuccess(String name, double lat, double lon, String author);
}
