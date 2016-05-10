package com.example.josip.ratetheplace.ui.map.myHotels.editMyHotel;

/**
 * Created by Josip on 25.4.2016..
 */
public interface EditHotelPresenter {
    void editHotel(String newName, String name, double lat, double lon, String author, float foodRating, float serviceRating,
                   float comfortRating, float averageRating, String review);

    void deleteHotel(String name, double lat, double lon, String author, float foodRating, float serviceRating,
                     float comfortRating, float averageRating, String review);

    void hotelEdited();
    void hotelDeleted();
}
