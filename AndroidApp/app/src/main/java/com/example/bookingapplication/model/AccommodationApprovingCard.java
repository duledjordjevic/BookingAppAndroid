package com.example.bookingapplication.model;

public class AccommodationApprovingCard {
    private String id;
    private String title;
    private Address address;
    private int image;
    private String description;

    public AccommodationApprovingCard(String id, String title, Address address, int image, String description) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.image = image;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Address getAddress() {
        return address;
    }

    public int getImage() {
        return image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
