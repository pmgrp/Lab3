package com.sorbellini.s214631.lab3;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by eugeniosorbellini on 10/05/16.
 */
public class AdapterShowReservations extends RecyclerView.Adapter<AdapterShowReservations.ReservationViewHolder> {

    ArrayList<Reservation> reservations;

    AdapterShowReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView offerName;
        TextView restaurantName;
        TextView time;
        TextView status;

        ReservationViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.reservation_card);
            offerName = (TextView) itemView.findViewById(R.id.reservation_card_offer_name);
            restaurantName = (TextView) itemView.findViewById(R.id.reservation_card_restaurant);
            time = (TextView) itemView.findViewById(R.id.reservation_card_time);
            status = (TextView) itemView.findViewById(R.id.reservation_card_status);
        }
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reservation_card, viewGroup, false);
        ReservationViewHolder rvh = new ReservationViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(final ReservationViewHolder restaurantViewHolder, int i) {

        restaurantViewHolder.offerName.setText(reservations.get(i).getDailyOffer().getName());
        restaurantViewHolder.restaurantName.setText(reservations.get(i).getDailyOffer().getRestaurantName());
        restaurantViewHolder.time.setText(reservations.get(i).getTime());
        int status = reservations.get(i).getStatus();
        switch (status) {
            case Reservation.ARRIVED:
                restaurantViewHolder.status.setText("To Be Approved");
                break;
            case Reservation.CONFIRMED:
                restaurantViewHolder.status.setText("Approved");
                break;
            case Reservation.COMPLETED:
                restaurantViewHolder.status.setText("Completed");
                break;
            case Reservation.REJECTED:
                restaurantViewHolder.status.setText("Rejected");
                break;
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}