package com.sorbellini.s214631.lab3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Locale;

public class ShowReservationDetails extends AppCompatActivity {

    private Reservation reservation;
    private DailyOffer dailyOffer;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reservation_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reservation_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();

        String json = preferences.getString("reservation", null);
        if(json != null) {
            reservation = gson.fromJson(json, Reservation.class);
            dailyOffer = reservation.getDailyOffer();
            customer = reservation.getCustomer();

            ImageView imageView = (ImageView) findViewById(R.id.reservation_details_image);
            imageView.setImageURI(Uri.parse(dailyOffer.getPhoto()));

            TextView textView = (TextView) findViewById(R.id.reservation_details_offer_name);
            textView.setText(dailyOffer.getName());
            textView = (TextView) findViewById(R.id.reservation_details_price);
            textView.setText(String.format(Locale.getDefault(), "%d", dailyOffer.getPrice()) + " €");
            textView = (TextView) findViewById(R.id.reservation_details_date_time);
            textView.setText(reservation.getDate() + " " + reservation.getTime());
            textView = (TextView) findViewById(R.id.reservation_details_customer_name);
            textView.setText(customer.getName() + " " + customer.getSurname());

            Button button = (Button) findViewById(R.id.restaurant_details_button_restaurant);
            button.setText(dailyOffer.getRestaurantName());
        }



    }


    public void goToOfferDescription(View view) {
        startActivity(new Intent(this, ShowOfferDetails.class));
    }



    public void goToRestaurantDescription(View view) {

    }


}
