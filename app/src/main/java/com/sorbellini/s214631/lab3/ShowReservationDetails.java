package com.sorbellini.s214631.lab3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Locale;

public class ShowReservationDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reservation_details);


        /*
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();

        String json = preferences.getString("reservation", null);
        if(json != null) {
            reservation = gson.fromJson(json, Reservation.class);

            ImageView imageView = (ImageView) findViewById(R.id.reservation_details_image);
            //imageView.setImageURI(Uri.parse(dailyOffer.getPhoto()));

            TextView textView = (TextView) findViewById(R.id.reservation_details_offer_name);
            textView.setText(reservation.getDailyOffer.getName());
            textView = (TextView) findViewById(R.id.reservation_details_price);
            textView.setText(String.format(Locale.getDefault(), "%d", reservation.getDailyOffer.getPrice()) + " €");
            textView = (TextView) findViewById(R.id.reservation_details_date_time);
            textView.setText(reservation.getTime());
            textView = (TextView) findViewById(R.id.reservation_details_customer_name);
            textView.setText(reservation.getCustomer.getSurname());
        }
        */


    }


    public void goToOfferDescription(View view) {
        startActivity(new Intent(this, ShowOfferDetails.class));
    }


    /*
    public void goToRestaurantDescription(View view) {

    }
    */

}
