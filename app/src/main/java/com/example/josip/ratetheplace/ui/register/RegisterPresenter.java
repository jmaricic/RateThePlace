package com.example.josip.ratetheplace.ui.register;

/**
 * Created by Josip on 21.3.2016..
 */
public interface RegisterPresenter {
    void registerNewUser(String username, String email, String password);

    void onFailure();

    void onSuccess(String email, String username);

    void onUserAlreadyExists();

    void onUserDoesNotExist(String username, String email, String password);
}
