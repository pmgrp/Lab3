package com.sorbellini.s214631.lab3;

import android.content.ContentResolver;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by eugeniosorbellini on 26/04/16.
 */
public class DataGen {

    static DailyOffer off1,off2,off3,off4,off5,off6;
    static Restaurant res1, res2, res3;
    static Reservation reserv1, reserv2, reserv3;

    public static ArrayList<Restaurant> makeRestaurants() {

        res1 = new Restaurant();
        Uri uri = Uri.parse("android.resource://com.sorbellini.s214631.lab3/drawable/restaurant_rome");
        String imagePath = uri.toString();
        res1.setID("1");
        res1.setRestaurantPhoto(imagePath);
        res1.setRestaurantName("Da Gennaro");
        res1.setRestaurantPhone("06887654");
        res1.setRestaurantAddress("Piazza Della Repubblica 1, Roma");
        res1.setLatitude(41.90278); //Rome
        res1.setLongitude(12.49637);

        res2 = new Restaurant();
        uri = Uri.parse("android.resource://com.sorbellini.s214631.lab3/drawable/restaurant_paris");
        imagePath = uri.toString();
        res2.setID("2");
        res2.setRestaurantPhoto(imagePath);
        res2.setRestaurantName("Chez Léo");
        res2.setRestaurantPhone("0165784325");
        res2.setRestaurantAddress("Rue de Rivoli 25, Paris");
        res2.setLatitude(48.85661); //Paris
        res2.setLongitude(2.35222);

        res3 = new Restaurant();
        uri = Uri.parse("android.resource://com.sorbellini.s214631.lab3/drawable/restaurant_tokyo");
        imagePath = uri.toString();
        res3.setID("3");
        res3.setRestaurantPhoto(imagePath);
        res3.setRestaurantName("Keio Sushi");
        res3.setRestaurantPhone("0332013331");
        res3.setRestaurantAddress("Keio Plaza 4, Tokyo");
        res3.setLatitude(35.689487); //Tokyo
        res3.setLongitude(139.691706);

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(res1);
        restaurants.add(res2);
        restaurants.add(res3);
        return restaurants;

    }

    public static ArrayList<DailyOffer> makeOffers() {

        off1 = new DailyOffer();
        Uri uri = Uri.parse("android.resource://com.sorbellini.s214631.lab3/drawable/pizza");
        String imagePath = uri.toString();
        off1.setID("1");
        off1.setRestaurant(res1);
        //off1.getRestaurant().setID("1");
        off1.setPhoto(imagePath);
        off1.setName("Pizza");
        off1.setPrice(10);
        //off1.getRestaurant().setLatitude(41.90278); //Rome
        //off1.getRestaurant().setLongitude(12.49637);
        //off1.getRestaurant().setRestaurantName("Da Gennaro");

        off2 = new DailyOffer();
        uri = Uri.parse("android.resource://com.sorbellini.s214631.lab3/drawable/escargot");
        imagePath = uri.toString();
        off2.setID("2");
        off2.setRestaurant(res2);
        //off2.getRestaurant().setID("2");
        off2.setPhoto(imagePath);
        off2.setName("Escargot");
        off2.setPrice(30);
        //off2.getRestaurant().setLatitude(48.85661); //Paris
        //off2.getRestaurant().setLongitude(2.35222);
        //off2.getRestaurant().setRestaurantName("Chez Léo");

        off3 = new DailyOffer();
        uri = Uri.parse("android.resource://com.sorbellini.s214631.lab3/drawable/pasta");
        imagePath = uri.toString();
        off3.setID("3");
        off3.setRestaurant(res1);
        //off3.getRestaurant().setID("1");
        off3.setPhoto(imagePath);
        off3.setName("Pasta");
        off3.setPrice(15);
        //off3.getRestaurant().setLatitude(41.90278); //Rome
        //off3.getRestaurant().setLongitude(12.49637);
        //off3.getRestaurant().setRestaurantName("Da Gennaro");

        off4 = new DailyOffer();
        uri = Uri.parse("android.resource://com.sorbellini.s214631.lab3/drawable/beef");
        imagePath = uri.toString();
        off4.setID("4");
        off4.setRestaurant(res2);
        //off4.getRestaurant().setID("2");
        off4.setPhoto(imagePath);
        off4.setName("Beef");
        off4.setPrice(20);
        //off4.getRestaurant().setLatitude(48.85661); //Paris
        //off4.getRestaurant().setLongitude(2.35222);
        //off4.getRestaurant().setRestaurantName("Chez Léo");

        off5 = new DailyOffer();
        uri = Uri.parse("android.resource://com.sorbellini.s214631.lab3/drawable/chicken");
        imagePath = uri.toString();
        off5.setID("5");
        off5.setRestaurant(res3);
        //off5.getRestaurant().setID("3");
        off5.setPhoto(imagePath);
        off5.setName("Chicken");
        off5.setPrice(8);
        //off5.getRestaurant().setLatitude(35.689487); //Tokio
        //off5.getRestaurant().setLongitude(139.691706);
        //off5.getRestaurant().setRestaurantName("Keio Sushi");

        off6 = new DailyOffer();
        uri = Uri.parse("android.resource://com.sorbellini.s214631.lab3/drawable/sushi");
        imagePath = uri.toString();
        off6.setID("6");
        off6.setRestaurant(res3);
        //off6.getRestaurant().setID("3");
        off6.setPhoto(imagePath);
        off6.setName("Sushi");
        off6.setPrice(16);
        //off6.getRestaurant().setLatitude(35.689487); //Tokio
        //off6.getRestaurant().setLongitude(139.691706);
        //off6.getRestaurant().setRestaurantName("Keio Sushi");

        ArrayList<DailyOffer> offers = new ArrayList<>();
        offers.add(off1);
        offers.add(off2);
        offers.add(off3);
        offers.add(off4);
        offers.add(off5);
        offers.add(off6);
        return offers;
    }

    public static ArrayList<Reservation> makeReservations(){

        Customer cus1 = new Customer();
        cus1.setName("Marco");
        cus1.setSurname("Bianchi");
        cus1.setPhone("3337659876");

        reserv1 = new Reservation();
        reserv1.setCustomer(cus1);
        reserv1.setDailyOffer(off1);
        reserv1.setStatus(Reservation.ARRIVED);
        reserv1.setTime("12:00");
        reserv1.setComment("I will need a big table");

        reserv2 = new Reservation();
        reserv2.setCustomer(cus1);
        reserv2.setDailyOffer(off2);
        reserv2.setStatus(Reservation.ARRIVED);
        reserv2.setTime("13:30");
        reserv2.setComment("I may be in late");

        reserv3 = new Reservation();
        reserv3.setCustomer(cus1);
        reserv3.setDailyOffer(off3);
        reserv3.setStatus(Reservation.ARRIVED);
        reserv3.setTime("14:00");
        reserv3.setComment("");

        ArrayList<Reservation> reservations = new ArrayList<>();
        reservations.add(reserv1);
        reservations.add(reserv2);
        reservations.add(reserv3);
        return reservations;

    }


}
