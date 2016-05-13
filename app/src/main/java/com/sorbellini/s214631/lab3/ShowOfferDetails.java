package com.sorbellini.s214631.lab3;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ShowOfferDetails extends AppCompatActivity
        implements FragmentTimePicker.FragmentListener{


    private DailyOffer dailyOffer;
    int xyear = -1;
    int xmonth = -1;
    int xday = -1;
    int xhour = -1;
    int xminute = -1;
    Reservation myReservation;
    ArrayList<Reservation> reservations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_offer_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_offer_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /*
        //final Calendar cal = Calendar.getInstance();
        xyear = cal.get(Calendar.YEAR);
        xmonth = cal.get(Calendar.MONTH);
        xday = cal.get(Calendar.DAY_OF_MONTH);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();


        String json = preferences.getString("offer", null);
        if(json != null) {
            dailyOffer = gson.fromJson(json, DailyOffer.class);

            ImageView imageView = (ImageView) findViewById(R.id.offer_details_image);
            imageView.setImageURI(Uri.parse(dailyOffer.getPhoto()));

            TextView textView = (TextView) findViewById(R.id.offer_details_offer_name);
            textView.setText(dailyOffer.getName());
            textView = (TextView) findViewById(R.id.offer_details_price);
            textView.setText(String.format(Locale.getDefault(), "%d", dailyOffer.getPrice()) + " €");
            textView = (TextView) findViewById(R.id.offer_details_description);
            textView.setText(dailyOffer.getDescription());

            Button button = (Button) findViewById(R.id.offer_details_button_restaurant);
            button.setText(dailyOffer.getRestaurantName());
        }


        //get reservations if there were already saved
        json = preferences.getString("reservations", null);
        if(json != null) {
            reservations = gson.fromJson(json, new TypeToken<List<Reservation>>() {
            }.getType());
        }
        else {
            reservations = new ArrayList<>();
        }

    }

    public void showDateTimePicker(View v){
        DialogFragment newFragment = new FragmentTimePicker();
        newFragment.show(getSupportFragmentManager(), "timePicker");
        newFragment = new FragmentDatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
        //Log.d("HOUR", Integer.toString(xhour));


    }

    public void onTimeFragmentOkListener(){
        Log.d("DAY", Integer.toString(xday));
        Log.d("MONTH", Integer.toString(xmonth));
        Log.d("YEAR", Integer.toString(xyear));
        Log.d("HOUR", Integer.toString(xhour));
        Log.d("MINUTE", Integer.toString(xminute));
        if(xhour != -1 && xminute != -1 && xday != -1 && xmonth != -1 && xyear != -1){
            //create dummy customer
            Customer dummyCustomer = new Customer();
            dummyCustomer.setName("Mario");
            dummyCustomer.setSurname("Rossi");
            dummyCustomer.setPhone("19247934");

            //create new reservation
            myReservation = new Reservation();
            myReservation.setCustomer(dummyCustomer);
            myReservation.setDate(Integer.toString(xday) + "-" + Integer.toString(xmonth) +"-" + Integer.toString(xyear));
            myReservation.setTime(Integer.toString(xhour) + ":" + Integer.toString(xminute));
            myReservation.setDailyOffer(dailyOffer);
            myReservation.setStatus(Reservation.ARRIVED);

            //add the reservation
            reservations.add(myReservation);
            Toast t = Toast.makeText(this, "Reservation Created", Toast.LENGTH_LONG);
            t.show();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(reservations, new TypeToken<List<Reservation>>(){}.getType());
            JsonArray jsonarray = element.getAsJsonArray();
            editor.putString("reservations", jsonarray.toString());
            editor.commit();

        }
        else{
            Toast t = Toast.makeText(this, "Reservation Not Completed", Toast.LENGTH_SHORT );
            t.show();
            xyear = -1;
            xmonth = -1;
            xday = -1;
            xhour = -1;
            xminute = -1;

        }
    }


    public void setTime(int hour, int minutes){
        this.xhour = hour;
        this.xminute = minutes;
    }

    public void setDate(int xyear, int xmonth, int xday){
        this.xyear = xyear;
        this.xmonth = xmonth;
        this.xday = xday;
    }




}






  /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
fab.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        //onClickPopupOffer();
        startActivity(new Intent(ActivityShowOffers.this, ActivityAddOffer.class));
        }
        });
*/


/*



   edittext.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new DatePickerDialog(classname.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    });

      private void updateLabel() {

    String myFormat = "MM/dd/yy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    edittext.setText(sdf.format(myCalendar.getTime()));
    }
 */





/*    Button buy = (Button) findViewById(R.id.button_buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPopupOffer();
            }
        });

    }

    public void onClickPopupOffer() {
        final CharSequence[] items = { "Yes", "No" };
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowOfferDetails.this);
        builder.setTitle("Do you want to add an offer_item ?");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Yes")) {
                    startActivity(new Intent(ShowOfferDetails.this, ActivityShowOffers.class));
                } else if (items[item].equals("No")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }*/