package com.example.josip.ratetheplace.ui.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.josip.ratetheplace.R;
import com.example.josip.ratetheplace.ui.base.BaseFragment;
import com.example.josip.ratetheplace.ui.main.MainActivity;
import com.example.josip.ratetheplace.ui.map.myHotels.myHotelsMap.MyHotelsMapActivity;
import com.example.josip.ratetheplace.ui.map.myHotels.addNewHotel.AddNewMarkerMapActivity;
import com.firebase.client.Firebase;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Josip on 9.4.2016..
 */
public class MyListFragment extends BaseFragment implements FloatingActionButton.OnClickListener {

    private RecyclerView mRecyclerView;

    @Bind(R.id.floating_button)
    FloatingActionButton mActionButton;

    @Bind(R.id.app_bar)
    Toolbar mToolbar;

    private Firebase hotelsRef = new Firebase("https://josip-my-application.firebaseio.com/Users/");
    private Firebase firebase = new Firebase(hotelsRef + hotelsRef.getAuth().getUid());
    private MyCustomAdapter mAdapter;

    public static MyListFragment newInstance() {
        return new MyListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_my_list, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycleView);
        mAdapter = new MyCustomAdapter(getActivity(), firebase);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareUi(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.clear();
        hotelsRef.addChildEventListener(mAdapter);
    }


    @Override
    protected void prepareUi(@NonNull View view) {
        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.go_back_toolbar);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                replaceFragment(R.id.fragment_container, ListFragment.newInstance(), false);
            }
        });

        mActionButton.setOnClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_my_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.show_map:
                Intent i = new Intent(getActivity(), MyHotelsMapActivity.class);
                i.putExtra("username", getActivity().getIntent().getStringExtra("username"));
                startActivity(i);
                return true;
            case R.id.logout:
                Toast.makeText(getActivity(), R.string.logout_message, Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(), MainActivity.class));
                hotelsRef.unauth();
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getActivity(), AddNewMarkerMapActivity.class);
        i.putExtra("username", getActivity().getIntent().getStringExtra("username"));
        startActivity(i);
    }

    protected void replaceFragment(@IdRes int id, @NonNull BaseFragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getTag());
        }
        transaction.commit();
    }
}
