package com.example.josip.ratetheplace.ui.login;

import com.example.josip.ratetheplace.interactor.login.LogInUserInteractorImpl;

/**
 * Created by Josip on 1.3.2016..
 */
public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private LogInUserInteractorImpl interactor;

    public LoginPresenterImpl(LoginView view) {
        this.loginView = view;
        interactor = new LogInUserInteractorImpl(this);
    }

    @Override
    public void tryToLogIn(String email, String password) {
        interactor.tryToLogin(email, password);
    }

    @Override
    public void goToSignUpScreen() {
        loginView.goToSignUpScrn();
    }

    @Override
    public void success(String username) {
        loginView.onSuccess(username);
    }

    @Override
    public void fail() {
        loginView.onError();
    }

}
