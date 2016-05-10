package com.example.josip.ratetheplace.interactor.login;

import com.example.josip.ratetheplace.model.User;
import com.example.josip.ratetheplace.ui.login.LoginPresenter;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Josip on 24.3.2016..
 */
public class LogInUserInteractorImpl implements LogInUserInteractor {


    private Firebase firebase = new Firebase("https://josip-my-application.firebaseio.com/Users/");
    private final LoginPresenter presenter;


    public LogInUserInteractorImpl(LoginPresenter pre) {
        this.presenter = pre;
    }

    @Override
    public void tryToLogin(final String email, final String password) {
        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {

            @Override
            public void onAuthenticated(final AuthData authData) {
                Firebase firebase = new Firebase("https://josip-my-application.firebaseio.com/Users/" + authData.getUid());
                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if(user != null) {
                            presenter.success(user.getUsername());
                        } else {
                            presenter.fail();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                    presenter.fail();
            }
        });
    }
}
