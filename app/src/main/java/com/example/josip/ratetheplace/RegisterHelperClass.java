package com.example.josip.ratetheplace;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Josip on 3.5.2016..
 */
public class RegisterHelperClass {

    public static void hideErrorMessageLogin(EditText username, EditText email, EditText password, final TextInputLayout usernameInput,
                                             final TextInputLayout emailInput, final TextInputLayout passwordInput) {
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailInput.setErrorEnabled(false);
                emailInput.setError(null);
                passwordInput.setErrorEnabled(false);
                passwordInput.setError(null);
                usernameInput.setErrorEnabled(false);
                usernameInput.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailInput.setErrorEnabled(false);
                emailInput.setError(null);
                passwordInput.setErrorEnabled(false);
                passwordInput.setError(null);
                usernameInput.setErrorEnabled(false);
                usernameInput.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailInput.setErrorEnabled(false);
                emailInput.setError(null);
                passwordInput.setErrorEnabled(false);
                passwordInput.setError(null);
                usernameInput.setErrorEnabled(false);
                usernameInput.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
