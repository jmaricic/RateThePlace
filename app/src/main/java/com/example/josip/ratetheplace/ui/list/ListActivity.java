package com.example.josip.ratetheplace.ui.list;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.josip.ratetheplace.R;
import com.example.josip.ratetheplace.ui.base.BaseActivity;

/**
 * Created by Josip on 6.4.2016..
 */
public class ListActivity extends BaseActivity {

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
        replaceFragment(R.id.fragment_container, ListFragment.newInstance(),false);
    }
}
