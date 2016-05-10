package com.example.josip.ratetheplace.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.josip.ratetheplace.R;
import com.example.josip.ratetheplace.ui.base.BaseActivity;
import com.example.josip.ratetheplace.ui.list.ListActivity;
import com.example.josip.ratetheplace.ui.login.LoginFragment;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends BaseActivity {

    private Firebase firebase = new Firebase("https://josip-my-application.firebaseio.com/Users/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        if (savedInstanceState == null) {
            if (firebase.getAuth() == null) {
                initUi();
            } else {
                String uid = firebase.getAuth().getUid();
                Firebase usernameFir = new Firebase("https://josip-my-application.firebaseio.com/Users/" + uid);
                usernameFir.child("username").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Intent i = new Intent(MainActivity.this, ListActivity.class);
                        i.putExtra("username", dataSnapshot.getValue().toString());
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        }
    }


    @Override
    public void initUi () {
        replaceFragment(R.id.fragment_container, LoginFragment.newInstance(), false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
