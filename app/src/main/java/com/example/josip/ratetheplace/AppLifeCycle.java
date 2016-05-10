package com.example.josip.ratetheplace;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Josip on 1.4.2016..
 */
public class AppLifeCycle extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
