package com.example.josip.ratetheplace.interactor.editHotel;

import java.util.Map;

/**
 * Created by Josip on 25.4.2016..
 */
public interface EditHotelInteractor {
    void editHotel(String newName, String name, double lat, double lon, String author, float foodRating, float serviceRating,
                   float comfortRating, float averageRating, String review);
    void deleteHotel(String name, double lat, double lon, String author, float foodRating, float serviceRating,
                     float comfortRating, float averageRating, String review);


    Map<String, Object> rateHotel(String name, double lat, double lon, String author, float foodRating,
                                  float serviceRating, float comfortRating, float averageRating, String review);
}
