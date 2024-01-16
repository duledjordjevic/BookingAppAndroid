package com.example.bookingapplication.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bookingapplication.helpers.LocalDateAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class CommentCard implements Parcelable {

    private Long id;
    private int rating;
    @JsonAdapter(LocalDateAdapter.class)
    @SerializedName("date")
    private LocalDate date;
    private String reportMessage;
    private String accommodationTitle;
    private boolean isReported;
    private String content;
    private boolean isApproved;
    private String guestName;
    private String guestLastName;
    private String guestEmail;
    private String hostNameSurname;

    public CommentCard(){

    }
    public CommentCard(Long id, int rating, LocalDate date, String reportMessage, String hostNameSurname,
                       String accommodationTitle, boolean isReported, String content,
                       boolean isApproved, String guestName, String guestLastName, String guestEmail) {
        this.id = id;
        this.rating = rating;
        this.date = date;
        this.reportMessage = reportMessage;
        this.hostNameSurname = hostNameSurname;
        this.accommodationTitle = accommodationTitle;
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
        reportMessage = in.readString();
        content = in.readString();
        accommodationTitle = in.readString();
        hostNameSurname = in.readString();
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

    public String getAccommodationTitle() {
        return accommodationTitle;
    }
    public String getHostNameSurname() {
        return hostNameSurname;
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

    public void setAccommodationTitle(String accommodationTitle) {
        this.accommodationTitle = accommodationTitle;
    }
    public void setHostNameSurname(String hostNameSurname) {
        this.hostNameSurname = hostNameSurname;
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
        parcel.writeString(accommodationTitle);
        parcel.writeString(hostNameSurname);
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

    @Override
    public String toString() {
        return "CommentCard{" +
                "id=" + id +
                ", rating=" + rating +
                ", date=" + date +
                ", reportMessage='" + reportMessage + '\'' +
                ", accommodationTitle='" + accommodationTitle + '\'' +
                ", isReported=" + isReported +
                ", content='" + content + '\'' +
                ", isApproved=" + isApproved +
                ", guestName='" + guestName + '\'' +
                ", guestLastName='" + guestLastName + '\'' +
                ", guestEmail='" + guestEmail + '\'' +
                ", hostNameSurname='" + hostNameSurname + '\'' +
                '}';
    }
}
