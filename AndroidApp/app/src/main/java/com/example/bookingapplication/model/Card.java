package com.example.bookingapplication.model;

import android.util.Log;

public class Card {

    private Long id;
    private String title;
    private String description;
    private Address address;
    private Double avgRate;
    private Double pricePerNight;
    private Double totalPrice;
    private String image;

    public Card(Long id, String title, String description, Address address, Double avgRate, Double pricePerNight, Double totalPrice, String image) {
        this.id = id;
        this.title = title;
        Log.d("Title: ", String.valueOf(this.title));
        this.description = description;
        this.address = address;
        this.avgRate = avgRate;
        this.pricePerNight = pricePerNight;
        this.totalPrice = totalPrice;
        this.image = image;
//        Log.d("NovaSlika: ", String.valueOf(this.image));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Double avgRate) {
        this.avgRate = avgRate;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
