package com.example.josip.ratetheplace.interactor.editHotel;


import com.example.josip.ratetheplace.ui.map.myHotels.editMyHotel.EditHotelPresenter;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josip on 25.4.2016..
 */
public class EditHotelInteractorImpl implements EditHotelInteractor {

    private Firebase firebase = new Firebase("https://josip-my-application.firebaseio.com/Users/");
    private EditHotelPresenter presenter;

    public EditHotelInteractorImpl(EditHotelPresenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void editHotel(String newName, String name, double lat, double lon, String author, float foodRating, float serviceRating, float comfortRating, float averageRating, String review) {
        String uid = firebase.getAuth().getUid();
        final Firebase hotelsRef = new Firebase("https://josip-my-application.firebaseio.com/Users/" + uid + "/hotel/").child(name);

        if(!name.equals(newName)) {
            hotelsRef.removeValue();

            final Firebase hotelsRefEdited = new Firebase("https://josip-my-application.firebaseio.com/Users/" + uid + "/hotel/").child(newName);

            hotelsRefEdited.setValue(rateHotel(newName,
                    lat, lon, author, foodRating, serviceRating,
                    comfortRating, averageRating, review));

        } else {
            hotelsRef.setValue(rateHotel(newName,
                    lat, lon, author, foodRating, serviceRating,
                    comfortRating, averageRating, review));
        }

        presenter.hotelEdited();

    }

    @Override
    public void deleteHotel(String name, double lat, double lon, String author, float foodRating, float serviceRating, float comfortRating, float averageRating, String review) {
        String uid = firebase.getAuth().getUid();
        final Firebase hotelsRef = new Firebase("https://josip-my-application.firebaseio.com/Users/" + uid + "/hotel/").child(name);
        hotelsRef.removeValue();

        presenter.hotelDeleted();
    }

    @Override
    public Map<String, Object> rateHotel(String name, double lat, double lon, String author, float foodRating, float serviceRating, float comfortRating, float averageRating, String review) {
        Map<String, Object> hotel = new HashMap<>();
        hotel.put("name", name);
        hotel.put("latit", lat);
        hotel.put("longit", lon);
        hotel.put("author", author);
        hotel.put("food", foodRating);
        hotel.put("service", serviceRating);
        hotel.put("comfort", comfortRating);
        hotel.put("rating", averageRating);
        hotel.put("review", review);
        return hotel;
    }
}
