package com.example.bookingapplication.model;

public class Analytics {
    private Long accommodationId;
    private String name;
    private Long totalReservations;
    private double totalEarnings;

    public Analytics() {}

    public Analytics(Long accommodationId, String name, Long totalReservations, double totalEarnings) {
        this.accommodationId = accommodationId;
        this.name = name;
        this.totalReservations = totalReservations;
        this.totalEarnings = totalEarnings;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTotalReservations() {
        return totalReservations;
    }

    public void setTotalReservations(Long totalReservations) {
        this.totalReservations = totalReservations;
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }
}
