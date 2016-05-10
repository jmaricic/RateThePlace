package com.example.josip.ratetheplace.ui.register;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.josip.ratetheplace.R;
import com.example.josip.ratetheplace.ui.base.BaseActivity;

/**
 * Created by Josip on 22.2.2016..
 */
public class RegisterActivity extends BaseActivity {

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
        replaceFragment(R.id.fragment_container, RegisterUserFragment.newInstance(), false);
    }

}
