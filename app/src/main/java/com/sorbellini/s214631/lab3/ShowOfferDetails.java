package com.sorbellini.s214631.lab3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ShowOfferDetails extends AppCompatActivity {

    private String ID;
    private Customer customer;
    private DailyOffer dailyOffer;
    private Restaurant restaurant;
    private String time;
    private int status;
    private Calendar myCalendar;
    static final int DIALOG_ID = 0;
    int xyear, xmonth, xday;
    int xhour, xminute;
    Reservation myReservation;
    ArrayList<Reservation> reservations = new ArrayList<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_offer_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_offer_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Calendar cal = Calendar.getInstance();
        xyear = cal.get(Calendar.YEAR);
        xmonth = cal.get(Calendar.MONTH);
        xday = cal.get(Calendar.DAY_OF_MONTH);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();


        String json = preferences.getString("offer", null);
        if (json != null) {
            dailyOffer = gson.fromJson(json, DailyOffer.class);
            restaurant = dailyOffer.getRestaurant();

            ImageView imageView = (ImageView) findViewById(R.id.offer_details_image);
            imageView.setImageURI(Uri.parse(dailyOffer.getPhoto()));

            TextView textView = (TextView) findViewById(R.id.offer_details_offer_name);
            textView.setText(dailyOffer.getName());
            textView = (TextView) findViewById(R.id.offer_details_price);
            textView.setText(String.format(Locale.getDefault(), "%d", dailyOffer.getPrice()) + " €");
            textView = (TextView) findViewById(R.id.offer_details_description);
            textView.setText(dailyOffer.getDescription());

            Button button = (Button) findViewById(R.id.offer_details_button_restaurant);
            button.setText(restaurant.getRestaurantName());
        }

        json = preferences.getString("reservations", null);
        if (json != null)
            reservations = gson.fromJson(json, new TypeToken<List<Reservation>>() {
            }.getType());
        else
            reservations = new ArrayList<>();
        showDialogOnButtonClick();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void goToRestaurantDescription(View view) {
        startActivity(new Intent(this, ActivityRestaurantProfile.class));
    }


    public void showDialogOnButtonClick() {
        Button buy = (Button) findViewById(R.id.button_buy);

        buy.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );


    }

    private void createTimePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(ShowOfferDetails.this, 4, new TimePickerDialog.OnTimeSetListener() {
            @Override
            // CLEMENT : si tu veux changer le design de l'horloge il faut que tu changes le nombre au-dessus
            // De 1 à 3, c'est des spinner et après c'est des horloges

            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                //eReminderTime.setText( selectedHour + ":" + selectedMinute);
                xhour = selectedHour;
                xminute = selectedMinute;

            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

        Log.d("Hour: ", Integer.toString(xhour));
        Log.d("Minutes: ", Integer.toString(xminute));

        myReservation.setTime(Integer.toString(xhour) + ":" + Integer.toString(xminute));
        Customer dummyCustomer = new Customer();
        dummyCustomer.setName("Donald");
        dummyCustomer.setSurname("Trump");
        dummyCustomer.setPhone("19247934");
        myReservation.setCustomer(dummyCustomer);

        reservations.add(myReservation);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(reservations, new TypeToken<List<Reservation>>() {
        }.getType());
        JsonArray jsonarray = element.getAsJsonArray();
        editor.putString("reservations", jsonarray.toString());
        editor.commit();


    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {

            DatePickerDialog myDatePickerDialog = new DatePickerDialog(this, dpickerListener, xyear, xmonth, xday);
            myDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            return myDatePickerDialog;
            // return new DatePickerDialog(this, dpickerListener, xyear, xmonth, xday);
        } else
            return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            xyear = year;
            xmonth = monthOfYear + 1; // by default it begins on 0
            xday = dayOfMonth;


            myReservation = new Reservation();

            myReservation.setDate(Integer.toString(xday) + "-" + Integer.toString(xmonth) + "-" + Integer.toString(xyear));
            myReservation.setDailyOffer(dailyOffer);
            myReservation.setStatus(Reservation.ARRIVED);
            createTimePicker();
        }

    };


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ShowOfferDetails Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.sorbellini.s214631.lab3/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ShowOfferDetails Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.sorbellini.s214631.lab3/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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