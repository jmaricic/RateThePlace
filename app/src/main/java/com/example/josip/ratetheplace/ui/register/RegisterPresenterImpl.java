package com.example.josip.ratetheplace.ui.register;

import com.example.josip.ratetheplace.interactor.registration.RegisterUserInteractorImpl;

/**
 * Created by Josip on 21.3.2016..
 */
public class RegisterPresenterImpl implements RegisterPresenter {

    private final RegisterUserView registerUserView;
    private final RegisterUserInteractorImpl interactor;


    public RegisterPresenterImpl(RegisterUserView view) {
        this.registerUserView = view;
        this.interactor = new RegisterUserInteractorImpl(this);

    }

    @Override
    public void registerNewUser(String username, String email, String password) {
        interactor.checkIfUserExists(username, email, password);
    }

    @Override
    public void onFailure() {
        registerUserView.onFailure();
    }

    @Override
    public void onSuccess(String email, String username) {
        registerUserView.onSuccess(email, username);
    }


    @Override
    public void onUserAlreadyExists() {
        registerUserView.userTaken();
    }

    @Override
    public void onUserDoesNotExist(String username, String email, String password) {
        registerUserView.userAvailable();
        interactor.tryToRegister(username, email, password);
    }

}
