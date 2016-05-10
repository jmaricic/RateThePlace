package com.example.josip.ratetheplace.ui.map.myHotels.addNewHotel;

import com.example.josip.ratetheplace.interactor.addHotel.AddHotelInteractorImpl;

/**
 * Created by Josip on 16.4.2016..
 */
public class AddNewMarkerPresenterImpl implements AddNewMarkerPresenter {

    private AddNewMarkerView view;
    private AddHotelInteractorImpl interactor;

    public AddNewMarkerPresenterImpl(AddNewMarkerView view) {
        this.view = view;
        this.interactor = new AddHotelInteractorImpl(this);
    }

    @Override
    public void placeMarker(String name, double lat, double lon, String author, float foodRating, float serviceRating, float comfortRating, float averageRating, String review) {
        interactor.addHotel(name, lat, lon, author, foodRating,serviceRating,comfortRating,averageRating, review);

    }

    @Override
    public void onSuccess(String name, double lat, double lon, String author) {
        view.onSuccess(name, lat, lon, author);

    }
}
