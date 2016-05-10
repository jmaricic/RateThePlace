package com.example.josip.ratetheplace.interactor.otherHotels;

import com.example.josip.ratetheplace.model.Hotel;
import com.example.josip.ratetheplace.ui.map.allMarkers.AllMarkersPresenter;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Josip on 4.4.2016..
 */
public class LoadHotelsInteractorImpl implements LoadHotelsInteractor {

    private Firebase firebase = new Firebase("https://myAppName.firebaseio.com/Users/");
    private final AllMarkersPresenter presenter;

    public LoadHotelsInteractorImpl(AllMarkersPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void requestMarkews() {

        final Firebase hotelsRef = new Firebase("https://myAppName.firebaseio.com/Users/");
        com.firebase.client.Query queryRef = hotelsRef.orderByChild("hotel");
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DataSnapshot hotelsSnapshot = dataSnapshot.child("hotel");
                for (DataSnapshot hotelSnapshot : hotelsSnapshot.getChildren()) {
                    Hotel hotel = hotelSnapshot.getValue(Hotel.class);
                    if (!dataSnapshot.getKey().equals(hotelsRef.getAuth().getUid())) {
                        presenter.onMarkersLoaded(hotel.getName(), hotel.getLatit(),
                                hotel.getLongit(), hotel.getRating(),
                                hotel.getReview());
                    } else {
                        break;
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
