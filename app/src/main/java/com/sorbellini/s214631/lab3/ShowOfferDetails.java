package com.sorbellini.s214631.lab3;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ShowOfferDetails extends AppCompatActivity {

    private String ID;
    private Customer customer;
    private DailyOffer dailyOffer;
    private String time;
    private int status;
    private Calendar myCalendar;
    static final int DIALOG_ID = 0;
    int xyear,xmonth,xday;
    int xhour,xminute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_offer_details);

        final Calendar cal = Calendar.getInstance();
        xyear = cal.get(Calendar.YEAR);
        xmonth = cal.get(Calendar.MONTH);
        xday = cal.get(Calendar.DAY_OF_MONTH);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = preferences.getString("offer", null);
        if(json != null)
            dailyOffer = gson.fromJson(json, DailyOffer.class);



        showDialogOnButtonClick();

        
        /*
        DailyOffer off1 = new DailyOffer();
        this.dailyOffer = off1;
        Uri uri = Uri.parse("android.resource://com.sorbellini.s214631.lab3/drawable/pizza");
        String imagePath = uri.toString();
        off1.setID("1");
        off1.setRestaurantID("1");
        off1.setPhoto(imagePath);
        off1.setName("Pizza");
        off1.setPrice(10);
        off1.setRestaurantLatitude(41.90278); //Rome
        off1.setRestaurantLongitude(12.49637);
        off1.setRestaurantName("Da Gennaro");
        */
    }



    public void showDialogOnButtonClick() {
        Button buy = (Button) findViewById(R.id.button_buy);

        buy.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
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
                Toast.makeText(ShowOfferDetails.this,xhour + " : "+ xminute, Toast.LENGTH_LONG).show();
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this,dpickerListener,xyear,xmonth,xday);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            xyear = year;
            xmonth = monthOfYear + 1; // by default it begins on 0
            xday = dayOfMonth;
            Toast.makeText(ShowOfferDetails.this,xyear+ " / "+ xmonth +" / "+xday,Toast.LENGTH_LONG).show();
            createTimePicker();
        }

    };





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