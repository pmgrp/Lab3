package com.sorbellini.s214631.lab3;

import java.util.Comparator;

/**
 * Created by eugeniosorbellini on 03/05/16.
 */
public class RestaurantDistanceComparator implements Comparator<Restaurant> {

        public int compare(Restaurant lhs, Restaurant rhs){

        return (int) (lhs.distance[0] - rhs.distance[0]);
    }
}
