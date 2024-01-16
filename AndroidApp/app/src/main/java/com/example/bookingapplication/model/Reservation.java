package com.example.bookingapplication.model;

import com.example.bookingapplication.helpers.LocalDateAdapter;
import com.example.bookingapplication.model.enums.ReservationStatus;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class Reservation {
        private Long id;
        @JsonAdapter(LocalDateAdapter.class)
        @SerializedName("startDate")
        private LocalDate startDate;
        @JsonAdapter(LocalDateAdapter.class)
        @SerializedName("endDate")
        private LocalDate endDate;
        private double price;
        private int numberOfGuests;
        private ReservationStatus status;
        private Guest guest;
        private Accommodation accommodation;
        private boolean hostReported;
        private boolean guestReported;

        public boolean isHostReported() {
                return hostReported;
        }

        public void setHostReported(boolean hostReported) {
                hostReported = hostReported;
        }

        public boolean isGuestReported() {
                return guestReported;
        }

        public void setGuestReported(boolean guestReported) {
                guestReported = guestReported;
        }
        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
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

        public double getPrice() {
                return price;
        }

        public void setPrice(double price) {
                this.price = price;
        }

        public int getNumberOfGuests() {
                return numberOfGuests;
        }

        public void setNumberOfGuests(int numberOfGuests) {
                this.numberOfGuests = numberOfGuests;
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

        public Accommodation getAccommodation() {
                return accommodation;
        }

        public void setAccommodation(Accommodation accommodation) {
                this.accommodation = accommodation;
        }
}
