package com.example.josip.ratetheplace.ui.register;

/**
 * Created by Josip on 21.3.2016..
 */
public interface RegisterUserView {
    void userAvailable();
    void userTaken();
    void onSuccess(String email, String username);
    void onFailure();
}
