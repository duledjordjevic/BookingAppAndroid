package com.example.bookingapplication.model;

public class CommentAboutAcc {
    private int rating;
    private String content;
    private Long guestId;
    private Long accommodationId;

    public CommentAboutAcc(){}
    public CommentAboutAcc(int rating, String content, Long guestId, Long accommodationId) {
        this.rating = rating;
        this.content = content;
        this.guestId = guestId;
        this.accommodationId = accommodationId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

}
