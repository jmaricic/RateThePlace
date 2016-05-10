package com.example.josip.ratetheplace.ui.register;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.josip.ratetheplace.R;
import com.example.josip.ratetheplace.RegisterHelperClass;
import com.example.josip.ratetheplace.ui.base.BaseFragment;
import com.example.josip.ratetheplace.ui.login.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Josip on 22.2.2016..
 */
public class RegisterUserFragment extends BaseFragment implements RegisterUserView {

    @Bind(R.id.app_bar)
    Toolbar mToolbar;

    @Bind(R.id.register_username_inputLayout)
    TextInputLayout mUsernameInputLayout;

    @Bind(R.id.register_email_inputLayout)
    TextInputLayout mEmailInputLayout;

    @Bind(R.id.register_password_inputLayout)
    TextInputLayout mPasswordInputLayout;

    @Bind(R.id.email_editText_register)
    EditText mEmail;

    @Bind(R.id.password_editText_register)
    EditText mPassword;

    @Bind(R.id.username_editText_register)
    EditText mUsername;

    @Bind(R.id.signUp_button_register)
    Button mSignUpBtn;

    private RegisterPresenterImpl registerPresenter;


    public static RegisterUserFragment newInstance() {
        return new RegisterUserFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareUi(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerPresenter = new RegisterPresenterImpl(this);
    }

    @Override
    protected void prepareUi(@NonNull View view) {
        ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @OnClick(R.id.signUp_button_register)
    public void tryToRegister() {
        checkIfUserExists();
    }

    private void checkIfUserExists() {
        if(mUsername.getText().length() > 1 && isEmailValid(mEmail.getText().toString()) && mPassword.getText().length() > 3) {
            registerPresenter.registerNewUser(mUsername.getText().toString(), mEmail.getText().toString(), mPassword.getText().toString());
        } else {
            if (mUsername.getText().length() == 0) {
                mUsernameInputLayout.setErrorEnabled(true);
                mUsernameInputLayout.setError("Please enter username");
            } else if(mUsername.getText().length() > 0 && mUsername.getText().length() < 2) {
                mUsernameInputLayout.setErrorEnabled(true);
                mUsernameInputLayout.setError("Too short (min 2 characters)");
            }

            if (mEmail.getText().length() == 0) {
                mEmailInputLayout.setErrorEnabled(true);
                mEmailInputLayout.setError("Please enter email");
            } else if(!isEmailValid(mEmail.getText().toString())) {
                mEmailInputLayout.setErrorEnabled(true);
                mEmailInputLayout.setError("Wrong email type");
            }

            if (mPassword.getText().length() == 0) {
                mPasswordInputLayout.setErrorEnabled(true);
                mPasswordInputLayout.setError("Please enter password");
            } else if(mPassword.getText().length() > 0 && mPassword.getText().length() < 4) {
                mPasswordInputLayout.setErrorEnabled(true);
                mPasswordInputLayout.setError("Too short (min 4 characters)");
            }
        }

        RegisterHelperClass.hideErrorMessageLogin(mUsername, mEmail, mPassword, mUsernameInputLayout, mEmailInputLayout, mPasswordInputLayout);
    }

    @Override
    public void userTaken() {
        mEmailInputLayout.setErrorEnabled(true);
        mEmailInputLayout.setError("This email has already been used");
        mUsernameInputLayout.setErrorEnabled(true);
        mUsernameInputLayout.setError("This username has already been used");
        mEmail.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        mUsername.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        RegisterHelperClass.hideErrorMessageLogin(mUsername, mEmail, mPassword, mUsernameInputLayout, mEmailInputLayout, mPasswordInputLayout);
    }

    @Override
    public void userAvailable() {
        RegisterHelperClass.hideErrorMessageLogin(mUsername, mEmail, mPassword, mUsernameInputLayout, mEmailInputLayout, mPasswordInputLayout);
        mEmailInputLayout.setErrorEnabled(false);
        mUsernameInputLayout.setErrorEnabled(false);
        mPasswordInputLayout.setErrorEnabled(false);
        mEmailInputLayout.setError(null);
        mUsernameInputLayout.setError(null);
        mPasswordInputLayout.setError(null);
        mEmail.getBackground().mutate().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        mUsername.getBackground().mutate().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        mPassword.getBackground().mutate().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onFailure() {
        mEmailInputLayout.setErrorEnabled(true);
        mUsernameInputLayout.setErrorEnabled(true);
        mPasswordInputLayout.setErrorEnabled(true);
        mEmailInputLayout.setError("Something went wrong, try again");
        mUsernameInputLayout.setError("Something went wrong, try again");
        mPasswordInputLayout.setError("Something went wrong, try again");
        mEmail.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        mUsername.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        mPassword.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        RegisterHelperClass.hideErrorMessageLogin(mUsername, mEmail, mPassword, mUsernameInputLayout, mEmailInputLayout, mPasswordInputLayout);
    }

    @Override
    public void onSuccess(String email, String username) {
        Intent i = new Intent(getActivity(), RegisterSuccessfulActivity.class);
        i.putExtra("email", email);
        i.putExtra("username", username);
        startActivity(i);
        getActivity().finish();
        Toast.makeText(getActivity(), "Successful!", Toast.LENGTH_SHORT).show();
    }

}
