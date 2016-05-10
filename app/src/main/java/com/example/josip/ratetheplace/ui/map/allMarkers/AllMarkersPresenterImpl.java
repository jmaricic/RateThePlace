package com.example.josip.ratetheplace.ui.map.allMarkers;

import com.example.josip.ratetheplace.interactor.otherHotels.LoadHotelsInteractorImpl;

/**
 * Created by Josip on 4.4.2016..
 */
public class AllMarkersPresenterImpl implements AllMarkersPresenter {

    private AllMarkersView allMarkersView;
    private LoadHotelsInteractorImpl interactor;

    public AllMarkersPresenterImpl(AllMarkersView allMarkersView) {
        this.allMarkersView = allMarkersView;
        this.interactor = new LoadHotelsInteractorImpl(this);
    }

    @Override
    public void getAllPlacedMarkers() {
        interactor.requestMarkews();
    }

    @Override
    public void onMarkersLoaded(String name, double lat, double lon,float averageRating, String review) {
        allMarkersView.onLoaded(name, lat, lon, averageRating, review);
    }

}
