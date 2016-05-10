package com.example.josip.ratetheplace.ui.map.myHotels.editMyHotel;

import com.example.josip.ratetheplace.interactor.editHotel.EditHotelInteractorImpl;

/**
 * Created by Josip on 25.4.2016..
 */
public class EditHotelPresenterImpl implements EditHotelPresenter {

    private EditHotelView view;
    private EditHotelInteractorImpl interactor;

    public EditHotelPresenterImpl(EditHotelView view) {
        this.view = view;
        this.interactor = new EditHotelInteractorImpl(this);
    }


    @Override
    public void editHotel(String newName, String name, double lat, double lon, String author,
                          float foodRating, float serviceRating, float comfortRating, float averageRating, String review) {
        interactor.editHotel(newName, name, lat, lon, author, foodRating, serviceRating, comfortRating, averageRating, review);
    }

    @Override
    public void deleteHotel(String name, double lat, double lon, String author,
                            float foodRating, float serviceRating, float comfortRating, float averageRating, String review) {
        interactor.deleteHotel(name, lat, lon, author, foodRating, serviceRating, comfortRating, averageRating, review);
    }

    @Override
    public void hotelEdited() {
        view.hotelEdited();
    }

    @Override
    public void hotelDeleted() {
        view.hotelDeleted();
    }
}
