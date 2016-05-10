package com.example.josip.ratetheplace.interactor.registration;

import com.example.josip.ratetheplace.model.User;
import com.example.josip.ratetheplace.ui.register.RegisterPresenter;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josip on 21.3.2016..
 */
public class RegisterUserInteractorImpl implements RegisterUserInteractor {

    private Firebase userRef = new Firebase("https://josip-my-application.firebaseio.com/Users/");
    private final RegisterPresenter presenter;

    public RegisterUserInteractorImpl(RegisterPresenter pre) {
        this.presenter = pre;
    }

    @Override
    public void tryToRegister(final String username, final String email, final String password) {
        userRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                String uid = stringObjectMap.get("uid").toString();
                userRef = new Firebase("https://josip-my-application.firebaseio.com/Users/" + uid);
                userRef.setValue(createUser(username, email, password));
                presenter.onSuccess(email, username);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                presenter.onFailure();
            }
        });
    }

    @Override
    public Map<String, Object> createUser(String username, String email, String password) {
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);
        user.put("password", password);
        return user;
    }

    @Override
    public void checkIfUserExists(final String username, final String email, final String password) {
        Firebase userRef = new Firebase("https://josip-my-application.firebaseio.com/Users/");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean exists = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    User user = snapshot.getValue(User.class);
                    if (user.getUsername().equals(username) || user.getEmail().equals(email)) {
                        exists = true;
                        break;
                    } else {
                        exists = false;
                    }
                }

                    if (exists) {
                        presenter.onUserAlreadyExists();
                    } else {
                        presenter.onUserDoesNotExist(username, email, password);
                    }
                }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
}

