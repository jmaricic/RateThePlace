package com.example.josip.ratetheplace.ui.login;

/**
 * Created by Josip on 1.3.2016..
 */
public interface LoginPresenter {
    void tryToLogIn(String email, String password);
    void success(String username);
    void fail();
    void goToSignUpScreen();
}
