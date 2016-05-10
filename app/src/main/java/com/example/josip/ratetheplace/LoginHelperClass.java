package com.example.josip.ratetheplace;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Josip on 3.5.2016..
 */
public class LoginHelperClass {

    public static void hideErrorMessageLogin(EditText email, EditText password, final TextInputLayout emailInput, final TextInputLayout passwordInput) {
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
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public static void checkFieldsLengthForError(EditText email, EditText password, final TextInputLayout emailInput, final TextInputLayout passwordInput) {
        if (email.getText().length() == 0) {
            emailInput.setErrorEnabled(true);
            emailInput.setError("Please enter email");
        }
        if(!isEmailValid(email.getText().toString()) && email.getText().length() != 0) {
            emailInput.setErrorEnabled(true);
            emailInput.setError("Wrong email type");
        }
        if (password.getText().length() == 0 ) {
            passwordInput.setErrorEnabled(true);
            passwordInput.setError("Please enter password");
        }
        if(password.getText().length() > 0 && password.getText().length() < 4) {
            passwordInput.setErrorEnabled(true);
            passwordInput.setError("Too short (min 4 characters)");
        }

        if ((password.getText().length() != 0) && (email.getText().length() != 0) ) {
            if(password.getText().length() < 4) {
                passwordInput.setErrorEnabled(true);
                passwordInput.setError("Too short (min 4 characters)");
            } else {
                if(!isEmailValid(email.getText().toString())) {
                    emailInput.setErrorEnabled(true);
                    emailInput.setError("Wrong email type");
                } else {
                    emailInput.setErrorEnabled(true);
                    emailInput.setError("Wrong email or password");
                    passwordInput.setErrorEnabled(true);
                    passwordInput.setError("Wrong email or password");
                }
            }

        }
    }

    private static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
