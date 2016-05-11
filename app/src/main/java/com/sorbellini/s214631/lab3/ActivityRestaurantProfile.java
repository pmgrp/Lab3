package com.sorbellini.s214631.lab3;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Locale;

public class ActivityRestaurantProfile extends AppCompatActivity {

    Restaurant restaurant;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_restaurant_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();

        String json = preferences.getString("restaurant", null);
        if(json != null) {
            restaurant = gson.fromJson(json, Restaurant.class);

            imageView = (ImageView) findViewById(R.id.restaurant_profile_image);
            imageView.setImageURI(Uri.parse(restaurant.getRestaurantPhoto()));

            textView = (TextView) findViewById(R.id.restaurant_profile_restaurant);
            textView.setText(restaurant.getRestaurantName());

            textView = (TextView) findViewById(R.id.restaurant_profile_restaurant_email);
            textView.setText(restaurant.getRestaurantEmail());

            textView = (TextView) findViewById(R.id.restaurant_profile_restaurant_address);
            textView.setText(restaurant.getRestaurantAddress());

            textView = (TextView) findViewById(R.id.restaurant_profile_restaurant_phone);
            textView.setText(restaurant.getRestaurantPhone());

            textView = (TextView) findViewById(R.id.restaurant_profile_restaurant_website);
            textView.setText(restaurant.getRestaurantWebsite());

            textView = (TextView) findViewById(R.id.restaurant_profile_restaurant_iva);
            textView.setText(restaurant.getRestaurantPiva());

        }
    }




}
