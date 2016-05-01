package com.sorbellini.s214631.lab3;

import java.util.Comparator;

/**
 * Created by eugeniosorbellini on 27/04/16.
 */
public class OfferDistanceComparator implements Comparator<DailyOffer> {

    public int compare(DailyOffer lhs, DailyOffer rhs){

        return (int) (lhs.distance[0] - rhs.distance[0]);
    }
}
