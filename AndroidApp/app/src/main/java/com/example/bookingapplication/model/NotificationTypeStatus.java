package com.example.bookingapplication.model;

import com.example.bookingapplication.model.enums.NotificationType;

public class NotificationTypeStatus {
    private Long id;
    private User user;
    private NotificationType type;
    private Boolean isTurned;
    private Long userId;

    public NotificationTypeStatus(Long id, User user, NotificationType type, Boolean isTurned, Long userId) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.isTurned = isTurned;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Boolean getTurned() {
        return isTurned;
    }

    public void setTurned(Boolean turned) {
        this.isTurned = turned;
    }

    @Override
    public String toString() {
        return "NotificationTypeStatus{" +
                "id=" + id +
                ", user=" + user +
                ", type=" + type +
                ", turned=" + isTurned +
                '}';
    }
}
