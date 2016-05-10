package com.example.josip.ratetheplace.interactor.addHotel;
import com.example.josip.ratetheplace.ui.map.myHotels.addNewHotel.AddNewMarkerPresenter;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josip on 18.4.2016..
 */
public class AddHotelInteractorImpl implements AddHotelInteractor {
    private Firebase firebase = new Firebase("https://myAppName.firebaseio.com/Users/");
    private final AddNewMarkerPresenter presenter;

    public AddHotelInteractorImpl(AddNewMarkerPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Map<String, Object> rateHotel(String name, double lat, double lon, String author, float foodRating,
                                         float serviceRating, float comfortRating, float averageRating, String review) {

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

    @Override
    public void addHotel(String name, double lat, double lon, String author,float foodRating,
                         float serviceRating,float comfortRating,float averageRating, String review) {
        String uid = firebase.getAuth().getUid();
        final Firebase hotelsRef = new Firebase("https://myAppName.firebaseio.com/Users/" + uid + "/hotel/").child(name);
        hotelsRef.setValue(rateHotel(name, lat, lon, author, foodRating, serviceRating, comfortRating, averageRating, review));
        presenter.onSuccess(name, lat, lon, author);
    }
}
