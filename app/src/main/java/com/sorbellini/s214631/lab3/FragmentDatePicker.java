package com.sorbellini.s214631.lab3;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by eugeniosorbellini on 13/05/16.
 */
public class FragmentDatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return dpd;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
            ((ShowOfferDetails)getActivity()).setDate(year,month,day);
    }


}
