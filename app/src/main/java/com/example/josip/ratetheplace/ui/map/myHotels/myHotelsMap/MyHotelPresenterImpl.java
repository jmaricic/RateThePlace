package com.example.josip.ratetheplace.ui.map.myHotels.myHotelsMap;

import com.example.josip.ratetheplace.interactor.myHotels.LoadMyHotelsInteractorImpl;

/**
 * Created by Josip on 18.4.2016..
 */
public class MyHotelPresenterImpl implements MyHotelsPresenter {

    private MyHotelsView view;
    private LoadMyHotelsInteractorImpl interactor;

    public MyHotelPresenterImpl(MyHotelsView view) {
        this.view = view;
        this.interactor = new LoadMyHotelsInteractorImpl(this);
    }

    @Override
    public void getMyPlacedMarkers() {
        interactor.requestMarkews();
    }

    @Override
    public void onMarkersLoaded(String name, double lat, double lon, float averageRating, String review) {
        view.onLoaded(name, lat, lon, averageRating, review);
    }

}