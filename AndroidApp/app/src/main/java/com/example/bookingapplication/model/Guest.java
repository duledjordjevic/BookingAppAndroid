package com.example.bookingapplication.model;

import java.util.HashSet;
import java.util.Set;

public class Guest extends Person{

    private int numberOfCancellation;
//    private Set<Accommodation> favourites = new HashSet<>();

    public int getNumberOfCancellation() {
        return numberOfCancellation;
    }

    public void setNumberOfCancellation(int numberOfCancellation) {
        this.numberOfCancellation = numberOfCancellation;
    }

//    public Set<Accommodation> getFavourites() {
//        return favourites;
//    }
//
//    public void setFavourites(Set<Accommodation> favourites) {
//        this.favourites = favourites;
//    }


}
