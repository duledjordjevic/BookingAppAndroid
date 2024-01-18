package com.example.bookingapplication.model;

public class CommentAboutHost {
    private int rating;
    private String content;
    private Long guestId;
    private Long hostId;

    public CommentAboutHost(int rating, String content, Long guestId, Long hostId) {
        this.rating = rating;
        this.content = content;
        this.guestId = guestId;
        this.hostId = hostId;
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

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }
}
