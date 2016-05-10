package com.example.josip.ratetheplace.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.josip.ratetheplace.LoginHelperClass;
import com.example.josip.ratetheplace.R;
import com.example.josip.ratetheplace.ui.base.BaseFragment;
import com.example.josip.ratetheplace.ui.list.ListActivity;
import com.example.josip.ratetheplace.ui.register.RegisterActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Josip on 22.2.2016..
 */
public class LoginFragment extends BaseFragment implements LoginView {

    @Bind(R.id.welcome_text)
    TextView mWelcome;

    @Bind(R.id.login_email_inputLayout)
    TextInputLayout mEmailInputLayout;

    @Bind(R.id.login_password_inputLayout)
    TextInputLayout mPasswordInputLayout;

    @Bind(R.id.email_editText_login)
    EditText mEmail;

    @Bind(R.id.password_editText_login)
    EditText mPassword;

    @Bind(R.id.signUp_button_login)
    TextView mSignUpBtn;

    @Bind(R.id.signIn_button_login)
    Button mSignInBtn;

    private LoginPresenterImpl loginPresenter;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareUi(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        loginPresenter = new LoginPresenterImpl(this);
    }

    @Override
    protected void prepareUi(@NonNull View view) {
        ButterKnife.bind(this, view);
    }


    @OnClick(R.id.signIn_button_login)
    public void signIn() {
        loginPresenter.tryToLogIn(mEmail.getText().toString(), mPassword.getText().toString());
    }


    @OnClick(R.id.signUp_button_login)
    public void goToSignUpScreen() {
        loginPresenter.goToSignUpScreen();
    }

    @Override
    public void onSuccess(String username) {

        loginSuccessfulColors();

        Intent i = new Intent(getActivity(), ListActivity.class);
        i.putExtra("username", username);
        startActivity(i);
        getActivity().finish();

    }

    private void loginSuccessfulColors() {
        mEmailInputLayout.setError(null);
        mEmailInputLayout.setErrorEnabled(false);
        mPasswordInputLayout.setError(null);
        mPasswordInputLayout.setErrorEnabled(false);
        mEmail.getBackground().mutate().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        mPassword.getBackground().mutate().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onError() {
        LoginHelperClass.checkFieldsLengthForError(mEmail, mPassword, mEmailInputLayout, mPasswordInputLayout);
        LoginHelperClass.hideErrorMessageLogin(mEmail, mPassword, mEmailInputLayout, mPasswordInputLayout);
    }

    @Override
    public void goToSignUpScrn() {
        startActivity(new Intent(getActivity(), RegisterActivity.class));
    }

}
