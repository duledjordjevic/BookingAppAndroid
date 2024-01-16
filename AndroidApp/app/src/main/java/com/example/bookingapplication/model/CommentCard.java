package com.example.bookingapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class CommentCard implements Parcelable {

    private Long id;
    private int rating;
    private LocalDate date;
    private String reportMessage;
    private Host host;
    private Accommodation accommodation;
    private boolean isReported;
    private String content;
    private boolean isApproved;
    private String guestName;
    private String guestLastName;
    private String guestEmail;

    public CommentCard(){

    }
    public CommentCard(Long id, int rating, LocalDate date, String reportMessage, Host host,
                       Accommodation accommodation, boolean isReported, String content,
                       boolean isApproved, String guestName, String guestLastName, String guestEmail) {
        this.id = id;
        this.rating = rating;
        this.date = date;
        this.reportMessage = reportMessage;
        this.host = host;
        this.accommodation = accommodation;
        this.isReported = isReported;
        this.content = content;
        this.isApproved = isApproved;
        this.guestName = guestName;
        this.guestLastName = guestLastName;
        this.guestEmail = guestEmail;
    }

    public CommentCard(Parcel in) {
        id = in.readLong();
        rating = in.readInt();
        content = in.readString();
        reportMessage = in.readString();
    }

    public Long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getReportMessage() {
        return reportMessage;
    }

    public Host getHost() {
        return host;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public boolean isReported() {
        return isReported;
    }

    public String getContent() {
        return content;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getGuestLastName() {
        return guestLastName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setReportMessage(String reportMessage) {
        this.reportMessage = reportMessage;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public void setReported(boolean reported) {
        isReported = reported;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public void setGuestLastName(String guestLastName) {
        this.guestLastName = guestLastName;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeInt(rating);
        parcel.writeString(reportMessage);
        parcel.writeString(content);
    }

    public static final Creator<CommentCard> CREATOR = new Creator<CommentCard>() {
        @Override
        public CommentCard createFromParcel(Parcel in) {
            return new CommentCard(in);
        }

        @Override
        public CommentCard[] newArray(int size) {
            return new CommentCard[size];
        }
    };
}
