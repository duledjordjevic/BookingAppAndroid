package com.example.bookingapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bookingapplication.model.enums.ReservationStatus;

import java.time.LocalDate;

public class ReservationGuestCard implements Parcelable {

    private Long id;
    private Accommodation accommodation;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer numberOfGuests;
    private ReservationStatus status;
    private double price;
    private boolean isHostReported;
    public boolean isHostReported() {
        return isHostReported;
    }
    public void setHostReported(boolean hostReported) {
        isHostReported = hostReported;
    }

    public ReservationGuestCard(){}

    protected ReservationGuestCard(Parcel in){
        id = in.readLong();
        accommodation.setHostId(in.readLong());
//        accommodation.setAmenities(in.createTypedArrayList());
//        startDate = in.read;
//        this.endDate = endDate;
//        this.numberOfGuests = numberOfGuests;
//        this.status = status;
//        this.price = price;
    }


    public ReservationGuestCard(Long id, Accommodation accommodation, LocalDate startDate, LocalDate endDate, Integer numberOfGuests, ReservationStatus status, double price) {
        this.id = id;
        this.accommodation = accommodation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.price = price;
    }

    public ReservationGuestCard(Reservation reservation){
        this.id = reservation.getId();
        this.accommodation = reservation.getAccommodation();
        this.startDate = reservation.getStartDate();
        this.endDate = reservation.getEndDate();
        this.numberOfGuests = reservation.getNumberOfGuests();
        this.status = reservation.getStatus();
        this.price = reservation.getPrice();
        this.isHostReported = reservation.isHostReported();
    }


    public static final Creator<ReservationGuestCard> CREATOR = new Creator<ReservationGuestCard>() {
        @Override
        public ReservationGuestCard createFromParcel(Parcel in) {
            return new ReservationGuestCard(in);
        }

        @Override
        public ReservationGuestCard[] newArray(int size) {
            return new ReservationGuestCard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        if (numberOfGuests == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numberOfGuests);
        }
        dest.writeDouble(price);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
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

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
