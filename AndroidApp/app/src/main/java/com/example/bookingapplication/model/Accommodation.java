package com.example.bookingapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.bookingapplication.model.enums.AccommodationApprovalStatus;
import com.example.bookingapplication.model.enums.AccommodationType;
import com.example.bookingapplication.model.enums.Amenities;
import com.example.bookingapplication.model.enums.CancellationPolicy;
import com.example.bookingapplication.model.enums.ReservationMethod;

import java.util.List;
import java.util.Set;

public class Accommodation {
    private Long id;
    private String title;
    private String description;
    private Address address;
    private List<Amenities> amenities;
    private List<String> images;
    private int minGuest;
    private int maxGuest;
    private AccommodationType type;
    private CancellationPolicy cancellationPolicy;
    private ReservationMethod reservationMethod;
    private AccommodationApprovalStatus accommodationApprovalStatus;
    private boolean isPriceForEntireAcc;
    private Set<PriceList> prices;
    private Long hostId;

    public Accommodation() {
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

    public List<Amenities> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenities> amenities) {
        this.amenities = amenities;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getMinGuest() {
        return minGuest;
    }

    public void setMinGuest(int minGuest) {
        this.minGuest = minGuest;
    }

    public int getMaxGuest() {
        return maxGuest;
    }

    public void setMaxGuest(int maxGuest) {
        this.maxGuest = maxGuest;
    }

    public AccommodationType getType() {
        return type;
    }

    public void setType(AccommodationType type) {
        this.type = type;
    }

    public CancellationPolicy getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(CancellationPolicy cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public ReservationMethod getReservationMethod() {
        return reservationMethod;
    }

    public void setReservationMethod(ReservationMethod reservationMethod) {
        this.reservationMethod = reservationMethod;
    }

    public AccommodationApprovalStatus getAccommodationApprovalStatus() {
        return accommodationApprovalStatus;
    }

    public void setAccommodationApprovalStatus(AccommodationApprovalStatus accommodationApprovalStatus) {
        this.accommodationApprovalStatus = accommodationApprovalStatus;
    }

    public boolean isPriceForEntireAcc() {
        return isPriceForEntireAcc;
    }

    public void setPriceForEntireAcc(boolean priceForEntireAcc) {
        isPriceForEntireAcc = priceForEntireAcc;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

}
