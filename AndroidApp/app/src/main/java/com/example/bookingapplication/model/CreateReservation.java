package com.example.bookingapplication.model;

import com.example.bookingapplication.helpers.LocalDateAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class CreateReservation {
    @JsonAdapter(LocalDateAdapter.class)
    @SerializedName("startDate")
    private LocalDate startDate;
    @JsonAdapter(LocalDateAdapter.class)
    @SerializedName("endDate")
    private LocalDate endDate;
    private int numberOfGuests;
    private Long guestId;
    private Long accommodationId;

    public CreateReservation(LocalDate startDate, LocalDate endDate, int numberOfGuests, Long guestId, Long accommodationId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.guestId = guestId;
        this.accommodationId = accommodationId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    @Override
    public String toString() {
        return "CreateReservation{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", numberOfGuests=" + numberOfGuests +
                ", guestId=" + guestId +
                ", accommodationId=" + accommodationId +
                '}';
    }
}
