package com.example.bookingapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.PushbackReader;
import java.time.LocalDate;

public class UserBlockCard implements Parcelable {
    private Long id;
    private String reason;
    private User reportedUser;
    private User reportingUser;
    private String accommodationTitle;
    private LocalDate startDate;
    private LocalDate endDate;

    public UserBlockCard(Long id, String reason, User reportedUser, User reportingUser, String accommodationTitle, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.reason = reason;
        this.reportedUser = reportedUser;
        this.reportingUser = reportingUser;
        this.accommodationTitle = accommodationTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public UserBlockCard(UserBlock userBlock){
        this.id = userBlock.getId();
        this.reason = userBlock.getReason();
        this.reportedUser = userBlock.getReportedUser();
        this.reportingUser = userBlock.getReportingUser();
        this.accommodationTitle = userBlock.getAccommodationTitle();
        this.startDate = userBlock.getStartDate();
        this.endDate = userBlock.getEndDate();
    }


    protected UserBlockCard(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        reason = in.readString();
        accommodationTitle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(reason);
        dest.writeString(accommodationTitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserBlockCard> CREATOR = new Creator<UserBlockCard>() {
        @Override
        public UserBlockCard createFromParcel(Parcel in) {
            return new UserBlockCard(in);
        }

        @Override
        public UserBlockCard[] newArray(int size) {
            return new UserBlockCard[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(User reportedUser) {
        this.reportedUser = reportedUser;
    }

    public User getReportingUser() {
        return reportingUser;
    }

    public void setReportingUser(User reportingUser) {
        this.reportingUser = reportingUser;
    }

    public String getAccommodationTitle() {
        return accommodationTitle;
    }

    public void setAccommodationTitle(String accommodationTitle) {
        this.accommodationTitle = accommodationTitle;
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
}
