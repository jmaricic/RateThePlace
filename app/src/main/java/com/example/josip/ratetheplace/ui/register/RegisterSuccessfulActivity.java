package com.example.josip.ratetheplace.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.example.josip.ratetheplace.R;
import com.example.josip.ratetheplace.ui.base.BaseActivity;
import com.example.josip.ratetheplace.ui.login.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Josip on 25.3.2016..
 */
public class RegisterSuccessfulActivity extends BaseActivity {

    @Bind(R.id.successful_register_your_username_textView)
    TextView mUsername;

    @Bind(R.id.successful_register_your_email_textView)
    TextView mEmail;

    @Bind(R.id.successful_register_proceed_button)
    Button mProceedBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succesfull_registration);

        if (savedInstanceState == null) {
            initUi();
        }
    }

    @Override
    public void initUi() {
        ButterKnife.bind(this);
        mEmail.setText(getIntent().getStringExtra("email"));
        mUsername.setText(getIntent().getStringExtra("username"));
    }

    @OnClick(R.id.successful_register_proceed_button)
    public void proceedToLogin() {
        startActivity(new Intent(RegisterSuccessfulActivity.this, LoginActivity.class));
    }
}
