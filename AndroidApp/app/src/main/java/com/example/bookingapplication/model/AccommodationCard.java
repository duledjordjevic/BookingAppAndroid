package com.example.bookingapplication.model;

import java.util.List;

public class AccommodationCard {
    private Long id;
    private String title;
    private Address address;
    private String description;
    private Double avgRate;
    private Double pricePerNight;
    private Double totalPrice;
    private List<String> images;
    private Boolean isFavourite;

    public AccommodationCard(Long id, String title, Address address, String description,
                             Double avgRate, Double pricePerNight, Double totalPrice,
                             List<String> images, Boolean isFavourite) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.description = description;
        this.avgRate = avgRate;
        this.pricePerNight = pricePerNight;
        this.totalPrice = totalPrice;
        this.images = images;
        this.isFavourite = isFavourite;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }
}
