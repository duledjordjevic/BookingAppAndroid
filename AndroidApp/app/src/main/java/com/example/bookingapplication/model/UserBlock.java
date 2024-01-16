package com.example.bookingapplication.model;

import com.example.bookingapplication.helpers.LocalDateAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class UserBlock {
    private Long id;
    private String reason;
    private User reportedUser;
    private User reportingUser;
    private String accommodationTitle;
    @JsonAdapter(LocalDateAdapter.class)
    @SerializedName("endDate")
    private LocalDate startDate;
    @JsonAdapter(LocalDateAdapter.class)
    @SerializedName("startDate")
    private LocalDate endDate;

    public Long getId() {
        return id;
    }

    public UserBlock(Long id, String reason, User reportedUser, User reportingUser, String accommodationTitle, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.reason = reason;
        this.reportedUser = reportedUser;
        this.reportingUser = reportingUser;
        this.accommodationTitle = accommodationTitle;
        this.startDate = startDate;
        this.endDate = endDate;
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
