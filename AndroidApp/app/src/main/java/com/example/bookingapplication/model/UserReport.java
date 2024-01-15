package com.example.bookingapplication.model;

public class UserReport {
    private Long id;
    private Long userReportedId;
    private Long userReportingId;
    private String reason;

    public UserReport(Long id, Long userReportedId, Long userReportingId, String reason, Long reservationId) {
        this.id = id;
        this.userReportedId = userReportedId;
        this.userReportingId = userReportingId;
        this.reason = reason;
        this.reservationId = reservationId;
    }

    private Long reservationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserReportedId() {
        return userReportedId;
    }

    public void setUserReportedId(Long userReportedId) {
        this.userReportedId = userReportedId;
    }

    public Long getUserReportingId() {
        return userReportingId;
    }

    public void setUserReportingId(Long userReportingId) {
        this.userReportingId = userReportingId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
}
