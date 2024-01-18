package com.example.bookingapplication.model;

import com.example.bookingapplication.model.enums.AccommodationApprovalStatus;

public class AccApprovalStatus {
    private AccommodationApprovalStatus approvalStatus;
    public AccApprovalStatus(AccommodationApprovalStatus accommodationApprovalStatus) {
        this.approvalStatus = accommodationApprovalStatus;
    }

    public AccommodationApprovalStatus getAccommodationApprovalStatus() {
        return approvalStatus;
    }

    public void setAccommodationApprovalStatus(AccommodationApprovalStatus accommodationApprovalStatus) {
        this.approvalStatus = accommodationApprovalStatus;
    }
}
