package com.example.josip.ratetheplace.ui.base;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Josip on 22.2.2016..
 */
public abstract class BaseFragment extends Fragment {

    protected abstract void prepareUi(@NonNull View view);

}
