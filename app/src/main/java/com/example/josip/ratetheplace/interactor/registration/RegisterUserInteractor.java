package com.example.josip.ratetheplace.interactor.registration;

import java.util.Map;

/**
 * Created by Josip on 21.3.2016..
 */
public interface RegisterUserInteractor {
    void tryToRegister(String username, String email, String password);
    Map<String, Object> createUser(String username, String email, String password);
    void checkIfUserExists(String username, String email, String password);
}
