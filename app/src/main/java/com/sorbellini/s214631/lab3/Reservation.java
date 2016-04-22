package com.sorbellini.s214631.lab3;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by eugeniosorbellini on 01/04/16.
 */
public class Reservation {
    public static final int ARRIVED = 0;
    public static final int CONFIRMED = 1;
    public static final int REJECTED = 2;
    public static final int COMPLETED = 3;
    private String ID;
    private String restaurantID;
    private Customer customer;
    private DailyOffer dailyOffer;
    private String time;
    private String comment;
    private int status;

    public Reservation(){
        this.ID = null;
        this.restaurantID = null;
        this.customer = null;
        this.dailyOffer = null;
        this.time = null;
        this.comment = null;
        this.status = ARRIVED;
    }

    //getter
    public String getID(){ return ID; }
    public String getRestaurantID(){ return restaurantID; }
    public Customer getCustomer(){ return this.customer; }
    public String getTime(){ return this.time; }
    public String getComment(){ return this.comment; }
    public int getStatus(){ return this.status; }
    public DailyOffer getDailyOffer(){ return dailyOffer; }

    //setter
    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setComment(String comment){ this.comment = comment; }

    public void setStatus(int status){ this.status = status; }

    public void setDailyOffer(DailyOffer dailyOffer){ this.dailyOffer = dailyOffer; }

}
