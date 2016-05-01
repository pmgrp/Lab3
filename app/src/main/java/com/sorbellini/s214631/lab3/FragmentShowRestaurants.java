package com.sorbellini.s214631.lab3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentShowRestaurants extends Fragment {

    //adapter and recicler view for the card view
    AdapterShowRestaurants cardAdapter;
    RecyclerView rv;
    ArrayList<Restaurant> restaurants;

    public FragmentShowRestaurants() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_restaurants, container, false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restaurants = DataGen.makeRestaurants();
        cardAdapter = new AdapterShowRestaurants(restaurants);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = (RecyclerView) view.findViewById(R.id.restaurants);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        cardAdapter = new AdapterShowRestaurants(restaurants);
        rv.setAdapter(cardAdapter);

    }
}
