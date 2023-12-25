package com.example.bookingapplication.model;

import com.example.bookingapplication.model.enums.AccommodationStatus;

import java.time.LocalDate;
import java.util.Date;

public class PriceList {
    private Long id;

    private Date date;

    private double price;

    private AccommodationStatus status;

    public PriceList(){

    }
    public PriceList(Long id, Date date, double price, AccommodationStatus status) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AccommodationStatus getStatus() {
        return status;
    }

    public void setStatus(AccommodationStatus status) {
        this.status = status;
    }
}
