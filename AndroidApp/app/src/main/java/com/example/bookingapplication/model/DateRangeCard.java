package com.example.bookingapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bookingapplication.helpers.LocalDateAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class DateRangeCard implements Parcelable {

    @JsonAdapter(LocalDateAdapter.class)
    @SerializedName("startDate")
    private LocalDate startDate;
    @JsonAdapter(LocalDateAdapter.class)
    @SerializedName("endDate")
    private LocalDate endDate;
    private double price;

    public DateRangeCard() {}

    public DateRangeCard(LocalDate startDate, LocalDate endDate, double price) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
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

    protected DateRangeCard(Parcel in) {
    }

    public static final Creator<DateRangeCard> CREATOR = new Creator<DateRangeCard>() {
        @Override
        public DateRangeCard createFromParcel(Parcel in) {
            return new DateRangeCard(in);
        }

        @Override
        public DateRangeCard[] newArray(int size) {
            return new DateRangeCard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }

    @Override
    public String toString() {
        return "DateRangeCard{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                '}';
    }
}
