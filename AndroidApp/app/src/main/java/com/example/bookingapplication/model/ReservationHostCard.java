package com.example.bookingapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bookingapplication.model.enums.ReservationStatus;

import java.time.LocalDate;

public class ReservationHostCard implements Parcelable {

    private Long id;
    private Accommodation accommodation;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;
    private Guest guest;
    private double price;

    public ReservationHostCard(){}
    public ReservationHostCard(Long id, Accommodation accommodation, LocalDate startDate, LocalDate endDate, ReservationStatus status, Guest guest, double price) {
        this.id = id;
        this.accommodation = accommodation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.guest = guest;
        this.price = price;
    }

    public ReservationHostCard(Reservation reservation){
        this.id = reservation.getId();
        this.accommodation = reservation.getAccommodation();
        this.startDate = reservation.getStartDate();
        this.endDate = reservation.getEndDate();
        this.status = reservation.getStatus();
        this.price = reservation.getPrice();
        this.guest = reservation.getGuest();
    }


    protected ReservationHostCard(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        price = in.readDouble();
    }

    public static final Creator<ReservationHostCard> CREATOR = new Creator<ReservationHostCard>() {
        @Override
        public ReservationHostCard createFromParcel(Parcel in) {
            return new ReservationHostCard(in);
        }

        @Override
        public ReservationHostCard[] newArray(int size) {
            return new ReservationHostCard[size];
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

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ReservationHostCard{" +
                "id=" + id +
                ", accommodation=" + accommodation +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", guest=" + guest +
                ", price=" + price +
                '}';
    }
}
