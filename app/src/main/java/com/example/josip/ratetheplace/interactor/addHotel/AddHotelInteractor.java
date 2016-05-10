package com.example.josip.ratetheplace.interactor.addHotel;

import java.util.Map;

/**
 * Created by Josip on 18.4.2016..
 */
public interface AddHotelInteractor {
    Map<String, Object> rateHotel(String name, double lat, double lon, String author, float foodRating,
                                  float serviceRating, float comfortRating, float averageRating, String review);

    void addHotel(String name, double lat, double lon, String author, float foodRating, float serviceRating,
                  float comfortRating, float averageRating, String review);
}
