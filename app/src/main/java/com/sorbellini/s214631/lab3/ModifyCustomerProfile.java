package com.sorbellini.s214631.lab3;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Locale;

public class ModifyCustomerProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_customer_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_offer_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();

        String json = preferences.getString("profile", null);
        if(json != null) {
            profile = gson.fromJson(json, Profile.class);

            ImageView imageView = (ImageView) findViewById(R.id.modify_customer_profile_photo);
            imageView.setImageURI(Uri.parse(profile.getPhoto()));

            TextView textView = (TextView) findViewById(R.id.modify_customer_profile_surname);
            textView.setText(profile.getSurname());
            textView = (TextView) findViewById(R.id.modify_customer_profile_name);
            textView.setText(profile.getName());
            textView = (TextView) findViewById(R.id.modify_customer_profile_email);
            textView.setText(profile.getEmail());
            textView = (TextView) findViewById(R.id.modify_customer_profile_phone);
            textView.setText(profile.getPhone());
            textView = (TextView) findViewById(R.id.modify_customer_profile_password);
            textView.setText(profile.getPassword());

        }
        */
    }


    public void saveData() {

    }

}
