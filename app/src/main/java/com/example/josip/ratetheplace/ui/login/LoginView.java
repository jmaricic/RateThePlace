package com.example.josip.ratetheplace.ui.login;

/**
 * Created by Josip on 1.3.2016..
 */
public interface LoginView {
    void onSuccess(String username);
    void onError();
    void goToSignUpScrn();
}
