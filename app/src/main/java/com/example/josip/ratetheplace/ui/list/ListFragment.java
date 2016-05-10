package com.example.josip.ratetheplace.ui.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.josip.ratetheplace.R;
import com.example.josip.ratetheplace.ui.base.BaseFragment;
import com.example.josip.ratetheplace.ui.main.MainActivity;
import com.example.josip.ratetheplace.ui.map.allMarkers.AllMarkersMapActivity;
import com.firebase.client.Firebase;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Josip on 6.4.2016..
 */
public class ListFragment extends BaseFragment implements Toolbar.OnMenuItemClickListener {

    @Bind(R.id.app_bar)
    Toolbar mToolbar;

    private RecyclerView mRecyclerView;
    private Firebase hotelsRef = new Firebase("https://josip-my-application.firebaseio.com/Users/");
    private CustomAdapter mAdapter;

    public static ListFragment newInstance() {
        return new ListFragment();
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
        View layout = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycleView);
        mAdapter = new CustomAdapter(getActivity(), hotelsRef);
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
        setToolbar();
    }

    private void setToolbar() {
        getActivity().getMenuInflater().inflate(R.menu.menu_list, mToolbar.getMenu());
        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.setTitle(R.string.hotel_list_toolbar);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                Toast.makeText(getActivity(), R.string.logout_message, Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(), MainActivity.class));
                hotelsRef.unauth();
                return true;
            case R.id.my_list:
                replaceFragment(R.id.fragment_container, MyListFragment.newInstance(), false);
                return true;
            case R.id.show_markers:
                Intent i = new Intent(getActivity(), AllMarkersMapActivity.class);
                i.putExtra("username", getActivity().getIntent().getStringExtra("username"));
                startActivity(i);
                return true;
        }
        return false;
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
