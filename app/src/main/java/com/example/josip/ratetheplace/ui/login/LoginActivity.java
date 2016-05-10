package com.example.josip.ratetheplace.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.example.josip.ratetheplace.R;
import com.example.josip.ratetheplace.ui.base.BaseActivity;

/**
 * Created by Josip on 22.2.2016..
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        if (savedInstanceState == null) {
            initUi();
        }

    }

    @Override
    public void initUi() {
        replaceFragment(R.id.fragment_container, LoginFragment.newInstance(),false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}

